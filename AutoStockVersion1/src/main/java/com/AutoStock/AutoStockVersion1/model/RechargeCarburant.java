package com.AutoStock.AutoStockVersion1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class RechargeCarburant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vehiculeId;
    private Date dateRecharge;
    private double quantiteCarburant;
    private double cout;
    private String fournisseur;
    private String lieuRecharge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public Date getDateRecharge() {
        return dateRecharge;
    }

    public void setDateRecharge(Date dateRecharge) {
        this.dateRecharge = dateRecharge;
    }

    public double getQuantiteCarburant() {
        return quantiteCarburant;
    }

    public void setQuantiteCarburant(double quantiteCarburant) {
        this.quantiteCarburant = quantiteCarburant;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getLieuRecharge() {
        return lieuRecharge;
    }

    public void setLieuRecharge(String lieuRecharge) {
        this.lieuRecharge = lieuRecharge;
    }
}