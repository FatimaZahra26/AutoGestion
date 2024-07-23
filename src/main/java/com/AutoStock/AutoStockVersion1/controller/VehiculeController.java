    package com.AutoStock.AutoStockVersion1.controller;
    import org.springframework.core.io.Resource;
    import org.springframework.core.io.UrlResource;

    import com.AutoStock.AutoStockVersion1.model.Vehicule;
    import com.AutoStock.AutoStockVersion1.service.VehiculeService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
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
    import java.sql.SQLException;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/vehicules")
    public class VehiculeController {

        @Autowired
        private VehiculeService vehiculeService;

        @GetMapping
        public List<Vehicule> getAllVehicules() {
            return vehiculeService.getAllVehicules();
        }

        @PutMapping("/{id}")
        public Vehicule updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehicule) {
            vehicule.setIdVehicule(id);
            return vehiculeService.updateVehicule(vehicule);
        }
        @PostMapping
        public Vehicule createVehicule(@RequestBody Vehicule vehicule) {
            return vehiculeService.saveVehicule(vehicule);
        }

        @DeleteMapping("/{id}")
        public void deleteVehicule(@PathVariable Long id) {
            vehiculeService.deleteVehicule(id);
        }
        @GetMapping("/reparationCounts")
        public ResponseEntity<Map<Long, Integer>> getReparationCounts() {
            Map<Long, Integer> reparationCounts = vehiculeService.getReparationCountByVehicule();
            return ResponseEntity.ok(reparationCounts);
        }

        @Value("${upload.dir}") // Injecte la valeur de 'upload.dir' depuis application.properties
        private String uploadDir;

        @PostMapping("/uploadFile")
        public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
            Map<String, String> response = new HashMap<>();
            try {
                // Assurez-vous que le répertoire de destination existe
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs(); // Créez le répertoire s'il n'existe pas
                }

                // Enregistrez le fichier dans le répertoire de destination
                String filePath = uploadDir + File.separator + file.getOriginalFilename();
                file.transferTo(new File(filePath));

                response.put("filePath", filePath);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                e.printStackTrace();
                response.put("error", "Failed to upload file: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
        @GetMapping("/{filename:.+}")
        public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            try {
                Resource resource = new UrlResource(filePath.toUri());

                // Vérifiez si la ressource existe et peut être lue
                if (resource.exists() && resource.isReadable()) {
                    // Déterminez le type MIME du fichier
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
        public Long countVehicules() throws SQLException {
            return vehiculeService.countVehicules();
        }
        @GetMapping("/vehicule/{id}")
        public Vehicule getVehicule(@PathVariable Long id) {
            return vehiculeService.getVehiculeById(id);
        }
        @GetMapping("/top-vehicles")
        public List<Object[]> getTopVehiclesWithRepairs() {
            return vehiculeService.getTopVehiclesWithRepairs();
        }
    }

