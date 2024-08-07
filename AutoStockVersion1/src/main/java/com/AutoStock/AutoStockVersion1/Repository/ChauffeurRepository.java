package com.AutoStock.AutoStockVersion1.Repository;

import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChauffeurRepository  extends JpaRepository<Chauffeur, Long> {
    List<Chauffeur> findAllByVehiculeId(Long vehiculeId);
}
