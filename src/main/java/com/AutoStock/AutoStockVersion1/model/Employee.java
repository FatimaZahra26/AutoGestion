package com.AutoStock.AutoStockVersion1.model;


import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    @Column(name = "secteur")
    private String secteur;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    private String CIN;
    private String situation_familiale;
    private String PPR;
    private String numero_assurance_sociale;
    private String Echelon;

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getSituation_familiale() {
        return situation_familiale;
    }

    public void setSituation_familiale(String situation_familiale) {
        this.situation_familiale = situation_familiale;
    }

    public String getPPR() {
        return PPR;
    }

    public void setPPR(String PPR) {
        this.PPR = PPR;
    }

    public String getNumero_assurance_sociale() {
        return numero_assurance_sociale;
    }

    public void setNumero_assurance_sociale(String numero_assurance_sociale) {
        this.numero_assurance_sociale = numero_assurance_sociale;
    }

    public String getEchelon() {
        return Echelon;
    }

    public void setEchelon(String echelon) {
        Echelon = echelon;
    }

    public Employee(Long id, String name, String email, String phone, String address, String CIN, String situation_familiale, String PPR, String numero_assurance_sociale, String echelon) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.CIN = CIN;
        this.situation_familiale = situation_familiale;
        this.PPR = PPR;
        this.numero_assurance_sociale = numero_assurance_sociale;
        Echelon = echelon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", CIN='" + CIN + '\'' +
                ", situation_familiale='" + situation_familiale + '\'' +
                ", PPR='" + PPR + '\'' +
                ", numero_assurance_sociale='" + numero_assurance_sociale + '\'' +
                ", Echelon='" + Echelon + '\'' +
                '}';
    }

    public Employee() {
    }
}

