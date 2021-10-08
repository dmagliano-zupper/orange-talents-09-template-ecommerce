package br.com.zup.dmagliano.ecommerce.customer.dto;

import br.com.zup.dmagliano.ecommerce.customer.Customer;
import br.com.zup.dmagliano.ecommerce.security.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerForm {

    @NotBlank
    private String name;
    @Email @NotBlank
    private String email;
    @NotBlank @Size(min = 6)
    private String password;

    @Deprecated
    public CustomerForm() {
    }

    public CustomerForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {return name; }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Customer toEntity(PasswordService passwordService) {
        String encodedPassword = passwordService.encode(this.password);
        return new Customer(this.email, this.name, encodedPassword);
    }
}
