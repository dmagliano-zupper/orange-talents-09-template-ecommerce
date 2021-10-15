package br.com.zup.dmagliano.ecommerce.purchase;

import br.com.zup.dmagliano.ecommerce.common.EmailSenderFake;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.customers.CustomerRepository;
import br.com.zup.dmagliano.ecommerce.products.Product;
import br.com.zup.dmagliano.ecommerce.products.ProductRepository;
import br.com.zup.dmagliano.ecommerce.security.LoggedCustomer;
import com.fasterxml.jackson.core.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    EmailSenderFake emailSenderFake;

    @PostMapping("/{id}/order")
    @Transactional
    public ResponseEntity order(@PathVariable("id") Long id,
                                @RequestBody @Valid PurchaseOrderForm orderForm,
                                @AuthenticationPrincipal LoggedCustomer loggedCustomer,
                                UriComponentsBuilder uriBuilder) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Customer customer = loggedCustomer.get();
        if (!product.isQuantityAvailable(orderForm.getQuantity())) {
            return ResponseEntity.status(422).body("Produto esgotado ou quantidade insuficiente.");
        }
        product.removeSold(orderForm.getQuantity());
        PurchaseOrder purchase = new PurchaseOrder(
                product,
                customer,
                orderForm.getQuantity(),
                product.getSellPrice(),
                orderForm.getPaymentMethod()
        );
        purchaseRepository.save(purchase);
        emailSenderFake.notifyOrder(purchase);
        URI uri = uriBuilder.path("/purchases/{id}").buildAndExpand(purchase.getId()).toUri();
        System.out.println("URL de redirect para pagamento: " + purchase.getPaymentURL(uri).toString());

        return ResponseEntity.status(HttpStatus.FOUND).location(purchase.getPaymentURL(uri)).build();
    }

}
