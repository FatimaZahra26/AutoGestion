package com.AutoStock.AutoStockVersion1.Repository;

import com.AutoStock.AutoStockVersion1.model.RechargeCarburant;
import com.AutoStock.AutoStockVersion1.model.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RechargeCarburantRepository extends JpaRepository<RechargeCarburant, Long> {
    List<RechargeCarburant> findAllByVehiculeId(Long vehiculeId);
}
