package com.AutoStock.AutoStockVersion1.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "technique")
public class Technique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materiel")
    private long idMateriel;

    @Column(name = "id_stock", nullable = false)
    private long idStock;

    @Column(name = "ref", nullable = false)
    private String ref;

    @Column(name = "description")
    private String description;

    @Column(name = "caracteristique_technique")
    private String caracteristiqueTechnique;

    @Column(name = "date_entree")
    @Temporal(TemporalType.DATE)
    private Date dateEntree;

    @Column(name = "marque")
    private String marque;

    @Column(name = "photo")
    private String photo;


    // Constructeurs
    public Technique() {
    }

    public Technique(long idStock, String ref, String description, String caracteristiqueTechnique, Date dateEntree, String marque) {
        this.idStock = idStock;
        this.ref = ref;
        this.description = description;
        this.caracteristiqueTechnique = caracteristiqueTechnique;
        this.dateEntree = dateEntree;
        this.marque = marque;
    }

    // Getters et Setters

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public long getIdMateriel() {
        return idMateriel;
    }

    public void setIdMateriel(long idMateriel) {
        this.idMateriel = idMateriel;
    }

    public long getIdStock() {
        return idStock;
    }

    public void setIdStock(long idStock) {
        this.idStock = idStock;
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

    public Date getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }
}
