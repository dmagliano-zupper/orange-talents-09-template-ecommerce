package br.com.zup.dmagliano.ecommerce.transaction.dto;

import br.com.zup.dmagliano.ecommerce.purchase.PurchaseOrder;
import br.com.zup.dmagliano.ecommerce.transaction.GatewayTransactionResponse;
import br.com.zup.dmagliano.ecommerce.transaction.Transaction;
import br.com.zup.dmagliano.ecommerce.transaction.enums.PagSeguroTransactionStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagSeguroTransactionResponse implements GatewayTransactionResponse {

    @NotBlank
    private String transactionId;
    @NotNull
    private PagSeguroTransactionStatus status;

    public String getTransactionId() {
        return transactionId;
    }

    public PagSeguroTransactionStatus getStatus() {
        return status;
    }

    @Override
    public Transaction toTransaction(PurchaseOrder purchaseOrder) {

        return new Transaction(
                transactionId,
                purchaseOrder,
                this.status.getTransactionStatus()
        );
    }

}
