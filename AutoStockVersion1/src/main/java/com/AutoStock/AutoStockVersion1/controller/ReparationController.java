package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import com.AutoStock.AutoStockVersion1.model.Reparation;
import com.AutoStock.AutoStockVersion1.service.ReparationService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reparations")
public class ReparationController {

    private final ReparationService reparationService;
    private static final String UPLOAD_DIR = "C:\\Users\\MON PC\\Downloads\\AutoStockVersion1\\AutoStockVersion1\\uploads\\reparations";

    @Autowired
    public ReparationController(ReparationService reparationService) {
        this.reparationService = reparationService;
    }

    @GetMapping
    public List<Reparation> getAllReparations() {
        return reparationService.getAllReparations();
    }

    @PostMapping
    public ResponseEntity<Reparation> addReparation(@RequestBody Reparation reparation) {
        Reparation savedReparation = reparationService.saveReparation(reparation);
        return ResponseEntity.ok(savedReparation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reparation> updateReparation(@PathVariable Long id, @RequestBody Reparation updatedReparation) {
        Reparation updated = reparationService.updateReparation(id, updatedReparation);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReparation(@PathVariable Long id) {
        reparationService.deleteReparation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Ensure the upload directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Resolve the file name to prevent overwrite
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Check if file with same name already exists
            if (Files.exists(filePath)) {
                // Append a timestamp or UUID to avoid overwriting existing files
                String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
                String fileExtension = FilenameUtils.getExtension(fileName);
                String newFileName = fileNameWithoutExtension + "_" + System.currentTimeMillis() + "." + fileExtension;
                filePath = uploadPath.resolve(newFileName);
            }

            // Save the file to the upload directory
            Files.copy(file.getInputStream(), filePath);

            String fileUrl = filePath.toAbsolutePath().toString();
            response.put("fileUrl", fileUrl);

            return ResponseEntity.ok(response);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            response.put("error", "Could not upload the file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/rapport/{id_vehicule}")
    public ResponseEntity<List<Reparation>> getAllReparationByVehicule(@PathVariable Long id_vehicule){
        return new ResponseEntity<>(reparationService.getAllReparationByVehiculeId(id_vehicule), HttpStatus.OK);
    }

}