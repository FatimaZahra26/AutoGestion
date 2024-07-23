package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.model.Reparation;
import com.AutoStock.AutoStockVersion1.service.ReparationService;
import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReparationServiceImpl implements ReparationService {

    private final Connection connection;

    @Value("${upload.dir.reparations}")
    private String uploadDir;

    public ReparationServiceImpl() throws SQLException {
        this.connection = DBUtil.getConnection();
    }

    @Override
    public List<Reparation> getAllReparations() {
        List<Reparation> reparations = new ArrayList<>();
        String sql = "SELECT r.*, v.numéro_immatriculation FROM réparation r " +
                "JOIN vehicule v ON r.vehicule_id = v.id_vehicule";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Reparation reparation = new Reparation();
                reparation.setIdReparation(rs.getLong("ID_Réparation"));
                reparation.setVehiculeId(rs.getInt("vehicule_id"));
                reparation.setTypeReparation(rs.getString("type_réparation"));
                reparation.setDateReparation(rs.getDate("date_réparation"));
                reparation.setCout(rs.getBigDecimal("cout"));
                reparation.setFournisseur(rs.getString("fournisseur"));
                reparation.setFacture(rs.getString("facture"));
                reparation.setRapportReparation(rs.getString("rapport_réparation"));
                reparation.setImmatriculation(rs.getString("numéro_immatriculation"));
                reparations.add(reparation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reparations;
    }

    @Override
    public Reparation saveReparation(Reparation reparation) {
        String selectVehiculeIdQuery = "SELECT id_vehicule FROM vehicule WHERE numéro_immatriculation = ?";
        String updateReparationQuery = "UPDATE réparation SET vehicule_id=?, type_réparation=?, date_réparation=?, " +
                "cout=?, fournisseur=?, facture=?, rapport_réparation=? WHERE id_réparation=?";
        String insertReparationQuery = "INSERT INTO réparation (vehicule_id, type_réparation, date_réparation, " +
                "cout, fournisseur, facture, rapport_réparation) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectVehiculeIdQuery)) {
            selectStmt.setString(1, reparation.getImmatriculation());
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                if (resultSet.next()) {
                    long vehiculeId = resultSet.getLong("id_vehicule");

                    try (PreparedStatement updateOrInsertStmt = connection.prepareStatement(
                            reparation.getIdReparation() != null ? updateReparationQuery : insertReparationQuery)) {

                        int index = 1;
                        updateOrInsertStmt.setLong(index++, vehiculeId);
                        updateOrInsertStmt.setString(index++, reparation.getTypeReparation());
                        updateOrInsertStmt.setDate(index++, new java.sql.Date(reparation.getDateReparation().getTime()));
                        updateOrInsertStmt.setBigDecimal(index++, reparation.getCout());
                        updateOrInsertStmt.setString(index++, reparation.getFournisseur());
                        updateOrInsertStmt.setString(index++, reparation.getFacture());
                        updateOrInsertStmt.setString(index++, reparation.getRapportReparation());

                        if (reparation.getIdReparation() != null) {
                            updateOrInsertStmt.setLong(index, reparation.getIdReparation());
                        }

                        updateOrInsertStmt.executeUpdate();
                    }
                } else {
                    throw new SQLException("Vehicle not found for immatriculation: " + reparation.getImmatriculation());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reparation;
    }

    @Override
    public Reparation updateReparation(Long id, Reparation updatedReparation) {
        // SQL queries
        String selectVehiculeIdQuery = "SELECT id_vehicule FROM vehicule WHERE numéro_immatriculation = ?";
        String updateReparationQuery = "UPDATE réparation SET vehicule_id=?, type_réparation=?, date_réparation=?, " +
                "cout=?, fournisseur=?, facture=?, rapport_réparation=? WHERE id_réparation=?";

        try {
            // Step 1: Retrieve the vehicle ID based on the immatriculation
            Long vehiculeId;
            try (PreparedStatement selectStmt = connection.prepareStatement(selectVehiculeIdQuery)) {
                selectStmt.setString(1, updatedReparation.getImmatriculation());
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next()) {
                        vehiculeId = resultSet.getLong("id_vehicule");
                    } else {
                        throw new SQLException("Vehicle not found for immatriculation: " + updatedReparation.getImmatriculation());
                    }
                }
            }

            // Step 2: Update the repair record
            try (PreparedStatement updateStmt = connection.prepareStatement(updateReparationQuery)) {
                updateStmt.setLong(1, vehiculeId);
                updateStmt.setString(2, updatedReparation.getTypeReparation());
                updateStmt.setDate(3, new java.sql.Date(updatedReparation.getDateReparation().getTime()));
                updateStmt.setBigDecimal(4, updatedReparation.getCout());
                updateStmt.setString(5, updatedReparation.getFournisseur());
                updateStmt.setString(6, updatedReparation.getFacture());
                updateStmt.setString(7, updatedReparation.getRapportReparation());
                updateStmt.setLong(8, id);

                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return updatedReparation;
                } else {
                    throw new SQLException("Failed to update repair with ID: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedReparation;
    }


    @Override
    public void deleteReparation(Long id) {
        String deleteQuery = "DELETE FROM réparation WHERE id_réparation=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String filePath = uploadPath.resolve(file.getOriginalFilename()).toString();
            file.transferTo(new File(filePath));
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Reparation getReparationById(Long id) {
        Reparation reparation = null;
        String sql = "SELECT r.*, v.numéro_immatriculation FROM réparation r " +
                "JOIN vehicule v ON r.vehicule_id = v.id_vehicule WHERE r.id_réparation = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    reparation = new Reparation();
                    reparation.setIdReparation(resultSet.getLong("ID_Réparation"));
                    reparation.setVehiculeId(resultSet.getInt("vehicule_id"));
                    reparation.setTypeReparation(resultSet.getString("type_réparation"));
                    reparation.setDateReparation(resultSet.getDate("date_réparation"));
                    reparation.setCout(resultSet.getBigDecimal("cout"));
                    reparation.setFournisseur(resultSet.getString("fournisseur"));
                    reparation.setFacture(resultSet.getString("facture"));
                    reparation.setRapportReparation(resultSet.getString("rapport_réparation"));
                    reparation.setImmatriculation(resultSet.getString("numéro_immatriculation"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reparation;
    }
}
