package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import com.AutoStock.AutoStockVersion1.model.PdfGenerator;
import com.AutoStock.AutoStockVersion1.model.Rapport;
import com.AutoStock.AutoStockVersion1.service.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class RapportServiceImpl implements RapportService {

    private final Connection connection;

    @Autowired
    private PdfGenerator pdfGenerator;

    public RapportServiceImpl() throws SQLException {
        this.connection = DBUtil.getConnection();
    }

    @Override
    public List<Rapport> getAllRapports() {
        List<Rapport> rapports = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rapport");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Rapport rapport = new Rapport();
                rapport.setIdRapport(rs.getLong("ID_Rapport"));
                rapport.setFormat(rs.getString("Format"));
                rapport.setTypeRapport(rs.getString("Type_Rapport"));
                rapport.setVehiculeId(rs.getLong("Vehicule_ID"));
                rapport.setDateGeneration(rs.getDate("Date_Génération"));
                rapport.setContenu(rs.getString("Contenu"));
                rapports.add(rapport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rapports;
    }

    @Override
    public Rapport getRapportById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rapport WHERE ID_Rapport = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Rapport rapport = new Rapport();
                rapport.setIdRapport(rs.getLong("ID_Rapport"));
                rapport.setFormat(rs.getString("Format"));
                rapport.setTypeRapport(rs.getString("Type_Rapport"));
                rapport.setVehiculeId(rs.getLong("Vehicule_ID"));
                rapport.setDateGeneration(rs.getDate("Date_Génération"));
                rapport.setContenu(rs.getString("Contenu"));
                return rapport;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Rapport saveRapport(Rapport rapport) {
        try {
            PreparedStatement preparedStatement;
            if (rapport.getIdRapport() != null) {
                // Update existing rapport
                preparedStatement = connection.prepareStatement(
                        "UPDATE rapport SET Format=?, Type_Rapport=?, Vehicule_ID=?, Date_Génération=?, Contenu=? WHERE ID_Rapport=?"
                );
                preparedStatement.setLong(6, rapport.getIdRapport());
            } else {
                // Insert new rapport
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO rapport (Format, Type_Rapport, Vehicule_ID, Date_Génération, Contenu) VALUES (?, ?, ?, ?, ?)"
                );
            }
            preparedStatement.setString(1, rapport.getFormat());
            preparedStatement.setString(2, rapport.getTypeRapport());
            preparedStatement.setLong(3, rapport.getVehiculeId());
            preparedStatement.setDate(4, new java.sql.Date(rapport.getDateGeneration().getTime()));
            preparedStatement.setString(5, rapport.getContenu());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rapport;
    }

    @Override
    public Rapport updateRapport(Long id, Rapport updatedRapport) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE rapport SET Format=?, Type_Rapport=?, Vehicule_ID=?, Date_Génération=?, Contenu=? WHERE ID_Rapport=?"
            );
            preparedStatement.setString(1, updatedRapport.getFormat());
            preparedStatement.setString(2, updatedRapport.getTypeRapport());
            preparedStatement.setLong(3, updatedRapport.getVehiculeId());
            preparedStatement.setDate(4, new java.sql.Date(updatedRapport.getDateGeneration().getTime()));
            preparedStatement.setString(5, updatedRapport.getContenu());
            preparedStatement.setLong(6, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return updatedRapport;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteRapport(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM rapport WHERE ID_Rapport=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rapport> generateRapport(String type, Integer year, Integer month, Integer day) {
        List<Rapport> rapports = new ArrayList<>();
        String sql = "SELECT ID_Rapport, Format, Type_Rapport, Vehicule_ID, Date_Génération, Contenu, total_cost " +
                "FROM rapport " +
                "WHERE Type_Rapport = ? " +
                "AND YEAR(Date_Génération) = ? " +
                "AND MONTH(Date_Génération) = ? " +
                "AND DAY(Date_Génération) = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, day);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Rapport rapport = new Rapport();
                rapport.setIdRapport(rs.getLong("ID_Rapport"));
                rapport.setFormat(rs.getString("Format"));
                rapport.setTypeRapport(rs.getString("Type_Rapport"));
                rapport.setVehiculeId(rs.getLong("Vehicule_ID"));
                rapport.setDateGeneration(rs.getDate("Date_Génération"));
                rapport.setContenu(rs.getString("Contenu"));
                rapport.setTotalCost(rs.getBigDecimal("total_cost"));

                rapports.add(rapport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rapports;
    }    private String generateReportContent(List<Rapport> rapports) {
        StringBuilder content = new StringBuilder();
        content.append("Rapport des coûts\n\n");
        for (Rapport rapport : rapports) {
            content.append("ID: ").append(rapport.getIdRapport()).append("\n");
            content.append("Format: ").append(rapport.getFormat()).append("\n");
            content.append("Type: ").append(rapport.getTypeRapport()).append("\n");
            content.append("Date de génération: ").append(rapport.getDateGeneration()).append("\n");
            content.append("Contenu: ").append(rapport.getContenu()).append("\n\n");
        }

        return content.toString();
    }
}