package br.com.zup.dmagliano.ecommerce.customer;

import br.com.zup.dmagliano.ecommerce.customer.dto.CustomerForm;
import br.com.zup.dmagliano.ecommerce.security.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordService passwordService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CustomerForm customerForm) {
        Customer customer = customerForm.toEntity(passwordService);
        customerRepository.save(customer);
        return ResponseEntity.ok().build();
    }

}
