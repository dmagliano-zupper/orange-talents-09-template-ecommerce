package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.categories.Category;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductFeatureForm;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @DecimalMin(value = "0.00", inclusive = false)
    @Positive
    private BigDecimal sellPrice;
    @Min(1)
    private Integer quantity;
    @Length(max = 1000)
    private String description;
    @Size(min = 3)
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductFeature> featureList;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    private Customer customer;
    private LocalDateTime createDateTime;

    @Deprecated
    public Product() {
    }

    public Product(String name, BigDecimal sellPrice, Integer quantity, String description,
                   Category category, Customer customer) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.customer = customer;
        this.createDateTime = LocalDateTime.now();
    }

    public void setFeatureList(List<ProductFeature> featureList) {
        this.featureList = featureList;
    }
}
