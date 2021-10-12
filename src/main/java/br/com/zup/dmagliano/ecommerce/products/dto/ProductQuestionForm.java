package br.com.zup.dmagliano.ecommerce.products.dto;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.products.Product;
import br.com.zup.dmagliano.ecommerce.products.ProductQuestion;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class ProductQuestionForm {

    @NotBlank
    @JsonProperty
    private String title;

    @Deprecated
    public ProductQuestionForm() {
    }

    public ProductQuestionForm(String title) {
        this.title = title;
    }

    public ProductQuestion toEntity(Product product, Customer customer) {
        return new ProductQuestion(this.title, product, customer);
    }
}
