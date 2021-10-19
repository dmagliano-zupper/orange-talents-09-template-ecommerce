package br.com.zup.dmagliano.ecommerce.transaction.dto;

import br.com.zup.dmagliano.ecommerce.purchase.PurchaseOrder;
import br.com.zup.dmagliano.ecommerce.transaction.enums.TransactionPaymentStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;

    private String gatewayTransactionId;

    @Enumerated(EnumType.STRING)
    private TransactionPaymentStatus status;

    private LocalDateTime dateTime;


    public Long getId() {
        return id;
    }

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    public TransactionPaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Deprecated
    public TransactionDTO() {
    }

    public TransactionDTO(Long id, String gatewayTransactionId, TransactionPaymentStatus status, LocalDateTime dateTime) {
        this.id = id;
        this.gatewayTransactionId = gatewayTransactionId;
        this.status = status;
        this.dateTime = dateTime;
    }
}
