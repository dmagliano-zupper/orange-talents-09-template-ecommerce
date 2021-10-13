package br.com.zup.dmagliano.ecommerce.products;

import br.com.zup.dmagliano.ecommerce.categories.CategoryRepository;
import br.com.zup.dmagliano.ecommerce.common.EmailSenderFake;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.customers.CustomerRepository;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductDetailsDto;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductForm;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductQuestionForm;
import br.com.zup.dmagliano.ecommerce.products.image.ImageUploadRequest;
import br.com.zup.dmagliano.ecommerce.products.image.UploaderFake;
import br.com.zup.dmagliano.ecommerce.products.dto.ProductRatingForm;
import br.com.zup.dmagliano.ecommerce.security.LoggedCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @Autowired
    EmailSenderFake emailSenderFake;



    @PostMapping
    @Transactional
    public ResponseEntity createProduct(@RequestBody @Valid ProductForm productForm, @AuthenticationPrincipal LoggedCustomer loggedCustomer) {
        Customer customer = getCustomerByEmail(loggedCustomer);

        Product product = productForm.toEntity(customer, categoryRepository);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/images")
    @Transactional
    public ResponseEntity addImage(@PathVariable("id") Long id, ImageUploadRequest imageUploadRequest,
                                   @AuthenticationPrincipal LoggedCustomer loggedCustomer) {

        Customer customer = getCustomerByEmail(loggedCustomer);

        Set<String> links = uploaderFake.send(imageUploadRequest.getImages());

        Product product = getProductById(id);
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
        Customer customer = getCustomerByEmail(loggedCustomer);
        Product product = getProductById(id);

        ProductRating productRating = ratingForm.toEntity(product, customer);
        product.addRatings(productRating);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/{id}/questions")
    @Transactional
    public ResponseEntity create(@PathVariable("id") Long id, @RequestBody @Valid ProductQuestionForm questionForm,
                                 @AuthenticationPrincipal LoggedCustomer loggedCustomer) {

        Customer customer = getCustomerByEmail(loggedCustomer);
        Product product = getProductById(id);

        ProductQuestion productQuestion = questionForm.toEntity(product, customer);
        product.addQuestion(productQuestion);
        emailSenderFake.notifyQuestion(productQuestion);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ProductDetailsDto> details(@PathVariable("id") Long id){
        Product product = getProductById(id);
        ProductDetailsDto productDetailsDto = product.toProductDetails();
        return ResponseEntity.ok().body(productDetailsDto);
    }


    private Customer getCustomerByEmail(LoggedCustomer loggedCustomer) {
        return customerRepository.findByEmail(loggedCustomer.getUsername()).orElseThrow(
                () -> new EntityNotFoundException("Usuário com email informado não encontrado"));
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Produto não encontrado ou id invalida"));
    }


}

