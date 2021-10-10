package br.com.zup.dmagliano.ecommerce.common;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.security.LoggedCustomer;
import br.com.zup.dmagliano.ecommerce.security.UserDetailsMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class AppCustomerDetailMapper implements UserDetailsMapper {

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new LoggedCustomer((Customer) shouldBeASystemUser);
    }
}
