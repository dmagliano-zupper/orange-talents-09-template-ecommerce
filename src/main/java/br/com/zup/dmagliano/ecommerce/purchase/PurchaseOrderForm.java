package br.com.zup.dmagliano.ecommerce.purchase;

public class PurchaseOrderForm {

    private Integer quantity;

    private PaymentMethod paymentMethod;

    public Integer getQuantity() {
        return quantity;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
