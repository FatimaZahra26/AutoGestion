package com.AutoStock.AutoStockVersion1.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "réparation")
public class Reparation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_réparation;

    @Column(name = "vehicule_id", nullable = false)
    private int vehiculeId;

    @Column(name = "type_réparation", nullable = false, length = 100)
    private String typeReparation;

    @Column(name = "date_réparation", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateReparation;

    @Column(name = "cout", nullable = false, precision = 10, scale = 2)
    private BigDecimal cout;

    @Column(name = "fournisseur", nullable = false, length = 100)
    private String fournisseur;

    @Lob
    @Column(name = "facture")
    private String facture;

    @Lob
    @Column(name = "rapport_reparation")
    private String rapportReparation;

    // Getters and setters

    public Long getIdReparation() {
        return id_réparation;
    }

    public void setIdReparation(Long id) {
        this.id_réparation = id;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public String getTypeReparation() {
        return typeReparation;
    }

    public void setTypeReparation(String typeReparation) {
        this.typeReparation = typeReparation;
    }

    public Date getDateReparation() {
        return dateReparation;
    }

    public void setDateReparation(Date dateReparation) {
        this.dateReparation = dateReparation;
    }

    public BigDecimal getCout() {
        return cout;
    }

    public void setCout(BigDecimal cout) {
        this.cout = cout;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getFacture() {
        return facture;
    }

    public void setFacture(String facture) {
        this.facture = facture;
    }

    public String getRapportReparation() {
        return rapportReparation;
    }

    public void setRapportReparation(String rapportReparation) {
        this.rapportReparation = rapportReparation;
    }

    public Reparation(Long id, int vehiculeId, String typeReparation, Date dateReparation, BigDecimal cout, String fournisseur, String facture,String rapportReparation) {
        this.id_réparation = id;
        this.vehiculeId = vehiculeId;
        this.typeReparation = typeReparation;
        this.dateReparation = dateReparation;
        this.cout = cout;
        this.fournisseur = fournisseur;
        this.facture = facture;
        this.rapportReparation = rapportReparation;
    }

    public Reparation() {
    }

    @Override
    public String toString() {
        return "Reparation{" +
                "id=" + id_réparation +
                ", vehiculeId=" + vehiculeId +
                ", typeReparation='" + typeReparation + '\'' +
                ", dateReparation=" + dateReparation +
                ", cout=" + cout +
                ", fournisseur='" + fournisseur + '\'' +
                ", facture=" + facture+
                ", rapportReparation=" +rapportReparation+
                '}';
    }
}
