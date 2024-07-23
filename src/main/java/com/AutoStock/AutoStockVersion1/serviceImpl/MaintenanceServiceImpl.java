package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.model.Maintenance;
import com.AutoStock.AutoStockVersion1.service.MaintenanceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Maintenance> getAllMaintenances() {
        return entityManager.createNativeQuery("SELECT * FROM maintenance", Maintenance.class).getResultList();
    }

    @Override
    public List getUpcomingMaintenances() {
        LocalDate today = LocalDate.now();
        return entityManager.createNativeQuery(
                        "SELECT m.* FROM maintenance m " +
                                "JOIN vehicule v ON m.vehicule_id = v.id_vehicule " +
                                "WHERE m.completed = false AND m.date_maintenance < :today " +
                                "AND ((m.repeat_type = 'DAYS' AND (DATE_ADD(m.date_maintenance, INTERVAL m.repeat_every_year" +
                                " DAY) < :today " +
                                "OR m.reminder_before_days IS NOT NULL AND (DATE_ADD(m.date_maintenance, INTERVAL (m.repeat_every_year - m.reminder_before_days) DAY) < :today))) " +
                                "OR (m.repeat_type = 'KM' AND m.repeat_every_km IS NOT NULL AND (v.kilometrage_actuel >= m.repeat_every_km)))", Maintenance.class)
                .setParameter("today", today)
                .getResultList();
    }

    @Override
    public Maintenance addMaintenance(Maintenance maintenance) {
        entityManager.createNativeQuery(
                        "INSERT INTO maintenance (id_maintenance, date_maintenance, description, completed, repeat_every_km, repeat_every_year, repeat_type, reminder_before_days) " +
                                "VALUES (:idMaintenance, :dateMaintenance, :description, :completed, :repeatEveryKm, :repeatEveryYear, :repeatType, :reminderBeforeDays)")
                .setParameter("idMaintenance", maintenance.getId())
                .setParameter("dateMaintenance", maintenance.getDateMaintenance())
                .setParameter("description", maintenance.getDescription())
                .setParameter("completed", maintenance.isCompleted())
                .setParameter("repeatEveryKm", maintenance.getRepeatEveryKm())
                .setParameter("repeatEveryYear", maintenance.getRepeatEveryYear())
                .setParameter("repeatType", maintenance.getRepeatType())
                .setParameter("reminderBeforeDays", maintenance.getReminderBeforeDays())
                .executeUpdate();

        return maintenance;
    }

    @Override
    public void deleteMaintenance(Long id) {
        entityManager.createNativeQuery("DELETE FROM maintenance WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Maintenance updateMaintenance(Maintenance maintenance) {
        entityManager.createNativeQuery(
                        "UPDATE maintenance SET date_maintenance = :dateMaintenance, " +
                                "description = :description, completed = :completed, " +
                                "repeat_every_km = :repeatEveryKm, repeat_every_year = :repeatEveryYear, " +
                                "repeat_type = :repeatType, reminder_before_days = :reminderBeforeDays " +
                                "WHERE id = :idMaintenance")
                .setParameter("idMaintenance", maintenance.getId())
                .setParameter("dateMaintenance", maintenance.getDateMaintenance())
                .setParameter("description", maintenance.getDescription())
                .setParameter("completed", maintenance.isCompleted())
                .setParameter("repeatEveryKm", maintenance.getRepeatEveryKm())
                .setParameter("repeatEveryYear", maintenance.getRepeatEveryYear())
                .setParameter("repeatType", maintenance.getRepeatType())
                .setParameter("reminderBeforeDays", maintenance.getReminderBeforeDays())
                .executeUpdate();

        return maintenance;
    }
}
