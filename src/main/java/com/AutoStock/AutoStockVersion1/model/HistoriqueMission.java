package com.AutoStock.AutoStockVersion1.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "historique_mission")
public class HistoriqueMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mission_id", nullable = false)
    private Long missionId;

    @Column(name = "nom_employee", nullable = false)
    private String nomEmployee;

    @Column(name = "prenom_employee", nullable = false)
    private String prenomEmployee;

    @Column(name = "cin_employee", nullable = false)
    private String cinEmployee;

    @Column(name = "id_vehicule", nullable = false)
    private Long idVehicule;

    @Column(name = "ville_depart", nullable = false)
    private String villeDepart;

    @Column(name = "ville_arrivee", nullable = false)
    private String villeArrivee;

    @Column(name = "date_depart", nullable = false)
    private Date dateDepart;

    @Column(name = "date_arrivee", nullable = false)
    private Date dateArrivee;

    @Column(name = "montant_recharge_carburant", nullable = false)
    private BigDecimal montantRechargeCarburant;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getNomEmployee() {
        return nomEmployee;
    }

    public void setNomEmployee(String nomEmployee) {
        this.nomEmployee = nomEmployee;
    }

    public String getPrenomEmployee() {
        return prenomEmployee;
    }

    public void setPrenomEmployee(String prenomEmployee) {
        this.prenomEmployee = prenomEmployee;
    }

    public String getCinEmployee() {
        return cinEmployee;
    }

    public void setCinEmployee(String cinEmployee) {
        this.cinEmployee = cinEmployee;
    }

    public Long getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(Long idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public BigDecimal getMontantRechargeCarburant() {
        return montantRechargeCarburant;
    }

    public void setMontantRechargeCarburant(BigDecimal montantRechargeCarburant) {
        this.montantRechargeCarburant = montantRechargeCarburant;
    }

}

