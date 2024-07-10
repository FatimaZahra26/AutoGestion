package com.AutoStock.AutoStockVersion1.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rapport")
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Rapport")
    private Long idRapport;

    @Column(name = "Format")
    private String format;

    @Column(name = "Type_Rapport")
    private String typeRapport;

    @Column(name = "Vehicule_ID")
    private Long vehiculeId;

    @Column(name = "Date_Génération")
    private Date dateGeneration;

    @Column(name = "Contenu")
    private String contenu;

    // Getters and setters

    public Long getIdRapport() {
        return idRapport;
    }

    public void setIdRapport(Long idRapport) {
        this.idRapport = idRapport;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTypeRapport() {
        return typeRapport;
    }

    public void setTypeRapport(String typeRapport) {
        this.typeRapport = typeRapport;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public Date getDateGeneration() {
        return dateGeneration;
    }

    public Rapport() {
    }

    public void setDateGeneration(Date dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Rapport(Long idRapport, String format, String typeRapport, Long vehiculeId, Date dateGeneration, String contenu) {
        this.idRapport = idRapport;
        this.format = format;
        this.typeRapport = typeRapport;
        this.vehiculeId = vehiculeId;
        this.dateGeneration = dateGeneration;
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "Rapport{" +
                "idRapport=" + idRapport +
                ", format='" + format + '\'' +
                ", typeRapport='" + typeRapport + '\'' +
                ", vehiculeId=" + vehiculeId +
                ", dateGeneration=" + dateGeneration +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}

