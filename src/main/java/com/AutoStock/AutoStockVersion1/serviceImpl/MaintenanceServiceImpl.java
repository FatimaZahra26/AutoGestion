package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.Repository.MaintenanceRepository;
import com.AutoStock.AutoStockVersion1.Repository.VehiculeRepository;
import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import com.AutoStock.AutoStockVersion1.model.Maintenance;
import com.AutoStock.AutoStockVersion1.model.Vehicule;
import com.AutoStock.AutoStockVersion1.service.MaintenanceService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Override
    public List<Maintenance> getAllMaintenances() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        for (Maintenance maintenance : maintenances) {
            if (maintenance.getVehicleId() != null) {
                Vehicule vehicule = vehiculeRepository.findById(maintenance.getVehicleId()).orElse(null);
                maintenance.setImmatriculation(vehicule != null ? vehicule.getNumeroImmatriculation() : null);
            }
        }
        return maintenances;
    }

    @Override
    public Maintenance getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id).orElse(null);
    }

    @Override
    public Maintenance saveMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance updateMaintenance(Long id, Maintenance maintenance) {
        Maintenance existingMaintenance = getMaintenanceById(id);
        if (existingMaintenance != null) {
            existingMaintenance.setVehicleId(maintenance.getVehicleId());
            existingMaintenance.setDateMaintenance(maintenance.getDateMaintenance());
            existingMaintenance.setType(maintenance.getType());
            existingMaintenance.setCost(maintenance.getCost());
            existingMaintenance.setDescription(maintenance.getDescription());
            existingMaintenance.setNextNotificationDate(maintenance.getNextNotificationDate());
            existingMaintenance.setCompleted(maintenance.getCompleted());

            Maintenance updatedMaintenance = maintenanceRepository.save(existingMaintenance);
            logMaintenanceHistory(updatedMaintenance, "UPDATE");
            return updatedMaintenance;
        }
        return null;
    }

    @Override
    public void deleteMaintenance(Long id) {
        Maintenance existingMaintenance = getMaintenanceById(id);
        if (existingMaintenance != null) {
            try (Connection connection = DBUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM maintenance WHERE id = ?")) {
                preparedStatement.setLong(1, id);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new RuntimeException("Deleting maintenance failed, no rows affected.");
                }

                logMaintenanceHistory(existingMaintenance, "DELETE");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void logMaintenanceHistory(Maintenance maintenance, String action) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO historique_maintenance (maintenance_id, vehicule_id, date_maintenance, type, cout, description, next_notification_date, completed) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setLong(1, maintenance.getId());
            preparedStatement.setLong(2, maintenance.getVehicleId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(maintenance.getDateMaintenance()));
            preparedStatement.setString(4, maintenance.getType());
            preparedStatement.setDouble(5, maintenance.getCost());
            preparedStatement.setString(6, maintenance.getDescription());
            java.sql.Date sqlDate = java.sql.Date.valueOf(maintenance.getNextNotificationDate());
            preparedStatement.setDate(7, sqlDate);
            preparedStatement.setBoolean(8, maintenance.getCompleted());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkUpcomingMaintenances() {
        List<Maintenance> maintenances = maintenanceRepository.findByNextNotificationDateBefore(Date.from(ZonedDateTime.now(ZoneId.systemDefault()).toInstant()));
        LocalDate now = ZonedDateTime.now(ZoneId.systemDefault()).toLocalDate();

        for (Maintenance maintenance : maintenances) {
            if (!maintenance.getCompleted()) {  // Ajout de la vérification pour les maintenances non terminées
                sendNotification(maintenance);
                maintenance.setNextNotificationDate(now.plusMonths(1));
                maintenanceRepository.save(maintenance);
            }
        }
    }

    private void sendNotification(Maintenance maintenance) {
        System.out.println("Entretien à venir pour le véhicule " + maintenance.getVehicleId());
    }

    @Override
    public List<Maintenance> getUpcomingMaintenances() {
        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromNow = today.plusWeeks(1);
        return maintenanceRepository.findByNextNotificationDateBetween(today, oneWeekFromNow);
    }

    @Override
    public List<Maintenance> getPendingMaintenances() {
        return maintenanceRepository.findByCompletedFalse();
    }
}
