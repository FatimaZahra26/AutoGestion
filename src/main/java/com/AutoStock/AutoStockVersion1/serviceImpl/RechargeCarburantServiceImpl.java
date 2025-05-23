package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.Repository.RechargeCarburantRepository;
import com.AutoStock.AutoStockVersion1.model.RechargeCarburant;
import com.AutoStock.AutoStockVersion1.service.RechargeCarburantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RechargeCarburantServiceImpl implements RechargeCarburantService {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private RechargeCarburantRepository rechargeCarburantRepository;

    @Override
    public List<RechargeCarburant> getAllRechargeCarburant() {
        List<RechargeCarburant> recharges = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rechargecarburant");
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                RechargeCarburant recharge = new RechargeCarburant();
                recharge.setId(rs.getLong("id_recharge"));
                recharge.setVehiculeId(rs.getLong("vehicule_id"));
                recharge.setDateRecharge(rs.getDate("date_recharge"));
                recharge.setQuantiteCarburant(rs.getDouble("quantité_carburant"));
                recharge.setCout(rs.getDouble("Coût"));
                recharge.setFournisseur(rs.getString("fournisseur"));
                recharge.setLieuRecharge(rs.getString("lieu_recharge"));
                recharges.add(recharge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recharges;
    }

    @Override
    public RechargeCarburant getRechargeCarburantById(Long id) {
        RechargeCarburant recharge = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM recharge_carburant WHERE id_recharge = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                recharge = new RechargeCarburant();
                recharge.setId(rs.getLong("ID_Recharge"));
                recharge.setVehiculeId(rs.getLong("vehicule_id"));
                recharge.setDateRecharge(rs.getDate("date_recharge"));
                recharge.setQuantiteCarburant(rs.getDouble("quantité_carburant"));
                recharge.setCout(rs.getDouble("Coût"));
                recharge.setFournisseur(rs.getString("fournisseur"));
                recharge.setLieuRecharge(rs.getString("lieu_recharge"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (recharge == null) {
            throw new RuntimeException("Recharge Carburant not found with id: " + id);
        }
        return recharge;
    }

    @Override
    public RechargeCarburant createRechargeCarburant(RechargeCarburant rechargeCarburant) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO rechargeCarburant (vehicule_id, date_recharge, quantité_carburant, Coût, fournisseur, lieu_recharge) " +
                             "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, rechargeCarburant.getVehiculeId());
            preparedStatement.setDate(2, new Date(rechargeCarburant.getDateRecharge().getTime()));
            preparedStatement.setDouble(3, rechargeCarburant.getQuantiteCarburant());
            preparedStatement.setDouble(4, rechargeCarburant.getCout());
            preparedStatement.setString(5, rechargeCarburant.getFournisseur());
            preparedStatement.setString(6, rechargeCarburant.getLieuRecharge());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating recharge carburant failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rechargeCarburant.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating recharge carburant failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rechargeCarburant;
    }

    @Override
    public RechargeCarburant updateRechargeCarburant(Long id, RechargeCarburant rechargeCarburant) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE rechargeCarburant SET vehicule_id = ?, date_recharge = ?, quantité_carburant = ?, " +
                             "Coût = ?, fournisseur = ?, lieu_recharge = ? WHERE ID_Recharge = ?")) {
            preparedStatement.setLong(1, rechargeCarburant.getVehiculeId());
            preparedStatement.setDate(2, new Date(rechargeCarburant.getDateRecharge().getTime()));
            preparedStatement.setDouble(3, rechargeCarburant.getQuantiteCarburant());
            preparedStatement.setDouble(4, rechargeCarburant.getCout());
            preparedStatement.setString(5, rechargeCarburant.getFournisseur());
            preparedStatement.setString(6, rechargeCarburant.getLieuRecharge());
            preparedStatement.setLong(7, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Updating recharge carburant failed, no rows affected.");
            }

            // Log the update in the historical table
            logRechargeCarburantHistory(rechargeCarburant, "UPDATE");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getRechargeCarburantById(id);
    }

    @Override
    public void deleteRechargeCarburant(Long id) {
        RechargeCarburant rechargeCarburant = getRechargeCarburantById(id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM rechargeCarburant WHERE ID_Recharge = ?")) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Deleting recharge carburant failed, no rows affected.");
            }

            // Log the deletion in the historical table
            logRechargeCarburantHistory(rechargeCarburant, "DELETE");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void logRechargeCarburantHistory(RechargeCarburant rechargeCarburant, String action) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO historique_rechargecarburant (id_recharge, vehicule_id, date_recharge, quantite_carburant, cout, fournisseur, lieu_recharge, action) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setLong(1, rechargeCarburant.getId());
            preparedStatement.setLong(2, rechargeCarburant.getVehiculeId());
            preparedStatement.setDate(3, new Date(rechargeCarburant.getDateRecharge().getTime()));
            preparedStatement.setDouble(4, rechargeCarburant.getQuantiteCarburant());
            preparedStatement.setDouble(5, rechargeCarburant.getCout());
            preparedStatement.setString(6, rechargeCarburant.getFournisseur());
            preparedStatement.setString(7, rechargeCarburant.getLieuRecharge());
            preparedStatement.setString(8, action);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
