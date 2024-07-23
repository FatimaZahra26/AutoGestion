package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.Repository.PersonnelRepository;
import com.AutoStock.AutoStockVersion1.exception.ResourceNotFoundException;
import com.AutoStock.AutoStockVersion1.model.Personnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class PersonnelController {

    @Autowired
    private PersonnelRepository personnelRepository;

    // get all users
    @GetMapping("/personnels")
    public List<Personnel> getAllPersonnels(){
        return personnelRepository.findAll();
    }
    // create user rest api
    @PostMapping("/personnels")
    public Personnel createPersonnel(@RequestBody Personnel personnel) {
        return personnelRepository.save(personnel);
    }
    // get user by id rest api
    @GetMapping("/personnels/{userID}")
    public ResponseEntity<Personnel> getPersonnelById(@PathVariable Long userID) {
        Personnel personnel = personnelRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("user not exist with id :" + userID));
        return ResponseEntity.ok(personnel);
    }

    // update user rest api
    @PutMapping("/personnels/{userID}")
    public ResponseEntity<Personnel> updatePersonnel(@PathVariable Long userID, @RequestBody Personnel personnelDetails){
        Personnel personnel = personnelRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("user not exist with id :" + userID));

        personnel.setNom(personnelDetails.getNom());
        personnel.setPrenom(personnelDetails.getPrenom());
        personnel.setEmail(personnelDetails.getEmail());
        personnel.setMotDePasse(personnelDetails.getMotDePasse());


        Personnel updatePersonnel = personnelRepository.save(personnel);
        return ResponseEntity.ok(updatePersonnel);
    }

    // delete user rest api
    @DeleteMapping("/personnels/{userID}")
    public ResponseEntity<Map<String, Boolean>> deletepersonnel(@PathVariable Long userID){
        Personnel personnel = personnelRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("user not exist with id :" + userID));

        personnelRepository.delete(personnel);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

