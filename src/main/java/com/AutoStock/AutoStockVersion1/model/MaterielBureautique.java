package com.AutoStock.AutoStockVersion1.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "materiel_bureautique")
public class MaterielBureautique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ref")
    private String ref;

    @Column(name = "description")
    private String description;

    @Column(name = "caracteristique_technique")
    private String caracteristiqueTechnique;

    @Column(name = "date_entree")
    private LocalDate dateEntree;

    @Column(name = "date_sortie")
    private LocalDate dateSortie;

    @Column(name = "marque_model")
    private String marqueModel;

    @Column(name = "service_affecte")
    private String serviceAffecte;

    public String getServiceAffecte() {
        return serviceAffecte;
    }

    public void setServiceAffecte(String serviceAffecte) {
        this.serviceAffecte = serviceAffecte;
    }

    @Column(name = "photo_path")
    private String photoPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaracteristiqueTechnique() {
        return caracteristiqueTechnique;
    }

    public void setCaracteristiqueTechnique(String caracteristiqueTechnique) {
        this.caracteristiqueTechnique = caracteristiqueTechnique;
    }

    public LocalDate getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getMarqueModel() {
        return marqueModel;
    }

    public void setMarqueModel(String marqueModel) {
        this.marqueModel = marqueModel;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}

