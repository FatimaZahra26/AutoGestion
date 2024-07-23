package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Reparation;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface ReparationService {

    List<Reparation> getAllReparations();

    Reparation saveReparation(Reparation reparation);

    Reparation updateReparation(Long id, Reparation updatedReparation) throws SQLException;

    void deleteReparation(Long id);
    public String uploadFile(MultipartFile file);
    Reparation getReparationById(Long id);
}
