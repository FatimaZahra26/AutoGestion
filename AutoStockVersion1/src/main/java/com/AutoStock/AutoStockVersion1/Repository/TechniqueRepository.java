package com.AutoStock.AutoStockVersion1.Repository;

import com.AutoStock.AutoStockVersion1.model.Technique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechniqueRepository extends JpaRepository<Technique, Long> {
}
