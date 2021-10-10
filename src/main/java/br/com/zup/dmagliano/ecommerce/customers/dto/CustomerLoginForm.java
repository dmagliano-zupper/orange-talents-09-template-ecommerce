package br.com.zup.dmagliano.ecommerce.customers.dto;

public class CustomerLoginForm {

    private String email;
    private String password;

    public CustomerLoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
