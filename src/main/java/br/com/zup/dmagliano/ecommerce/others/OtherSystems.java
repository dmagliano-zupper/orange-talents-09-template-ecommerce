package br.com.zup.dmagliano.ecommerce.others;

import br.com.zup.dmagliano.ecommerce.purchase.dto.PurchaseOrderDto;
import br.com.zup.dmagliano.ecommerce.transaction.dto.TransactionDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OtherSystems {

    @PostMapping(value = "/invoices")
    public void invoice(@RequestBody PurchaseOrderDto purchaseOrderDto) {
        TransactionDTO successfulTransaction = purchaseOrderDto.sucessfullTransaction();
        System.out.println("#### Invoice System ####");
        System.out.println("Order " + purchaseOrderDto.getId() + " completed, status seted to: " + purchaseOrderDto.getOrderStatus());
        System.out.println("With transaction id: " +  successfulTransaction.getId() + " completed with " + successfulTransaction.getStatus() + " status." );
    }

    @PostMapping(value = "/ranking")
    public void ranking(@RequestBody PurchaseOrderDto purchaseOrderDto) {
        TransactionDTO successfulTransaction = purchaseOrderDto.sucessfullTransaction();
        System.out.println("#### Ranking System ####");
        System.out.println("Order " + purchaseOrderDto.getId() + " generated points to seller: " + purchaseOrderDto.getSellerName());
        System.out.println("With transaction id: " +  successfulTransaction.getId() + " completed with " + successfulTransaction.getStatus() + " status." );
    }

}
