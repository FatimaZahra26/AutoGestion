    package com.AutoStock.AutoStockVersion1.controller;


    import com.AutoStock.AutoStockVersion1.model.Vehicule;
    import com.AutoStock.AutoStockVersion1.service.VehiculeService;
    import jakarta.annotation.Resource;
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
    import java.nio.file.Path;
    import java.nio.file.Paths;
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
        @GetMapping("/downloadFile/{fileName:.+}")
        public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
            // Chargez le fichier en utilisant son chemin absolu
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            UrlResource resource;
            try {
                   resource = new UrlResource(filePath.toUri());
                // Vérifiez si le fichier existe et est lisible
                if (resource.exists() && resource.isReadable()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                            .body((Resource) resource);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    }

