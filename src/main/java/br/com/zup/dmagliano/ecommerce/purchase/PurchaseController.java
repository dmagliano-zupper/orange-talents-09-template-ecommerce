package br.com.zup.dmagliano.ecommerce.purchase;

import br.com.zup.dmagliano.ecommerce.common.EmailSenderFake;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.customers.CustomerRepository;
import br.com.zup.dmagliano.ecommerce.products.Product;
import br.com.zup.dmagliano.ecommerce.products.ProductRepository;
import br.com.zup.dmagliano.ecommerce.security.LoggedCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    EmailSenderFake emailSenderFake;

    @PostMapping("/{id}/order")
    @Transactional
    public ResponseEntity order(@PathVariable("id") Long id, @RequestBody @Valid PurchaseOrderForm orderForm, @AuthenticationPrincipal LoggedCustomer loggedCustomer) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Customer customer = customerRepository.findByEmail(loggedCustomer.getUsername()).orElseThrow(EntityNotFoundException::new);
        if (product.isQuantityAvailable(orderForm.getQuantity())) {
            PurchaseOrder purchase = new PurchaseOrder(
                    product,
                    customer,
                    orderForm.getQuantity(),
                    product.getSellPrice(),
                    orderForm.getPaymentMethod()
            );
            emailSenderFake.notifyOrder(purchase);
            purchaseRepository.save(purchase);

        }
        return null;
    }

}
