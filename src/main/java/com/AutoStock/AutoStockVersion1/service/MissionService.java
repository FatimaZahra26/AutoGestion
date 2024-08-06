package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Mission;
import com.AutoStock.AutoStockVersion1.model.Vehicule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
@Service
public interface MissionService {
    public List<Vehicule> getUnassignedVehicles() throws SQLException;
    public ResponseEntity<String> addMission(Mission mission);
    public Mission getMissionById(Long id);
    public List<Mission> getAllMissions();
    public ResponseEntity<String> updateMission(Long id, Mission mission);
    public ResponseEntity<String> deleteMission(Long id);
}
