package com.AutoStock.AutoStockVersion1.Repository;

import com.AutoStock.AutoStockVersion1.model.MaterielBureautique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterielBureautiqueRepository extends JpaRepository<MaterielBureautique, Long> {
    @Query("SELECT COUNT(m) FROM MaterielBureautique m")
    Long countMateriels();
}
