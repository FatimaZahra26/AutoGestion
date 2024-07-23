package com.AutoStock.AutoStockVersion1.model;

import jakarta.persistence.*;


@Entity
@Table(name = "chauffeur")
public class Chauffeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Chauffeur")
    private Long idChauffeur;

    @Column(name = "Nom")
    private String nom;

    @Column(name = "Prenom")
    private String prenom;

    @Column(name = "Telephone")
    private String telephone;

    @Column(name = "Type_Permis")
    private String typePermis;

    @Column(name = "Vehicule_ID")
    private Long vehiculeId;


    @Transient // Cette annotation évite que cette propriété soit mappée dans la base de données
    private String immatriculation;
    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;}
    // Getters and Setters

    public Long getIdChauffeur() {
        return idChauffeur;
    }

    public void setIdChauffeur(Long idChauffeur) {
        this.idChauffeur = idChauffeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTypePermis() {
        return typePermis;
    }

    public void setTypePermis(String typePermis) {
        this.typePermis = typePermis;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public Chauffeur(Long idChauffeur, String nom, String prenom, String telephone, String typePermis, Long vehiculeId) {
        this.idChauffeur = idChauffeur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.typePermis = typePermis;
        this.vehiculeId = vehiculeId;
    }

    public Chauffeur() {
    }

    @Override
    public String toString() {
        return "Chauffeur{" +
                "idChauffeur=" + idChauffeur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", typePermis='" + typePermis + '\'' +
                ", vehiculeId=" + vehiculeId +
                '}';
    }
}
