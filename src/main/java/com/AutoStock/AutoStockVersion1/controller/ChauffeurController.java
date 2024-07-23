package com.AutoStock.AutoStockVersion1.controller;


import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import com.AutoStock.AutoStockVersion1.service.ChauffeurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/chauffeurs")
@CrossOrigin(origins = "http://localhost:4200")
public class ChauffeurController {
    @Autowired
    private ChauffeurService chauffeurService;

    private static final Logger logger = LoggerFactory.getLogger(ChauffeurController.class);

    @GetMapping
    public List<Chauffeur> getChauffeursList() throws SQLException {
        return chauffeurService.getChauffeurList();
    }

    @GetMapping("/count")
    public long countChauffeurs() throws SQLException {
        return chauffeurService.countChauffeurs();
    }
    @PostMapping("/add")
    public ResponseEntity<String> addChauffeur(@RequestBody Map<String, Object> payload) {
        try {
            Chauffeur chauffeur = new ObjectMapper().convertValue(payload.get("chauffeur"), Chauffeur.class);
            String immatriculation = (String) payload.get("immatriculation");
            return chauffeurService.addChauffeur(chauffeur, immatriculation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du chauffeur.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChauffeur(@PathVariable Long id) {
        chauffeurService.deleteChauffeur(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateChauffeur(
            @PathVariable("id") Long idChauffeur,
            @RequestParam("immatriculation") String immatriculation,
            @RequestBody Chauffeur chauffeur) throws SQLException {

        chauffeur.setIdChauffeur(idChauffeur);

        return chauffeurService.updateChauffeur(chauffeur, immatriculation);
    }


}
