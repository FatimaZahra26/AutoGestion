package com.AutoStock.AutoStockVersion1.service;


import com.AutoStock.AutoStockVersion1.model.Rapport;

import java.io.IOException;
import java.util.List;

public interface RapportService {
    List<Rapport> getAllRapports();
    Rapport getRapportById(Long id);
    Rapport saveRapport(Rapport rapport);
    Rapport updateRapport(Long id, Rapport updatedRapport);
    void deleteRapport(Long id);

    List<Rapport> generateRapport(String type, Integer year, Integer month, Integer day);
}
