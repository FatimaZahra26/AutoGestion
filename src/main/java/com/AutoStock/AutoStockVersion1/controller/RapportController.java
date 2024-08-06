package com.AutoStock.AutoStockVersion1.controller;
import com.AutoStock.AutoStockVersion1.model.Mission;
import com.AutoStock.AutoStockVersion1.model.RechargeCarburant;
import com.AutoStock.AutoStockVersion1.model.Reparation;
import com.AutoStock.AutoStockVersion1.model.Vehicule;
import com.AutoStock.AutoStockVersion1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;
@RestController
@RequestMapping("/generate-rapport")
public class RapportController {

    /*@Autowired
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
    }*/

    @Autowired
    private VehiculeService vehiculeService;

    @PostMapping("/generateReport")
    public ResponseEntity<ByteArrayResource> generateReport(@RequestBody Map<String, String> params) {
        String registrationNumber = params.get("registrationNumber");
        String reportDate = params.get("reportDate");

        Vehicule vehicle = vehiculeService.getVehiculeByRegistrationNumber(registrationNumber);
        String htmlContent2 = generateHtmlContent(vehicle, reportDate);
        System.out.println(htmlContent2);
        if (vehicle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            String htmlContent = generateHtmlContent(vehicle, reportDate);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport.pdf");
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    private String generateHtmlContent(Vehicule vehicle, String reportDate) {
        String photoBase64 = "";
        String carteGriseBase64 = "";
        String assuranceBase64 = "";
        String vignetteBase64 = "";
        String logoBase64 = "";
        int repairCount = vehiculeService.getRepairCountByVehicule(vehicle.getIdVehicule());
        String chauffeur = vehiculeService.getChauffeurByVehicule(vehicle.getIdVehicule());

        try {
            photoBase64 = convertImageToBase64(vehicle.getPhoto());
            carteGriseBase64 = convertImageToBase64(vehicle.getCarteGrise());
            assuranceBase64 = convertImageToBase64(vehicle.getAssurance());
            vignetteBase64 = convertImageToBase64(vehicle.getVignette());
            logoBase64 = convertImageToBase64("C:\\Users\\MON PC\\Downloads\\AutoStockVersion1\\Frontend\\src\\assets\\logo.png");
        } catch (IOException e) {
            e.printStackTrace();
            return "<html><body><p>Erreur lors du chargement des images.</p></body></html>";
        }

        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; margin: 20px; background-color: #f9f9f9; }" +
                "h1 { color: #333; text-align: center; margin-bottom: 20px; }" +
                "p { color: #555; line-height: 1.6; }" +
                ".container { max-width: 800px; margin: 0 auto; padding: 20px; background-color: #fff; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                ".photo { display: flex; justify-content: center; margin-bottom: 20px; }" +
                ".photo img { border: 2px solid #1717cf; border-radius: 50%; width: 500px; height: 300px; object-fit: cover; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);margin-left:10% }" +
                ".vehicle-info { margin-bottom: 20px; }" +
                ".vehicle-info p { margin: 5px 0; }" +
                ".image-container { display: flex; flex-wrap: wrap; gap: 20px; justify-content: space-around; }" +
                ".image-container div { text-align: center; }" +
                ".image-container img { width: 60%; max-width: 200px; height: auto; border: 1px solid #ccc; border-radius: 8px; }" +
                ".image-container h3 { margin-bottom: 10px; }" +
                ".report-date { text-align: center; font-size: 14px; color: #888; margin-top: 20px; }" +
                ".header { text-align: center; margin-bottom: 20px; }" +
                ".header img { width: 100px; height: auto; margin-bottom: 10px; }" +
                ".header p { margin: 0; font-size: 14px; color: #333; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header' style='margin-top:-50%'>" +
                "<img src='data:image/png;base64," + logoBase64 + "' alt='Logo' style='width:60px'/>" +
                "<p>Ministère de l'intérieur / Préfecture d'Oujda-Angad</p>" +
                "<p>Province de Oujda</p>" +
                "</div>" +
                "<div class='container'>" +
                "<h1>Rapport de véhicule</h1>" +
                "<div class='photo'><img src='data:image/jpeg;base64," + photoBase64 + "' alt='Photo du véhicule'/></div>" +
                "<div class='vehicle-info'>" +
                "<p><strong>Numéro d'immatriculation:</strong> " + vehicle.getNumeroImmatriculation() + "</p>" +
                "<p><strong>Marque:</strong> " + vehicle.getMarque() + "</p>" +
                "<p><strong>Modèle:</strong> " + vehicle.getModele() + "</p>" +
                "<p><strong>État:</strong> " + vehicle.getEtat() + "</p>" +
                "<p><strong>Année:</strong> " + vehicle.getAnnee() + "</p>" +
                "<p><strong>Nombre de réparations:</strong> " + repairCount + "</p>" +
                "<p><strong>Assignée à:</strong> " + chauffeur + "</p>" +
                "<p><strong>Type de carburant:</strong> " + vehicle.getTypeCarburant() + "</p>" +
                "<p><strong>Kilométrage actuel:</strong> " + vehicle.getKilometrageActuel() + "</p>" +
                "<p><strong>Date d'acquisition:</strong> " + vehicle.getDateAcquisition() + "</p>" +
                "</div>" +
                "<div class='image-container'>" +
                "<div><h3>Carte grise:</h3><img src='data:image/jpeg;base64," + carteGriseBase64 + "' alt='Carte grise'/></div>" +
                "<div><h3>Assurance:</h3><img src='data:image/jpeg;base64," + assuranceBase64 + "' alt='Assurance'/></div>" +
                "<div><h3>Vignette:</h3><img src='data:image/jpeg;base64," + vignetteBase64 + "' alt='Vignette'/></div>" +
                "</div>" +
                "<p class='report-date'><strong>Date du rapport:</strong> " + reportDate + "</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }



    private String convertImageToBase64(String filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        return Base64.getEncoder().encodeToString(fileBytes);
    }

}