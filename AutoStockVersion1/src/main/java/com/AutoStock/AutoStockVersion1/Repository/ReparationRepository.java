package com.AutoStock.AutoStockVersion1.Repository;


import com.AutoStock.AutoStockVersion1.model.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReparationRepository extends JpaRepository<Reparation, Long> {
}
