package br.com.zup.dmagliano.ecommerce.products.dto;

public class ProductRatingDto {

    private Integer rating;

    private String title;

    private String description;

    public ProductRatingDto(Integer rating, String title, String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
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
