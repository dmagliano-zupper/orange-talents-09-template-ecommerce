package br.com.zup.dmagliano.ecommerce.transaction.dto;


import br.com.zup.dmagliano.ecommerce.purchase.PurchaseOrder;
import br.com.zup.dmagliano.ecommerce.transaction.GatewayTransactionResponse;
import br.com.zup.dmagliano.ecommerce.transaction.Transaction;
import br.com.zup.dmagliano.ecommerce.transaction.enums.TransactionPaymentStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PaypalTransactionResponse implements GatewayTransactionResponse {

    @NotBlank
    private String transactionId;
    @Min(0)
    @Max(1)
    private Integer status;

    public String getTransactionId() {
        return transactionId;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public Transaction toTransaction(PurchaseOrder purchaseOrder) {
        TransactionPaymentStatus transactionPaymentStatus = this.status == 0 ?
                TransactionPaymentStatus.ERROR : TransactionPaymentStatus.SUCCESS;

        return new Transaction(
                transactionId,
                purchaseOrder,
                transactionPaymentStatus
        );
    }
}
