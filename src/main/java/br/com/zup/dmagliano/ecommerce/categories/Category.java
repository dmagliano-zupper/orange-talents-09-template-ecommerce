package br.com.zup.dmagliano.ecommerce.categories;

import br.com.zup.dmagliano.ecommerce.common.UniqueValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @Deprecated
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public void setParentCategory(Category category) {
        this.parentCategory = category;
    }
}
