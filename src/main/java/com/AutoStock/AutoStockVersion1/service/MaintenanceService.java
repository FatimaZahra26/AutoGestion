package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Maintenance;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MaintenanceService {

    public List<Maintenance> getAllMaintenances();
    public List<Maintenance> getUpcomingMaintenances();
    public Maintenance addMaintenance(Maintenance maintenance);
    public void deleteMaintenance(Long id);
    public Maintenance updateMaintenance(Maintenance maintenance);
}
