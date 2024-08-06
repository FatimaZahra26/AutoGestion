package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.Repository.MaintenanceRepository;
import com.AutoStock.AutoStockVersion1.model.Maintenance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Scheduled(cron = "0 0 9 * * ?") // Scheduled daily at 9:00 AM
    public void checkUpcomingMaintenances() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        LocalDate now = ZonedDateTime.now(ZoneId.systemDefault()).toLocalDate();

        for (Maintenance maintenance : maintenances) {
            LocalDate nextNotificationDate = maintenance.getNextNotificationDate();
            if (nextNotificationDate != null && !nextNotificationDate.isAfter(now)) {
                sendNotification(maintenance);
                maintenance.setNextNotificationDate(now.plusMonths(1));
                maintenanceRepository.save(maintenance);
            }
        }
    }

    private void sendNotification(Maintenance maintenance) {
        System.out.println("Entretien à venir pour le véhicule " + maintenance.getVehicleId());
    }
}
