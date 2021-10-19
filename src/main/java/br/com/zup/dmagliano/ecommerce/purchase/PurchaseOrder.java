package br.com.zup.dmagliano.ecommerce.purchase;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.products.Product;
import br.com.zup.dmagliano.ecommerce.purchase.dto.PurchaseOrderDto;
import br.com.zup.dmagliano.ecommerce.transaction.Transaction;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class PurchaseOrder {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Customer buyer;

    private Integer quantity;

    private BigDecimal agreedPrice;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.STARTED;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.MERGE)
    private Set<Transaction> transactionSet = new HashSet<>();


    @Deprecated
    public PurchaseOrder() {
    }

    public PurchaseOrder(Product product, Customer buyer, Integer quantity, BigDecimal agreedPrice, PaymentMethod paymentMethod) {
        this.product = product;
        this.buyer = buyer;
        this.quantity = quantity;
        this.agreedPrice = agreedPrice;
        this.totalPrice = agreedPrice.multiply(BigDecimal.valueOf(quantity));
        this.paymentMethod = paymentMethod;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public UUID getId() {
        return id;
    }

    public Customer getSeller() {
      return this.product.getSeller();
    }

    public URI getPaymentURL(URI purchaseUri){

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(this.paymentMethod.address)
                .query("buyerId={orderId}")
                .query("redirectUrl={redirectUrl}")
                .buildAndExpand(this.id,purchaseUri.toString());

        return uriComponents.toUri();
    }

    public PurchaseOrderDto toDto(){
        return new PurchaseOrderDto(
                this.id,
                this.product.getName(),
                this.buyer.getName(),
                this.product.getSeller().getName(),
                this.quantity,
                this.agreedPrice,
                this.totalPrice,
                this.paymentMethod,
                this.orderStatus,
                this.transactionSet.stream().map(Transaction::toDto).collect(Collectors.toSet())
        );
    }

    public Boolean isStatusFinished(){
        return this.orderStatus.equals(OrderStatus.FINISHED) ? Boolean.TRUE : Boolean.FALSE;
    }

    private void setStatusFinished(){
        if (!isStatusFinished()){this.orderStatus = OrderStatus.FINISHED;}
    }

    public void addTransactionAttempt(Transaction transaction) {
        if (transaction.isSucess()){
            this.setStatusFinished();
        }
        this.transactionSet.add(transaction);
    }
}
