package br.com.zup.dmagliano.ecommerce.customer;

import br.com.zup.dmagliano.ecommerce.security.PlainPassword;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Customer {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    private LocalDateTime createDate;

    public Customer(String email, String name, PlainPassword plainPassword) {
        this.email = email;
        this.name = name;
        this.password = plainPassword.encode();
        this.createDate = LocalDateTime.now();
    }
    @Deprecated
    public Customer() {
    }


}
