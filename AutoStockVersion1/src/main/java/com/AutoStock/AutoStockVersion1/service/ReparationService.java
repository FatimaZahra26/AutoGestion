package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import com.AutoStock.AutoStockVersion1.model.Reparation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReparationService {

    List<Reparation> getAllReparations();

    Reparation saveReparation(Reparation reparation);

    Reparation updateReparation(Long id, Reparation updatedReparation);

    void deleteReparation(Long id);
    public String uploadFile(MultipartFile file);
    List<Reparation> getAllReparationByVehiculeId(Long VehiculeId);
}
