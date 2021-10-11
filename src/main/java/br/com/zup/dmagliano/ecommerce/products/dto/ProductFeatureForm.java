package br.com.zup.dmagliano.ecommerce.products.dto;

import br.com.zup.dmagliano.ecommerce.products.ProductFeature;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ProductFeatureForm {

    @NotBlank
    private String feature;
    @NotBlank
    @Length(max = 200, min = 3)
    private String description;

    @Deprecated
    public ProductFeatureForm() {
    }

    public ProductFeatureForm(String feature, String description) {
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
