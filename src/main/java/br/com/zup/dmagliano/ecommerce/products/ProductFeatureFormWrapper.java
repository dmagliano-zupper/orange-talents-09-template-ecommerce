package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.products.dto.ProductFeatureForm;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class ProductFeatureFormWrapper {

    @Length(min = 3)
    private List<ProductFeatureForm> productFeatureForms;

    public List<ProductFeatureForm> getList() {
        return productFeatureForms;
    }
}
