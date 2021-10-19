package br.com.zup.dmagliano.ecommerce.others;

import br.com.zup.dmagliano.ecommerce.purchase.dto.PurchaseOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@FeignClient(value = "invoices", url = "localhost:8080/invoices")
public interface Invoice {

    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = "application/json")
    Void process(@RequestBody PurchaseOrderDto purchaseOrderDto);
}
