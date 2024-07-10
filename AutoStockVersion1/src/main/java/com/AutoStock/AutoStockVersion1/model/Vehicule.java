package com.AutoStock.AutoStockVersion1.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vehicule;

    private String typePermis;
    private String marque;
    private String modele;
    private String etat;
    private int annee;
    private String typeCarburant;
    private String numeroImmatriculation;
    private int kilometrageActuel;
    private Date dateAcquisition;
    private String carteGrise;
    private String assurance;
    private String vignette;

    public Long getIdVehicule() {
        return id_vehicule;
    }

    public void setIdVehicule(Long id) {
        this.id_vehicule = id;
    }

    public String getTypePermis() {
        return typePermis;
    }

    public void setTypePermis(String typePermis) {
        this.typePermis = typePermis;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getTypeCarburant() {
        return typeCarburant;
    }

    public void setTypeCarburant(String typeCarburant) {
        this.typeCarburant = typeCarburant;
    }

    public String getNumeroImmatriculation() {
        return numeroImmatriculation;
    }

    public void setNumeroImmatriculation(String numeroImmatriculation) {
        this.numeroImmatriculation = numeroImmatriculation;
    }

    public int getKilometrageActuel() {
        return kilometrageActuel;
    }

    public void setKilometrageActuel(int kilometrageActuel) {
        this.kilometrageActuel = kilometrageActuel;
    }

    public Date getDateAcquisition() {
        return dateAcquisition;
    }

    public void setDateAcquisition(Date dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    public String getCarteGrise() {
        return carteGrise;
    }

    public void setCarteGrise(String carteGrise) {
        this.carteGrise = carteGrise;
    }

    public String getAssurance() {
        return assurance;
    }

    public void setAssurance(String assurance) {
        this.assurance = assurance;
    }

    public String getVignette() {
        return vignette;
    }

    public void setVignette(String vignette) {
        this.vignette = vignette;
    }

    public Vehicule(Long id, String typePermis, String marque, String modele, String etat, int annee, String typeCarburant, String numeroImmatriculation, int kilometrageActuel, Date dateAcquisition, String carteGrise, String assurance, String vignette) {
        this.id_vehicule = id;
        this.typePermis = typePermis;
        this.marque = marque;
        this.modele = modele;
        this.etat = etat;
        this.annee = annee;
        this.typeCarburant = typeCarburant;
        this.numeroImmatriculation = numeroImmatriculation;
        this.kilometrageActuel = kilometrageActuel;
        this.dateAcquisition = dateAcquisition;
        this.carteGrise = carteGrise;
        this.assurance = assurance;
        this.vignette = vignette;
    }

    public Vehicule() {
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id_vehicule +
                ", typePermis='" + typePermis + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", etat='" + etat + '\'' +
                ", annee=" + annee +
                ", typeCarburant='" + typeCarburant + '\'' +
                ", numeroImmatriculation='" + numeroImmatriculation + '\'' +
                ", kilometrageActuel=" + kilometrageActuel +
                ", dateAcquisition=" + dateAcquisition +
                ", carteGrise='" + carteGrise + '\'' +
                ", assurance='" + assurance + '\'' +
                ", vignette='" + vignette + '\'' +
                '}';
    }
}