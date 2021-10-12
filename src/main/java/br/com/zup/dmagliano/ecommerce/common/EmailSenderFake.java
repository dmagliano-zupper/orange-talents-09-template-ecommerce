package br.com.zup.dmagliano.ecommerce.common;

import br.com.zup.dmagliano.ecommerce.products.ProductQuestion;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderFake {

    public void notifyQuestion(ProductQuestion productQuestion) {

        String email = productQuestion.getCustomer().getEmail();
        String name = productQuestion.getCustomer().getName();
        String productName = productQuestion.getProduct().getName();

        System.out.println("##########################################################");
        System.out.println("Email enviado para " + email);
        System.out.println("Olá " + name + " há uma nova pergunta sobre "+ productName);
        System.out.println("##########################################################");
    }
}
