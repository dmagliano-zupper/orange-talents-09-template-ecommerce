package br.com.zup.dmagliano.ecommerce.transaction;

import br.com.zup.dmagliano.ecommerce.others.Invoice;
import br.com.zup.dmagliano.ecommerce.others.Ranking;
import br.com.zup.dmagliano.ecommerce.purchase.PurchaseOrder;
import br.com.zup.dmagliano.ecommerce.purchase.PurchaseRepository;
import br.com.zup.dmagliano.ecommerce.transaction.dto.PagSeguroTransactionResponse;
import br.com.zup.dmagliano.ecommerce.transaction.dto.PaypalTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    private Invoice invoice;
    @Autowired
    private Ranking ranking;


    @PostMapping("/paypal/{orderId}")
    public ResponseEntity paypalTransactionCallback(@PathVariable("orderId") String orderId,
                                                    @RequestBody @Valid PaypalTransactionResponse paypalTransaction) {

        return processGatewayTransactionResponse(orderId, paypalTransaction);
    }

    @PostMapping("/pagseguro/{orderId}")
    @Transactional
    public ResponseEntity pagSeguroTransactionCallback(@PathVariable("orderId") String orderId,
                                                       @RequestBody @Valid PagSeguroTransactionResponse pagSeguroTransaction) {

        return processGatewayTransactionResponse(orderId, pagSeguroTransaction);
    }


    private ResponseEntity processGatewayTransactionResponse(String orderId, GatewayTransactionResponse gatewayTransactionResponse) {

        PurchaseOrder purchase = purchaseRepository.findById(UUID.fromString(orderId)).orElseThrow(EntityNotFoundException::new);
        if (purchase.isStatusFinished()) {
            return ResponseEntity.badRequest().body("Transação já concluida");
        }

        Transaction transaction = gatewayTransactionResponse.toTransaction(purchase);
        purchase.addTransactionAttempt(transaction);
        purchaseRepository.saveAndFlush(purchase);
        if (transaction.isSucess()) {
            System.out.println("Invoice requested to order " + purchase.getId());
            invoice.process(purchase.toDto());
            System.out.println("Ranking points added to " + purchase.getSeller().getName());
            ranking.process(purchase.toDto());
        }

        return ResponseEntity.ok().build();
    }

}
