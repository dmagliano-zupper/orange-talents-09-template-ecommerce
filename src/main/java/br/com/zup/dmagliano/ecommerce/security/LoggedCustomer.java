package br.com.zup.dmagliano.ecommerce.security;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class LoggedCustomer implements UserDetails {

    private Customer customer;
    private User springUserDetails;

    public LoggedCustomer(@NotNull @Valid Customer customer) {
        this.customer = customer;
        springUserDetails = new User(customer.getEmail(), customer.getPassword(), List.of());
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return springUserDetails.getAuthorities();
    }



    public String getPassword() {
        return springUserDetails.getPassword();
    }



    public String getUsername() {
        return springUserDetails.getUsername();
    }



    public boolean isEnabled() {
        return springUserDetails.isEnabled();
    }



    public boolean isAccountNonExpired() {
        return springUserDetails.isAccountNonExpired();
    }



    public boolean isAccountNonLocked() {
        return springUserDetails.isAccountNonLocked();
    }



    public boolean isCredentialsNonExpired() {
        return springUserDetails.isCredentialsNonExpired();
    }



    public Customer get() {
        return customer;
    }

}
