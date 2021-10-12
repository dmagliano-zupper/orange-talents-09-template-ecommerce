package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.products.Product;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
public class ProductRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private Integer rating;
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    @Length(max = 500)
    private String description;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Customer customer;

    @Deprecated
    public ProductRating() {
    }

    public ProductRating(Integer rating, String title, String description, Product product, Customer customer) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.product = product;
        this.customer = customer;
    }
}
