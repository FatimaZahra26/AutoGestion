package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.Repository.TechniqueRepository;
import com.AutoStock.AutoStockVersion1.exception.ResourceNotFoundException;
import com.AutoStock.AutoStockVersion1.model.Technique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class TechniqueController {

    @Autowired
    private TechniqueRepository techniqueRepository;

    // get all matériel_technique
    @GetMapping("/techniques")
    public List<Technique> getAllTechniques(){
        return techniqueRepository.findAll();
    }
    // create matériel_technique rest api
    @PostMapping("/techniques")
    public Technique createTechnique(@RequestBody Technique technique) {
        return techniqueRepository.save(technique);
    }
    // get materiel_technique by id rest api
    @GetMapping("/techniques/{idMateriel}")
    public ResponseEntity<Technique> getTechniqueById(@PathVariable Long idMateriel) {
        Technique technique = techniqueRepository.findById(idMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("materiel not exist with id :" + idMateriel));
        return ResponseEntity.ok(technique);
    }

    // update materiel_technique rest api
    @PutMapping("/techniques/{idMateriel}")
    public ResponseEntity<Technique> updateTechnique(@PathVariable Long idMateriel, @RequestBody Technique techniqueDetails){
        Technique technique = techniqueRepository.findById(idMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + idMateriel));

        technique.setRef(techniqueDetails.getRef());
        technique.setDescription(techniqueDetails.getDescription());
        technique.setCaracteristiqueTechnique(techniqueDetails.getCaracteristiqueTechnique());
        technique.setDateEntree(techniqueDetails.getDateEntree());
        technique.setMarque(techniqueDetails.getMarque());

        Technique updateTechnique = techniqueRepository.save(technique);
        return ResponseEntity.ok(updateTechnique);
    }

    // delete materiel_technique rest api
    @DeleteMapping("/techniques/{idMateriel}")
    public ResponseEntity<Map<String, Boolean>> deletetechnique(@PathVariable Long idMateriel){
        Technique technique = techniqueRepository.findById(idMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("materiel not exist with id :" + idMateriel));

        techniqueRepository.delete(technique);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
