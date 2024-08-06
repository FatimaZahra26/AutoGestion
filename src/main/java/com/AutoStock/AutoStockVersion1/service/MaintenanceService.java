package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Maintenance;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MaintenanceService {

    public List<Maintenance> getAllMaintenances();
    public Maintenance getMaintenanceById(Long id);
    public Maintenance saveMaintenance(Maintenance maintenance);
    public void deleteMaintenance(Long id);
    public void checkUpcomingMaintenances();
    List<Maintenance> getUpcomingMaintenances();
    public Maintenance updateMaintenance(Long id, Maintenance maintenance);
    public List<Maintenance> getPendingMaintenances();
}
