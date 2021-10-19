package br.com.zup.dmagliano.ecommerce.transaction.enums;

public enum PagSeguroTransactionStatus {

    ERRO,
    SUCESSO;

    public TransactionPaymentStatus getTransactionStatus(){
        if (this.equals(SUCESSO)){
            return TransactionPaymentStatus.SUCCESS;
        }
        return TransactionPaymentStatus.ERROR;
    }

}
