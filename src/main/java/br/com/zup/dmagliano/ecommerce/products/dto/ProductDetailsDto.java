package br.com.zup.dmagliano.ecommerce.products.dto;

import br.com.zup.dmagliano.ecommerce.products.ProductRatingSet;

import java.math.BigDecimal;
import java.util.Set;

public class ProductDetailsDto {

    private String name;

    private BigDecimal price;

    private Set<String> productImages;

    private Set<ProductFeatureDto> featureSet;

    private String description;

    private Set<ProductQuestionDto> questionsSet;

    private ProductRatingSet productRatingSet;

    @Deprecated
    public ProductDetailsDto() {
    }

    public ProductDetailsDto(String name, BigDecimal price, Set<String> productImages, Set<ProductFeatureDto> featureDtoSet,
                             String description, Set<ProductQuestionDto> questionsSet,
                             ProductRatingSet ratingSet) {
        this.name = name;
        this.price = price;
        this.productImages = productImages;
        this.featureSet = featureDtoSet;
        this.description = description;
        this.questionsSet = questionsSet;
        this.productRatingSet = ratingSet;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Set<String> getProductImages() {
        return productImages;
    }

    public Set<ProductFeatureDto> getFeatureSet() {
        return featureSet;
    }

    public String getDescription() {
        return description;
    }

    public Set<ProductQuestionDto> getQuestionsSet() {
        return questionsSet;
    }

    public ProductRatingSet getProductRatingSet() {
        return productRatingSet;
    }
}
