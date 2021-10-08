package br.com.zup.dmagliano.ecommerce.customers.dto;

import br.com.zup.dmagliano.ecommerce.common.UniqueValue;
import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.security.PlainPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerForm {

    @NotBlank
    private String name;
    @Email
    @NotBlank
    @UniqueValue(fieldName = "email", domainClass = Customer.class)
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

    public Customer toEntity() {
        PlainPassword plainPassword = new PlainPassword(password);
        return new Customer(this.email, this.name, plainPassword);
    }
}
