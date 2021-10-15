package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.categories.Category;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductDetailsDto;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductFeatureDto;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductQuestionDto;
import br.com.zup.dmagliano.ecommerce.products.image.ProductImage;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private List<ProductFeature> featureList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    private Customer seller;
    private LocalDateTime createDateTime;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ProductImage> productImages = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ProductRating> productRatings = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<ProductQuestion> productQuestionList = new ArrayList<>();

    @Deprecated
    public Product() {
    }

    public Product(String name, BigDecimal sellPrice, Integer quantity, String description,
                   Category category, Customer seller) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.seller = seller;
        this.createDateTime = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public Customer getSeller(){
       return this.seller;
    }

    public void setFeatureList(List<ProductFeature> featureList) {

        this.featureList = featureList;
    }

    public void setImages(Set<String> links) {
        this.productImages = links.stream().map(
                        link -> new ProductImage(this, link))
                .collect(Collectors.toSet());
    }

    public void addRatings(ProductRating productRating) {

        this.productRatings.add(productRating);
    }

    public void addQuestion(ProductQuestion productQuestion) {

        this.productQuestionList.add(productQuestion);
    }

    public boolean isOwner(Customer customer) {

        return this.seller.equals(customer);
    }

    private Set<String> getImagesSet() {
        return this.productImages.stream().map(productImage -> productImage.getLink()).collect(Collectors.toSet());
    }

    private ProductRatingSet getRatingSet() {
        return new ProductRatingSet(this.productRatings);
    }

    private Set<ProductFeatureDto> toFeatureDto() {
        return this.featureList.stream().map(ProductFeature::toRatingDto).collect(Collectors.toSet());
    }

    private Set<ProductQuestionDto> toQuestionDto() {
        return this.productQuestionList.stream().map(ProductQuestion::toQuestionDto)
                .collect(Collectors.toSet());
    }

    public ProductDetailsDto toProductDetails() {
        return new ProductDetailsDto(
                this.name,
                this.sellPrice,
                getImagesSet(),
                toFeatureDto(),
                this.description,
                toQuestionDto(),
                getRatingSet()
        );
    }

    public boolean isQuantityAvailable(Integer quantity) {
        if (this.quantity.compareTo(quantity) >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
