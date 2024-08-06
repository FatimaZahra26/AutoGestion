package com.AutoStock.AutoStockVersion1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "ID_Vehicule", insertable = false, updatable = false)
    private Vehicule vehicule;
    public String getImmatriculation() {
        return vehicule != null ? vehicule.getNumeroImmatriculation() : immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    @Transient
    private String immatriculation;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "date_maintenance")
    private LocalDateTime dateMaintenance;

    @Column(name = "type")
    private String type;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "description")
    private String description;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "next_notification_date")
    private LocalDate nextNotificationDate;

    @Column(name = "completed")
    private boolean completed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getDateMaintenance() {
        return dateMaintenance;
    }

    public void setDateMaintenance(LocalDateTime dateMaintenance) {
        this.dateMaintenance = dateMaintenance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getNextNotificationDate() {
        return nextNotificationDate;
    }

    public void setNextNotificationDate(LocalDate nextNotificationDate) {
        this.nextNotificationDate = nextNotificationDate;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
