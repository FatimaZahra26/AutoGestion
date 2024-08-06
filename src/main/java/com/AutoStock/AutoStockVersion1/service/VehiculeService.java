package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Vehicule;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface VehiculeService {
    List<Vehicule> getAllVehicules();
    public Vehicule updateVehicule(Vehicule vehicule) ;
    public void deleteVehicule(Long id) ;
    public String uploadFile(MultipartFile file) throws IOException;
    public Map<Long, Integer> getReparationCountByVehicule() ;
    public Vehicule getVehiculeById(Long id);
    public Long countVehicules() throws SQLException;
    Vehicule saveVehicule(Vehicule vehicule);
    public List<Object[]> getTopVehiclesWithRepairs();
    Vehicule getVehiculeByRegistrationNumber(String registrationNumber);

    int getRepairCountByVehicule(Long vehiculeId);

    String getChauffeurByVehicule(Long vehiculeId);
}
