package br.com.zup.dmagliano.ecommerce.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody @Valid CategoryForm categoryForm) {
        Category category = categoryForm.toEntity(categoryRepository);
        categoryRepository.save(category);

    }

}
