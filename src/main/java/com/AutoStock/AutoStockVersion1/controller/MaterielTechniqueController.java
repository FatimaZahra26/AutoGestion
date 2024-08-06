package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.model.MaterielTechnique;
import com.AutoStock.AutoStockVersion1.service.MaterielTechniqueService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/materiel-technique")
@CrossOrigin(origins = "http://localhost:4200")
public class MaterielTechniqueController {

    @Value("${upload.dir.materieltech}")
    private String uploadDir;

    @Autowired
    private MaterielTechniqueService materielTechniqueService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @GetMapping("")
    public List<MaterielTechnique> getAllMateriels() {
        return materielTechniqueService.getAllMateriels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterielTechnique> getMaterielById(@PathVariable Long id) {
        MaterielTechnique materiel = materielTechniqueService.getMaterielById(id);
        if (materiel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materiel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMateriel(@PathVariable Long id) {
        materielTechniqueService.deleteMateriel(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createMaterielWithFiles(
            @RequestParam("description") String description,
            @RequestParam("caracteristiqueTechnique") String caracteristiqueTechnique,
            @RequestParam("dateEntree") String dateEntree,
            @RequestParam("dateSortie") String dateSortie,
            @RequestParam("marqueModel") String marqueModel,
            @RequestParam("service_affecte") String service_affecte,

            @RequestParam(value = "refFile", required = false) MultipartFile refFile,
            @RequestParam(value = "photoFile", required = false) MultipartFile photoFile) {

        LocalDateTime dateEntreeParsed;
        LocalDateTime dateSortieParsed;

        try {
            dateEntreeParsed = LocalDateTime.parse(dateEntree, DATE_FORMATTER);
            dateSortieParsed = LocalDateTime.parse(dateSortie, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid date format: " + e.getMessage()));
        }

        MaterielTechnique materiel = new MaterielTechnique();
        materiel.setDescription(description);
        materiel.setCaracteristiqueTechnique(caracteristiqueTechnique);
        materiel.setDateEntree(dateEntreeParsed.toLocalDate());
        materiel.setDateSortie(dateSortieParsed.toLocalDate());
        materiel.setMarqueModel(marqueModel);
        materiel.setServiceAffecte(service_affecte);

        Map<String, String> response = new HashMap<>();

        try {
            if (refFile != null && !refFile.isEmpty()) {
                String refFilePath = uploadFile(refFile);
                materiel.setRef(refFilePath);
                response.put("refFilePath", refFilePath);
            }

            if (photoFile != null && !photoFile.isEmpty()) {
                String photoFilePath = uploadFile(photoFile);
                materiel.setPhotoPath(photoFilePath);
                response.put("photoFilePath", photoFilePath);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "File upload failed: " + e.getMessage()));
        }

        materielTechniqueService.saveMateriel(materiel);

        response.put("message", "Materiel created successfully");
        return ResponseEntity.ok(response);
    }

    private String uploadFile(MultipartFile file) throws IOException {
        // Ensure the upload directory exists
        Path directoryPath = Paths.get(uploadDir);
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Use the original filename
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IOException("Invalid file name");
        }

        // Construct the file path
        Path filePath = directoryPath.resolve(originalFilename).normalize();

        // Convert the path to use forward slashes
        String filePathString = filePath.toString().replace("\\", "/");

        // Log the constructed file path
        System.out.println("Saving file to: " + filePathString);

        File serverFile = filePath.toFile();
        try {
            file.transferTo(serverFile);
            System.out.println("File saved successfully: " + filePathString);
        } catch (IOException e) {
            System.err.println("Failed to save file: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }

        // Return the relative path with forward slashes
        return filePathString;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateMaterielWithFiles(
            @PathVariable Long id,
            @RequestParam("description") String description,
            @RequestParam("caracteristiqueTechnique") String caracteristiqueTechnique,
            @RequestParam("dateEntree") String dateEntree,
            @RequestParam("dateSortie") String dateSortie,
            @RequestParam("marqueModel") String marqueModel,
            @RequestParam("service_affecte") String service_affecte,
            @RequestParam(value = "refFile", required = false) MultipartFile refFile,
            @RequestParam(value = "photoFile", required = false) MultipartFile photoFile) {

        LocalDateTime dateEntreeParsed = null;
        LocalDateTime dateSortieParsed = null;
        System.out.println("Updating materiel with ID: " + id);


        try {
            if (dateEntree != null && !dateEntree.isEmpty()) {
                dateEntreeParsed = LocalDateTime.parse(dateEntree, DATE_FORMATTER);
            }
            if (dateSortie != null && !dateSortie.isEmpty()) {
                dateSortieParsed = LocalDateTime.parse(dateSortie, DATE_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid date format: " + e.getMessage()));
        }

        MaterielTechnique existingMateriel = materielTechniqueService.getMaterielById(id);
        if (existingMateriel == null) {
            return ResponseEntity.notFound().build();
        }

        existingMateriel.setDescription(description);
        existingMateriel.setCaracteristiqueTechnique(caracteristiqueTechnique);
        existingMateriel.setServiceAffecte(service_affecte);
        if (dateEntreeParsed != null) {
            existingMateriel.setDateEntree(dateEntreeParsed.toLocalDate());
        }
        if (dateSortieParsed != null) {
            existingMateriel.setDateSortie(dateSortieParsed.toLocalDate());
        }
        existingMateriel.setMarqueModel(marqueModel);

        Map<String, String> response = new HashMap<>();

        try {
            if (refFile != null && !refFile.isEmpty()) {
                String refFilePath = uploadFile(refFile);
                existingMateriel.setRef(refFilePath);
                response.put("refFilePath", refFilePath);
            }

            if (photoFile != null && !photoFile.isEmpty()) {
                String photoFilePath = uploadFile(photoFile);
                existingMateriel.setPhotoPath(photoFilePath);
                response.put("photoFilePath", photoFilePath);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "File upload failed: " + e.getMessage()));
        }

        materielTechniqueService.saveMateriel(existingMateriel);
        response.put("message", "Materiel updated successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/count")
    public Long getMaterielCount(){
        return materielTechniqueService.getAllMateriels().stream().count();
    }
}
