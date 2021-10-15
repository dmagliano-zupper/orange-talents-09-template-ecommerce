package br.com.zup.dmagliano.ecommerce.common;

import br.com.zup.dmagliano.ecommerce.customers.Customer;
import br.com.zup.dmagliano.ecommerce.customers.CustomerRepository;
import br.com.zup.dmagliano.ecommerce.products.ProductQuestion;
import br.com.zup.dmagliano.ecommerce.purchase.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderFake {

    @Autowired
    CustomerRepository customerRepository;

    public void notifyQuestion(ProductQuestion productQuestion) {

        String email = productQuestion.getCustomer().getEmail();
        String name = productQuestion.getCustomer().getName();
        String productName = productQuestion.getProduct().getName();

        System.out.println("##########################################################");
        System.out.println("Email enviado para " + email);
        System.out.println("Olá " + name + " há uma nova pergunta sobre " + productName);
        System.out.println("##########################################################");
    }

    public void notifyOrder(PurchaseOrder order) {

        Customer seller = order.getSeller();
        String sellerEmail = seller.getEmail();
        String sellerName = seller.getName();
        String productName = order.getProduct().getName();
        String orderId = order.getOrderId().toString();

        System.out.println("##########################################################");
        System.out.println("Email enviado para " + sellerEmail);
        System.out.println("Olá " + sellerName + " você vendeu " + productName);
        System.out.println("Aguarde a confirmação do pagamento");
        System.out.println("Id da venda :" + orderId);
        System.out.println("##########################################################");
    }

}
