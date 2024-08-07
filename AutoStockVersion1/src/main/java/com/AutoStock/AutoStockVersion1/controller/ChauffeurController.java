package com.AutoStock.AutoStockVersion1.controller;


import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import com.AutoStock.AutoStockVersion1.service.ChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/chauffeurs")
@CrossOrigin(origins = "http://localhost:4200")
public class ChauffeurController {
    @Autowired
    private ChauffeurService chauffeurService;

    @GetMapping
    public List<Chauffeur> getChauffeursList() throws SQLException {
        return chauffeurService.getChauffeurList();
    }

    @GetMapping("/count")
    public long countChauffeurs() throws SQLException {
        return chauffeurService.countChauffeurs();
    }

    @GetMapping("/rapport/{id_vehicule}")
    public ResponseEntity<List<Chauffeur>> getAllChaufeursByVehicule(@PathVariable Long id_vehicule){
        return new ResponseEntity<>(chauffeurService.getAllChauffeursByVehiculeId(id_vehicule), HttpStatus.OK);
    }
}
