package br.com.zup.dmagliano.ecommerce.common.handlers;

public class ErrorDTO {

    String field;
    String error;

    public ErrorDTO(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }


}
