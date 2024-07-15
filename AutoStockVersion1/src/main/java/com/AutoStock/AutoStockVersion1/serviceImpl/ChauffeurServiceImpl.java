package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import com.AutoStock.AutoStockVersion1.service.ChauffeurService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChauffeurServiceImpl implements ChauffeurService {
    Connection connection;

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
}
