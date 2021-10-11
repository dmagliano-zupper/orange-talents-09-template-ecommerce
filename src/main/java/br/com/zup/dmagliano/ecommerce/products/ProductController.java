package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.categories.CategoryRepository;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.customers.CustomerRepository;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductForm;
import br.com.zup.dmagliano.ecommerce.security.LoggedCustomer;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ProductForm productForm, @AuthenticationPrincipal LoggedCustomer loggedCustomer){
        Customer customer = customerRepository.findByEmail(loggedCustomer.getUsername());
        Product product = productForm.toEntity(customer, categoryRepository);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

}
