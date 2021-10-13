package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.products.dto.ProductRatingDto;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductRatingSet {

    private Set<ProductRatingDto> productRatingDto;

    private Double productMedianRating;

    public ProductRatingSet(Set<ProductRatingDto> productRatingDto, Double productMedianRating) {
        this.productRatingDto = productRatingDto;
        this.productMedianRating = productMedianRating;
    }

    public ProductRatingSet(Set<ProductRating> productRating) {
        this.productRatingDto = toRatingDtoSet(productRating);
        this.productMedianRating = getMedianRating(productRating);
    }


    private Set<ProductRatingDto> toRatingDtoSet(Set<ProductRating> productRatings){
        return productRatings.stream().map(ProductRating::toRatingDto)
                .collect(Collectors.toSet());
    }

    private Double getMedianRating(Set<ProductRating> ratingSet) {
        Integer ratingSum = ratingSet.stream().mapToInt(rating -> rating.getRating()).sum();
        return Double.valueOf(ratingSum) / ratingSet.size();
    }

    public Set<ProductRatingDto> getProductRatingDto() {
        return productRatingDto;
    }

    public Double getProductMedianRating() {
        return productMedianRating;
    }
}
