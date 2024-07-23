package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.model.Rapport;
import com.AutoStock.AutoStockVersion1.service.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rapport")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping
    public List<Rapport> getAllRapports() {
        return rapportService.getAllRapports();
    }

    @GetMapping("/{id}")
    public Rapport getRapportById(@PathVariable Long id) {
        return rapportService.getRapportById(id);
    }

    @PostMapping
    public Rapport createRapport(@RequestBody Rapport rapport) {
        return rapportService.saveRapport(rapport);
    }

    @PutMapping("/{id}")
    public Rapport updateRapport(@PathVariable Long id, @RequestBody Rapport rapport) {
        return rapportService.updateRapport(id, rapport);
    }

    @DeleteMapping("/{id}")
    public void deleteRapport(@PathVariable Long id) {
        rapportService.deleteRapport(id);
    }
    @GetMapping("/generate")
    public List<Rapport> generateReport(
            @RequestParam String type,
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Integer day) {
        return rapportService.generateRapport(type, year, month, day);
    }
}


