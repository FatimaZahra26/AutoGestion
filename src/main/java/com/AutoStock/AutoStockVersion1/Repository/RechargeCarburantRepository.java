package com.AutoStock.AutoStockVersion1.Repository;

import com.AutoStock.AutoStockVersion1.model.RechargeCarburant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RechargeCarburantRepository extends JpaRepository<RechargeCarburant, Long> {

}
