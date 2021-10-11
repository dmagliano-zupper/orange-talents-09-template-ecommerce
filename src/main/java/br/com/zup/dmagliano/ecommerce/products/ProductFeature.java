package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.products.dto.ProductFeatureForm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "feature")
public class ProductFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feature;

    private String description;

    @ManyToOne
    private Product product;

    @Deprecated
    public ProductFeature() {
    }

    public ProductFeature(String feature, String description, Product product) {
        this.feature = feature;
        this.description = description;
        this.product = product;
    }

    public ProductFeature(ProductFeatureForm productFeatureForm, Product product) {
        this.feature = productFeatureForm.getFeature();
        this.description = productFeatureForm.getDescription();
        this.product = product;
    }
}
