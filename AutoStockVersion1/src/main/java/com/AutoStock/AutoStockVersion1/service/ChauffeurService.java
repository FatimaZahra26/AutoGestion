package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface ChauffeurService {
    List<Chauffeur>getChauffeurList() throws SQLException;
    Long countChauffeurs() throws SQLException;
    List<Chauffeur> getAllChauffeursByVehiculeId(Long VehiculeId);
}
