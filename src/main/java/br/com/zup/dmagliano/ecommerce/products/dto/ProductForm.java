package br.com.zup.dmagliano.ecommerce.products.dto;

import br.com.zup.dmagliano.ecommerce.categories.Category;
import br.com.zup.dmagliano.ecommerce.categories.CategoryRepository;
import br.com.zup.dmagliano.ecommerce.common.ExistingId;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.products.Product;
import br.com.zup.dmagliano.ecommerce.products.ProductFeature;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductForm {

    @NotBlank
    private String name;
    @DecimalMin(value = "0.00", inclusive = false)
    @Positive
    private BigDecimal sellPrice;
    @Positive
    private Integer quantity;
    @NotBlank
    @Length(max = 1000)
    private String description;
    @Size(min = 3)
    @Valid
    private List<ProductFeatureForm> featureList = new ArrayList<>();
    @ExistingId(domainClass = Category.class, fieldName = "id")
    private Long categoryId;

    @Deprecated
    public ProductForm() {
    }

    public ProductForm(String name, BigDecimal sellPrice, Integer quantity, String description, List<ProductFeatureForm> featureList, Long categoryId) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.description = description;
        this.featureList.addAll(featureList);
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public List<ProductFeatureForm> getFeatureList() {
        return featureList;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Product toEntity(Customer customer,
                            CategoryRepository categoryRepository) {

        Product product = new Product(
                this.name,
                this.sellPrice,
                this.quantity,
                this.description,
                findCategoryById(categoryRepository, this.categoryId),
                customer
        );
        product.setFeatureList(toFeatureList(featureList, product));
        return product;
    }

    @Transactional
    public List<ProductFeature> toFeatureList(List<ProductFeatureForm> featureFormList, Product product) {
        List<ProductFeature> productFeatureList = featureFormList.stream().map(
                productFeatureForm -> new ProductFeature(productFeatureForm, product)
        ).collect(Collectors.toList());
        return productFeatureList;
    }

    public Category findCategoryById(CategoryRepository categoryRepository, Long id) {
        Optional<Category> category = Optional.ofNullable(
                categoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Categoria não encontrada, ou Id incorreto")));
        return category.get();
    }
}
