package br.com.zup.dmagliano.ecommerce.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseOrder, Long> {
}
