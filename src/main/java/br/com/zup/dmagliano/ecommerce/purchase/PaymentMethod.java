package br.com.zup.dmagliano.ecommerce.purchase;

public enum PaymentMethod {
    PAYPAL("paypal.com"),
    PAGSEGURO("pagseguro.com");

    public final String address;

    private PaymentMethod(String address){
        this.address = address;
    }
}
