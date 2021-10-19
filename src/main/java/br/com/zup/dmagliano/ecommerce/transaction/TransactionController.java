package br.com.zup.dmagliano.ecommerce.transaction;

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

        purchase.addTransactionAttempt(gatewayTransactionResponse);
        purchaseRepository.save(purchase);

        return ResponseEntity.ok().build();


    }

}
