package br.com.zup.dmagliano.ecommerce.categories.dto;

import br.com.zup.dmagliano.ecommerce.categories.Category;
import br.com.zup.dmagliano.ecommerce.categories.CategoryRepository;
import br.com.zup.dmagliano.ecommerce.common.ExistingId;
import br.com.zup.dmagliano.ecommerce.common.UniqueValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.Nullable;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class CategoryForm {

    private CategoryRepository categoryRepository;

    @NotBlank
    @Length(min = 3)
    @UniqueValue(fieldName = "name", domainClass = Category.class)
    private String name;

    private Long parentCategoryId;

    public CategoryForm() {
    }

    public String getName() {
        return name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public Category toEntity(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        Category category = new Category(this.name);
        if (parentCategoryId != null) {
            category.setParentCategory(getCategoryFromId(parentCategoryId, this.categoryRepository));
        }
        return category;
    }

    public Category getCategoryFromId(Long parentCategoryId, CategoryRepository categoryRepository) {
        Optional<Category> categoryOptional = categoryRepository.findById(parentCategoryId);
        if (!categoryOptional.isPresent()) {
            Assert.isTrue(categoryOptional.isPresent(), "Categoria mãe não encontrada ou id incorreto");
        }
        return categoryOptional.get();
    }

}