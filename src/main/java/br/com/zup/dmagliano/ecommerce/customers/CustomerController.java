package br.com.zup.dmagliano.ecommerce.customers;

import br.com.zup.dmagliano.ecommerce.customers.dto.CustomerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CustomerForm customerForm) {
        Customer customer = customerForm.toEntity();
        customerRepository.save(customer);
        return ResponseEntity.ok().build();
    }

}
