package br.com.zup.dmagliano.ecommerce.purchase.dto;

import br.com.zup.dmagliano.ecommerce.purchase.OrderStatus;
import br.com.zup.dmagliano.ecommerce.purchase.PaymentMethod;
import br.com.zup.dmagliano.ecommerce.transaction.dto.TransactionDTO;
import br.com.zup.dmagliano.ecommerce.transaction.enums.TransactionPaymentStatus;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PurchaseOrderDto {

    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String productName;

    private String buyerName;

    private String sellerName;

    private Integer quantity;

    private BigDecimal agreedPrice;

    private BigDecimal totalPrice;

    private PaymentMethod paymentMethod;

    private OrderStatus orderStatus;

    private Set<TransactionDTO> transactionDtoSet;

    public PurchaseOrderDto(UUID id, String productName, String buyerName, String sellerName, Integer quantity,
                            BigDecimal agreedPrice, BigDecimal totalPrice, PaymentMethod paymentMethod,
                            OrderStatus orderStatus, Set<TransactionDTO> transactionDtoSet) {
        this.id = id;
        this.productName = productName;
        this.buyerName = buyerName;
        this.sellerName = sellerName;
        this.quantity = quantity;
        this.agreedPrice = agreedPrice;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.transactionDtoSet = transactionDtoSet;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getSellerName() {return sellerName; }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getAgreedPrice() {
        return agreedPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Set<TransactionDTO> getTransactionDtoSet() {
        return transactionDtoSet;
    }

    public TransactionDTO sucessfullTransaction() {
        List<TransactionDTO> transactionDTOList = this.transactionDtoSet.stream()
                .filter(transactionDTO -> transactionDTO.getStatus().equals(TransactionPaymentStatus.SUCCESS))
                .collect(Collectors.toList());
        return transactionDTOList.get(0);
    }

}
