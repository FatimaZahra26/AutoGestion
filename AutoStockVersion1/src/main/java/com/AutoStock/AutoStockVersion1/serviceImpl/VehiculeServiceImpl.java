package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.model.Vehicule;
import com.AutoStock.AutoStockVersion1.service.VehiculeService;
import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehiculeServiceImpl implements VehiculeService {

    @Value("${upload.dir}")
    private String uploadDir;

    private Connection connection;

    public VehiculeServiceImpl() throws SQLException {
        connection = DBUtil.getConnection(); // Assume DBUtil class manages database connection
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        List<Vehicule> vehicules = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicule");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vehicule vehicule = new Vehicule();
                vehicule.setIdVehicule(rs.getLong("ID_Vehicule"));
                vehicule.setTypePermis(rs.getString("Type_Permis"));
                vehicule.setMarque(rs.getString("Marque"));
                vehicule.setModele(rs.getString("Modele"));
                vehicule.setEtat(rs.getString("Etat"));
                vehicule.setAnnee(rs.getInt("Annee"));
                vehicule.setTypeCarburant(rs.getString("Type_Carburant"));
                vehicule.setNumeroImmatriculation(rs.getString("Numéro_Immatriculation"));
                vehicule.setKilometrageActuel(rs.getInt("Kilometrage_Actuel"));
                vehicule.setDateAcquisition(rs.getDate("Date_acquisition"));
                vehicule.setCarteGrise(rs.getString("Carte_Grise"));
                vehicule.setAssurance(rs.getString("Assurance"));
                vehicule.setVignette(rs.getString("Vignette"));
                vehicules.add(vehicule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicules;
    }

    @Override
    public Vehicule saveVehicule(Vehicule vehicule) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO vehicule (Type_Permis, Marque, Modele, Etat, Annee, Type_Carburant, Numéro_Immatriculation, Kilometrage_Actuel, Date_acquisition, Carte_Grise, Assurance, Vignette) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1, vehicule.getTypePermis());
            preparedStatement.setString(2, vehicule.getMarque());
            preparedStatement.setString(3, vehicule.getModele());
            preparedStatement.setString(4, vehicule.getEtat());
            preparedStatement.setInt(5, vehicule.getAnnee());
            preparedStatement.setString(6, vehicule.getTypeCarburant());
            preparedStatement.setString(7, vehicule.getNumeroImmatriculation());
            preparedStatement.setInt(8, vehicule.getKilometrageActuel());
            preparedStatement.setDate(9, new java.sql.Date(vehicule.getDateAcquisition().getTime()));
            preparedStatement.setString(10, vehicule.getCarteGrise());
            preparedStatement.setString(11, vehicule.getAssurance());
            preparedStatement.setString(12, vehicule.getVignette());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicule;
    }

    @Override
    public Vehicule updateVehicule(Vehicule vehicule) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE vehicule SET Type_Permis=?, Marque=?, Modele=?, Etat=?, Annee=?, Type_Carburant=?, Numéro_Immatriculation=?, Kilometrage_Actuel=?, Date_acquisition=?, Carte_Grise=?, Assurance=?, Vignette=? WHERE ID_Vehicule=?"
            );
            preparedStatement.setString(1, vehicule.getTypePermis());
            preparedStatement.setString(2, vehicule.getMarque());
            preparedStatement.setString(3, vehicule.getModele());
            preparedStatement.setString(4, vehicule.getEtat());
            preparedStatement.setInt(5, vehicule.getAnnee());
            preparedStatement.setString(6, vehicule.getTypeCarburant());
            preparedStatement.setString(7, vehicule.getNumeroImmatriculation());
            preparedStatement.setInt(8, vehicule.getKilometrageActuel());
            preparedStatement.setDate(9, new java.sql.Date(vehicule.getDateAcquisition().getTime()));
            preparedStatement.setString(10, vehicule.getCarteGrise());
            preparedStatement.setString(11, vehicule.getAssurance());
            preparedStatement.setString(12, vehicule.getVignette());
            preparedStatement.setLong(13, vehicule.getIdVehicule());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicule;
    }

    @Override
    public void deleteVehicule(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM vehicule WHERE ID_Vehicule=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = uploadDir + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
