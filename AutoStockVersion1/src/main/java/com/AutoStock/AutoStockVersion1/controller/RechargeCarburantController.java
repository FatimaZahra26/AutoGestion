package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.model.RechargeCarburant;
import com.AutoStock.AutoStockVersion1.service.RechargeCarburantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recharge-carburant")
@CrossOrigin(origins = "http://localhost:4200")
public class RechargeCarburantController {

    @Autowired
    private RechargeCarburantService rechargeCarburantService;

    // Endpoint pour récupérer toutes les recharges de carburant
    @GetMapping
    public ResponseEntity<List<RechargeCarburant>> getAllRechargeCarburant() {
        List<RechargeCarburant> recharges = rechargeCarburantService.getAllRechargeCarburant();
        return new ResponseEntity<>(recharges, HttpStatus.OK);
    }

    // Endpoint pour récupérer une recharge de carburant par ID
    @GetMapping("/{id}")
    public ResponseEntity<RechargeCarburant> getRechargeCarburantById(@PathVariable("id") Long id) {
        RechargeCarburant recharge = rechargeCarburantService.getRechargeCarburantById(id);
        if (recharge != null) {
            return new ResponseEntity<>(recharge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour créer une nouvelle recharge de carburant
    @PostMapping
    public ResponseEntity<RechargeCarburant> createRechargeCarburant(@RequestBody RechargeCarburant rechargeCarburant) {
        RechargeCarburant createdRecharge = rechargeCarburantService.createRechargeCarburant(rechargeCarburant);
        return new ResponseEntity<>(createdRecharge, HttpStatus.CREATED);
    }

    // Endpoint pour mettre à jour une recharge de carburant existante
    @PutMapping("/{id}")
    public ResponseEntity<RechargeCarburant> updateRechargeCarburant(@PathVariable("id") Long id,
                                                                     @RequestBody RechargeCarburant rechargeCarburant) {
        RechargeCarburant updatedRecharge = rechargeCarburantService.updateRechargeCarburant(id, rechargeCarburant);
        if (updatedRecharge != null) {
            return new ResponseEntity<>(updatedRecharge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour supprimer une recharge de carburant par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRechargeCarburant(@PathVariable("id") Long id) {
        rechargeCarburantService.deleteRechargeCarburant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}