package br.com.zup.dmagliano.ecommerce.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<PurchaseOrder, UUID> {
}
