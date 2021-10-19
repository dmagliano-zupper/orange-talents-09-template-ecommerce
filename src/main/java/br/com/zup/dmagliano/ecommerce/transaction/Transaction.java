package br.com.zup.dmagliano.ecommerce.transaction;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.purchase.PurchaseOrder;
import br.com.zup.dmagliano.ecommerce.transaction.dto.TransactionDTO;
import br.com.zup.dmagliano.ecommerce.transaction.enums.TransactionPaymentStatus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gatewayTransactionId;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @Enumerated(EnumType.STRING)
    private TransactionPaymentStatus status;

    private LocalDateTime dateTime;

    @Deprecated
    public Transaction() {
    }

    public Transaction(String gatewayTransactionId, PurchaseOrder purchaseOrder, TransactionPaymentStatus status) {
        this.gatewayTransactionId = gatewayTransactionId;
        this.purchaseOrder = purchaseOrder;
        this.status = status;
        this.dateTime = LocalDateTime.now();
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public boolean isSucess() {
        return this.status.equals(TransactionPaymentStatus.SUCCESS) ? Boolean.TRUE : Boolean.FALSE;
    }

    public TransactionDTO toDto(){
        return new TransactionDTO(
                this.id,
                this.gatewayTransactionId,
                this.status,
                this.dateTime
        );
    }
}
