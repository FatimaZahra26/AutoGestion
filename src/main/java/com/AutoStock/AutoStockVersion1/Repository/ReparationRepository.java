package com.AutoStock.AutoStockVersion1.Repository;


import com.AutoStock.AutoStockVersion1.model.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReparationRepository extends JpaRepository<Reparation, Long> {

}
