package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.Repository.BureautiqueRepository;
import com.AutoStock.AutoStockVersion1.exception.ResourceNotFoundException;
import com.AutoStock.AutoStockVersion1.model.Bureautique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class BureautiqueController {

    @Autowired
    private BureautiqueRepository bureautiqueRepository;

    // get all matériel
    @GetMapping("/bureautiques")
    public List<Bureautique> getAllBureautiques(){
        return bureautiqueRepository.findAll();
    }
    // create employee rest api
    @PostMapping("/bureautiques")
    public Bureautique createBureautique(@RequestBody Bureautique bureautique) {
        return bureautiqueRepository.save(bureautique);
    }
    // get employee by id rest api
    @GetMapping("/bureautiques/{idMateriel}")
    public ResponseEntity<Bureautique> getBureautiqueById(@PathVariable Long idMateriel) {
        Bureautique bureautique = bureautiqueRepository.findById(idMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("materiel not exist with id :" + idMateriel));
        return ResponseEntity.ok(bureautique);
    }

    // update employee rest api
    @PutMapping("/bureautiques/{idMateriel}")
    public ResponseEntity<Bureautique> updateBureautique(@PathVariable Long idMateriel, @RequestBody Bureautique bureautiqueDetails){
        Bureautique bureautique = bureautiqueRepository.findById(idMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + idMateriel));

        bureautique.setRef(bureautiqueDetails.getRef());
        bureautique.setDescription(bureautiqueDetails.getDescription());
        bureautique.setTaille(bureautiqueDetails.getTaille());
        bureautique.setDateEntree(bureautiqueDetails.getDateEntree());
        bureautique.setPhoto(bureautiqueDetails.getPhoto());

        Bureautique updateBureautique = bureautiqueRepository.save(bureautique);
        return ResponseEntity.ok(updateBureautique);
    }

    // delete employee rest api
    @DeleteMapping("/bureautiques/{idMateriel}")
    public ResponseEntity<Map<String, Boolean>> deletebureautique(@PathVariable Long idMateriel){
        Bureautique bureautique = bureautiqueRepository.findById(idMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("materiel not exist with id :" + idMateriel));

        bureautiqueRepository.delete(bureautique);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    ////////////////////////////////////
    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/uploadFi")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = uploadDir + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            response.put("filePath", "/uploads/" + file.getOriginalFilename());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

