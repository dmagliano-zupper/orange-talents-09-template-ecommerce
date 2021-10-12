package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.categories.CategoryRepository;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.customers.CustomerRepository;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductForm;
import br.com.zup.dmagliano.ecommerce.products.image.ImageUploadRequest;
import br.com.zup.dmagliano.ecommerce.products.image.UploaderFake;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductRatingForm;
import br.com.zup.dmagliano.ecommerce.security.LoggedCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UploaderFake uploaderFake;

    @PostMapping
    @Transactional
    public ResponseEntity createProduct(@RequestBody @Valid ProductForm productForm, @AuthenticationPrincipal LoggedCustomer loggedCustomer) {
        Customer customer = customerRepository.findByEmail(loggedCustomer.getUsername());
        Product product = productForm.toEntity(customer, categoryRepository);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/images")
    @Transactional
    public ResponseEntity addImage(@PathVariable("id") Long id, ImageUploadRequest imageUploadRequest,
                                   @AuthenticationPrincipal LoggedCustomer loggedCustomer) {
        /*
          1- Enviar imagens para hospedagem
          2- Obter os links das imagens
          3- Associar os links com os produtos em questão
          4- Carregar o produto
          5- Atualizar nova versão do produto
        */
        Customer customer = getCustomer(loggedCustomer);

        Set<String> links = uploaderFake.send(imageUploadRequest.getImages());

        Product product = getProduct(id);
        if (!product.isOwner(customer)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        product.setImages(links);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/ratings")
    @Transactional
    public ResponseEntity create(@PathVariable("id") Long id, @RequestBody @Valid ProductRatingForm ratingForm,
                                 @AuthenticationPrincipal LoggedCustomer loggedCustomer) {
        Customer customer = getCustomer(loggedCustomer);
        Product product = getProduct(id);

        ProductRating productRating = ratingForm.toEntity(product, customer);
        product.addRatings(productRating);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }


    private Customer getCustomer(LoggedCustomer loggedCustomer) {
        return customerRepository.findByEmail(loggedCustomer.getUsername());
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Produto não encontrado ou id invalida"));
    }

}

