package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Reparation;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReparationService {

    List<Reparation> getAllReparations();

    Reparation saveReparation(Reparation reparation);

    Reparation updateReparation(Long id, Reparation updatedReparation) throws SQLException;
    public Map<String, BigDecimal> getTotalCostsByRepairType() throws SQLException;

    void deleteReparation(Long id);
    public String uploadFile(MultipartFile file);
    Reparation getReparationById(Long id);

}
