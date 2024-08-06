package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.model.Mission;
import com.AutoStock.AutoStockVersion1.model.Vehicule;
import com.AutoStock.AutoStockVersion1.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/missions")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @PostMapping("/add")
    public ResponseEntity<?> addMission(@RequestBody Mission mission) {
        try {
            missionService.addMission(mission);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Mission ajoutée avec succès\"}");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'ajout de la mission");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
        Mission mission = missionService.getMissionById(id);
        if (mission != null) {
            return ResponseEntity.ok(mission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMission(@PathVariable Long id, @RequestBody Mission mission) {
        return missionService.updateMission(id, mission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        try {
            missionService.deleteMission(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Log the error for debugging
            Logger.getLogger(MissionController.class.getName()).log(Level.SEVERE, null, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/available")
    public List<Vehicule> getAvailableVehicles() throws SQLException {
               return missionService.getUnassignedVehicles();
    }


    @GetMapping
    public List<Mission> getAllMissions(){
        return missionService.getAllMissions();
    }}