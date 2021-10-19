package br.com.zup.dmagliano.ecommerce.transaction;

import br.com.zup.dmagliano.ecommerce.purchase.PurchaseOrder;

public interface GatewayTransactionResponse {

    Transaction toTransaction(PurchaseOrder purchaseOrder);
}
