package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.Repository.ChauffeurRepository;
import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import com.AutoStock.AutoStockVersion1.service.ChauffeurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChauffeurServiceImpl implements ChauffeurService {
    Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(ChauffeurServiceImpl.class);

    @Autowired
    private ChauffeurRepository chauffeurRepository;
    public ChauffeurServiceImpl() throws SQLException {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Chauffeur> getChauffeurList() throws SQLException {
        List<Chauffeur> chauffeurs = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(
                "SELECT c.Id_Chauffeur, c.Nom, c.Prenom, c.Telephone, c.Type_Permis, c.Vehicule_ID, v.numéro_immatriculation AS Immatriculation " +
                        "FROM chauffeur c " +
                        "INNER JOIN vehicule v ON c.Vehicule_ID = v.ID_Vehicule");
        ResultSet rs = pr.executeQuery();
        while (rs.next()) {
            Chauffeur chauffeur = new Chauffeur();
            chauffeur.setIdChauffeur(rs.getLong("Id_Chauffeur"));
            chauffeur.setNom(rs.getString("Nom"));
            chauffeur.setPrenom(rs.getString("Prenom"));
            chauffeur.setTelephone(rs.getString("Telephone"));
            chauffeur.setTypePermis(rs.getString("Type_Permis"));
            chauffeur.setVehiculeId(rs.getLong("Vehicule_ID"));
            chauffeur.setImmatriculation(rs.getString("Immatriculation"));
            chauffeurs.add(chauffeur);
        }
        return chauffeurs;
    }
    @Override
    public Long countChauffeurs() throws SQLException {
        Long count = 0L;
        String query = "SELECT COUNT(*) AS total FROM chauffeur";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong("total");
            }
        }
        return count;
    }



    @Override
    public ResponseEntity<String> addChauffeur(Chauffeur chauffeur, String immatriculation) {
        logger.info("Ajout du chauffeur: " + chauffeur + " avec immatriculation: " + immatriculation);
        String getVehicleIdSql = "SELECT ID_Vehicule FROM vehicule WHERE numéro_immatriculation = ?";

        try (PreparedStatement getVehicleIdStmt = connection.prepareStatement(getVehicleIdSql)) {
            getVehicleIdStmt.setString(1, immatriculation);

            ResultSet vehicleResult = getVehicleIdStmt.executeQuery();

            if (vehicleResult.next()) {
                Long vehicleId = vehicleResult.getLong("ID_Vehicule");
                logger.info("ID du véhicule trouvé: " + vehicleId);

                // Vérifier si le véhicule est déjà assigné à un chauffeur
                String checkVehicleAssignedSql = "SELECT COUNT(*) FROM chauffeur WHERE vehicule_id = ?";
                try (PreparedStatement checkVehicleAssignedStmt = connection.prepareStatement(checkVehicleAssignedSql)) {
                    checkVehicleAssignedStmt.setLong(1, vehicleId);
                    ResultSet assignedResult = checkVehicleAssignedStmt.executeQuery();
                    assignedResult.next();
                    int count = assignedResult.getInt(1);

                    if (count == 0) {
                        logger.info("Véhicule non assigné, ajout du chauffeur.");
                        // Ajouter le chauffeur si le véhicule n'est pas déjà assigné
                        String sql = "INSERT INTO chauffeur (nom, prenom, telephone, type_permis, vehicule_id) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                            preparedStatement.setString(1, chauffeur.getNom());
                            preparedStatement.setString(2, chauffeur.getPrenom());
                            preparedStatement.setString(3, chauffeur.getTelephone());
                            preparedStatement.setString(4, chauffeur.getTypePermis());
                            preparedStatement.setLong(5, vehicleId);
                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                logger.info("Chauffeur ajouté avec succès.");
                                return ResponseEntity.ok("Chauffeur ajouté avec succès");
                            } else {
                                logger.error("Erreur lors de l'ajout du chauffeur.");
                                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du chauffeur.");
                            }
                        }
                    } else {
                        logger.error("Le véhicule est déjà assigné à un autre chauffeur.");
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Le véhicule est déjà assigné à un autre chauffeur.");
                    }
                }
            } else {
                logger.error("Véhicule non trouvé.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Véhicule non trouvé.");
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL lors de l'ajout du chauffeur.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur SQL lors de l'ajout du chauffeur.");
        }
    }

    @Override
    public ResponseEntity<String> updateChauffeur(Chauffeur chauffeur, String immatriculation) throws SQLException {
        if (chauffeur == null || chauffeur.getIdChauffeur() == null) {
            return ResponseEntity.badRequest().body("Chauffeur or ID cannot be null");
        }

        String getVehicleIdSql = "SELECT ID_Vehicule FROM vehicule WHERE numéro_immatriculation = ?";
        String checkVehicleAssignedSql = "SELECT COUNT(*) FROM chauffeur WHERE vehicule_id = ? AND id_chauffeur != ?";
        String updateChauffeurSql = "UPDATE chauffeur SET nom = ?, prenom = ?, telephone = ?, type_permis = ?, vehicule_id = ? WHERE id_chauffeur = ?";

        PreparedStatement getVehicleIdStmt = null;
        PreparedStatement checkVehicleAssignedStmt = null;
        PreparedStatement updateChauffeurStmt = null;
        ResultSet vehicleResult = null;
        ResultSet assignedResult = null;


            Long vehicleId = null;
            getVehicleIdStmt = connection.prepareStatement(getVehicleIdSql);
            getVehicleIdStmt.setString(1, immatriculation);
            vehicleResult = getVehicleIdStmt.executeQuery();

            if (vehicleResult.next()) {
                vehicleId = vehicleResult.getLong("ID_Vehicule");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Véhicule non trouvé.");
            }

            // Step 2: Check if the Vehicle ID is already assigned
            boolean isAssigned;
            checkVehicleAssignedStmt = connection.prepareStatement(checkVehicleAssignedSql);
            checkVehicleAssignedStmt.setLong(1, vehicleId);
            checkVehicleAssignedStmt.setLong(2, chauffeur.getIdChauffeur());
            assignedResult = checkVehicleAssignedStmt.executeQuery();
            assignedResult.next();
            int count = assignedResult.getInt(1);
            isAssigned = count > 0;

            if (!isAssigned) {
                // Step 3: Update the Chauffeur
                updateChauffeurStmt = connection.prepareStatement(updateChauffeurSql);
                updateChauffeurStmt.setString(1, chauffeur.getNom());
                updateChauffeurStmt.setString(2, chauffeur.getPrenom());
                updateChauffeurStmt.setString(3, chauffeur.getTelephone());
                updateChauffeurStmt.setString(4, chauffeur.getTypePermis());
                updateChauffeurStmt.setLong(5, vehicleId);
                updateChauffeurStmt.setLong(6, chauffeur.getIdChauffeur());
                int rowsAffected = updateChauffeurStmt.executeUpdate();

                if (rowsAffected > 0) {
                    return ResponseEntity.ok("Chauffeur mis à jour avec succès");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du chauffeur.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Le véhicule est déjà assigné à un autre chauffeur.");
            }

    }


    @Override
    public ResponseEntity<String> deleteChauffeur(Long id) {
        String sql = "DELETE FROM chauffeur WHERE id_chauffeur = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Chauffeur deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chauffeur not found");
            }
        } catch (SQLException e) {
            logger.error("SQL error while deleting chauffeur", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SQL error while deleting chauffeur");
        }
    }


}
