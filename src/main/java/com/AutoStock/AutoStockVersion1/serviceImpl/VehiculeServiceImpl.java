package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.model.Vehicule;
import com.AutoStock.AutoStockVersion1.service.VehiculeService;
import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehiculeServiceImpl implements VehiculeService {

    @Value("${upload.dir}")
    private String uploadDir;

    private Connection connection;

    @PersistenceContext
    private EntityManager entityManager;


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
                vehicule.setPhoto(rs.getString("Photo"));
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
            // Vérifier si le numéro d'immatriculation existe déjà
            PreparedStatement checkStatement = connection.prepareStatement(
                    "SELECT COUNT(*) AS count FROM vehicule WHERE Numéro_Immatriculation = ?"
            );
            checkStatement.setString(1, vehicule.getNumeroImmatriculation());
            ResultSet checkResult = checkStatement.executeQuery();
            checkResult.next();
            int count = checkResult.getInt("count");

            if (count > 0) {
                // Le numéro d'immatriculation existe déjà, peut-être renvoyer une erreur ou gérer la mise à jour
                // Exemple: return null ou throw new IllegalStateException("Numéro d'immatriculation déjà utilisé");
                // Pour le démontrer, je vais laisser return vehicule
                return vehicule;
            }

            // Insérer le véhicule s'il n'existe pas déjà
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO vehicule (Type_Permis, Marque, Modele, Etat, Annee, Type_Carburant, Numéro_Immatriculation, Kilometrage_Actuel, Date_acquisition, Carte_Grise, Assurance, Vignette, Photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
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
            preparedStatement.setString(13, vehicule.getPhoto());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicule;
    }

    @Override
    public List<Object[]> getTopVehiclesWithRepairs() {
        String sql = "SELECT v.numéro_immatriculation, COUNT(r.id_réparation) as repair_count " +
                "FROM vehicule v " +
                "JOIN réparation r ON v.id_vehicule = r.vehicule_id " +
                "GROUP BY v.id_vehicule " +
                "ORDER BY repair_count DESC " +
                "LIMIT 2";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public Vehicule updateVehicule(Vehicule vehicule) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE vehicule SET Type_Permis=?, Marque=?, Modele=?, Etat=?, Annee=?, Type_Carburant=?, Numéro_Immatriculation=?, Kilometrage_Actuel=?, Date_acquisition=?, Carte_Grise=?, Assurance=?, Vignette=?, Photo=? WHERE ID_Vehicule=?"
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
            preparedStatement.setString(13, vehicule.getPhoto());
            preparedStatement.setLong(14, vehicule.getIdVehicule());

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

    public Map<Long, Integer> getReparationCountByVehicule() {
        Map<Long, Integer> reparationCountMap = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT Vehicule_ID, COUNT(*) as count FROM réparation GROUP BY Vehicule_ID"
            );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                reparationCountMap.put(rs.getLong("Vehicule_ID"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reparationCountMap;
    }

    public Long countVehicules() throws SQLException {
        Long count = 0L;
        String query = "SELECT COUNT(*) AS total FROM vehicule";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong("total");
            }
        }
        return count;
    }
    @Override
    public Vehicule getVehiculeById(Long id) {
        Vehicule vehicule = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicule WHERE ID_Vehicule = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                vehicule = new Vehicule();
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
                vehicule.setPhoto(rs.getString("Photo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicule;
    }
}
