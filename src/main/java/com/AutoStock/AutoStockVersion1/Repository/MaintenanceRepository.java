package com.AutoStock.AutoStockVersion1.Repository;

import com.AutoStock.AutoStockVersion1.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByCompletedFalseAndDateMaintenanceBefore(LocalDate date);
    List<Maintenance> findByNextNotificationDateBefore(Date date);
    List<Maintenance> findByNextNotificationDateBetween(LocalDate startDate, LocalDate endDate);
    List<Maintenance> findByCompletedFalse();
}
