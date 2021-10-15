package br.com.zup.dmagliano.ecommerce.products.dto;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.products.Product;
import br.com.zup.dmagliano.ecommerce.products.ProductRating;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRatingForm {

    @Min(0)
    @Max(5)
    private Integer rating;
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    @Length(max = 500)
    private String description;

    @Deprecated
    public ProductRatingForm() {
    }

    public ProductRatingForm(Integer rating, String title, String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
    }

    public ProductRating toEntity(Product product, Customer customer) {
        return new ProductRating(this.rating,this.title,this.description,product,customer);
    }

    public Integer getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
