package br.com.zup.dmagliano.ecommerce.purchase;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.products.Product;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
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
}
