package br.com.zup.dmagliano.ecommerce.products.image;

import br.com.zup.dmagliano.ecommerce.products.Product;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Product product;

    @URL
    @NotBlank
    private String link;

    @Deprecated
    public ProductImage() {
    }

    public ProductImage(Product product, String link) {
        this.product = product;
        this.link = link;
    }

}
