package com.seta.tis4.model.repositories;

import com.seta.tis4.model.entities.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    List<Purchase> findPurchaseByCustomer_Id(UUID id);

    List<Purchase> findPurchaseByEmployee_Id(UUID id);
}