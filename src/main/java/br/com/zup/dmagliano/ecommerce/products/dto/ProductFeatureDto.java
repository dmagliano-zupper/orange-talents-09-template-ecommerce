package br.com.zup.dmagliano.ecommerce.products.dto;

public class ProductFeatureDto {

    private String feature;
    private String description;

    @Deprecated
    public ProductFeatureDto() {
    }

    public ProductFeatureDto(String feature, String description) {
        this.feature = feature;
        this.description = description;
    }

    public String getFeature() {
        return feature;
    }

    public String getDescription() {
        return description;
    }
}
