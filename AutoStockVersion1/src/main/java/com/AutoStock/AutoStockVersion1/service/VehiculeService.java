package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Vehicule;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VehiculeService {
    List<Vehicule> getAllVehicules();
    public Vehicule updateVehicule(Vehicule vehicule) ;
    public void deleteVehicule(Long id) ;
    public String uploadFile(MultipartFile file) throws IOException;


    Vehicule saveVehicule(Vehicule vehicule);
}
