package br.com.zup.dmagliano.ecommerce.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomerDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
