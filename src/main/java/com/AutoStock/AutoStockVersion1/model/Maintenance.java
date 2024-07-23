package com.AutoStock.AutoStockVersion1.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Maintenance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    @Column(name = "date_maintenance", nullable = false)
    private LocalDate dateMaintenance;

    private String description;
    private String type;
    private boolean completed;


    @Column(name = "repeat_every_km")
    private Integer repeatEveryKm;

    @Column(name = "repeat_every_year")
    private Integer repeatEveryYear;


    @Column(name = "repeat_type")
    private String repeatType;

    @Column(name = "reminder_before_days")
    private Integer reminderBeforeDays;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public LocalDate getDateMaintenance() {
        return dateMaintenance;
    }

    public Integer getRepeatEveryKm() {
        return repeatEveryKm;
    }

    public Maintenance(Long id, Vehicule vehicule, LocalDate dateMaintenance, String description, String type, boolean completed, Integer repeatEveryKm, Integer repeatEveryYear, String repeatType, Integer reminderBeforeDays) {
        this.id = id;
        this.vehicule = vehicule;
        this.dateMaintenance = dateMaintenance;
        this.description = description;
        this.type = type;
        this.completed = completed;
        this.repeatEveryKm = repeatEveryKm;
        this.repeatEveryYear = repeatEveryYear;
        this.repeatType = repeatType;
        this.reminderBeforeDays = reminderBeforeDays;
    }

    public void setRepeatEveryKm(Integer repeatEveryKm) {
        this.repeatEveryKm = repeatEveryKm;
    }

    public Integer getRepeatEveryYear() {
        return repeatEveryYear;
    }

    public void setRepeatEveryYear(Integer repeatEverYear) {
        this.repeatEveryYear = repeatEveryYear;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String  repeatType) {
        this.repeatType = repeatType;
    }

    public Integer getReminderBeforeDays() {
        return reminderBeforeDays;
    }

    public void setReminderBeforeDays(Integer reminderBeforeDays) {
        this.reminderBeforeDays = reminderBeforeDays;
    }

    public void setDateMaintenance(LocalDate dateMaintenance) {
        this.dateMaintenance = dateMaintenance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    public Maintenance() {
    }
}
