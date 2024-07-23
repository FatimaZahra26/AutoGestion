package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.model.Reparation;
import com.AutoStock.AutoStockVersion1.service.ReparationService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/reparations")
public class ReparationController {

    private final ReparationService reparationService;
    @Value("${upload.dir.reparations}")
    private String uploadDir;

    @Autowired
    public ReparationController(ReparationService reparationService) {
        this.reparationService = reparationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reparation> getReparationById(@PathVariable Long id) {
        Reparation reparation = reparationService.getReparationById(id);
        if (reparation != null) {
            return ResponseEntity.ok(reparation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
    public ResponseEntity<Reparation> updateReparation(
            @PathVariable("id") Long id,
            @RequestBody Reparation reparation) throws SQLException {
        System.out.println("Received reparation data: " + reparation);
        System.out.println("Received ID: " + id);

        Reparation updatedReparation = reparationService.updateReparation(id, reparation);
        return ResponseEntity.ok(updatedReparation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReparation(@PathVariable Long id) {
        reparationService.deleteReparation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/uploadRepair")
    public ResponseEntity<String> uploadRepairFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File name is null");
        }

        String extension = FilenameUtils.getExtension(fileName);
        if (!"pdf".equalsIgnoreCase(extension) && !"jpg".equalsIgnoreCase(extension)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type");
        }

        Path path = Paths.get(uploadDir + fileName);
        try {
            Files.createDirectories(path.getParent()); // Ensure directory exists
            Files.write(path, file.getBytes());
            // Construct URL based on your server setup
            String fileUrl = uploadDir + fileName;
            return ResponseEntity.ok(fileUrl); // Return the relative URL
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}
