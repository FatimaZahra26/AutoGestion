package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface ChauffeurService {
    public List<Chauffeur>getChauffeurList() throws SQLException;
    public Long countChauffeurs() throws SQLException;
    public ResponseEntity<String> addChauffeur(Chauffeur chauffeur, String immatriculation);
    public ResponseEntity<String> updateChauffeur(Chauffeur chauffeur, String immatriculation) throws SQLException;
    public ResponseEntity<String> deleteChauffeur(Long id) ;
    }
