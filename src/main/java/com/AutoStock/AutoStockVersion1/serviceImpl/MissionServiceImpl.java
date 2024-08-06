package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.Repository.ChauffeurRepository;
import com.AutoStock.AutoStockVersion1.Repository.HistoriqueMissionRepository;
import com.AutoStock.AutoStockVersion1.Repository.MissionRepository;
import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import com.AutoStock.AutoStockVersion1.model.HistoriqueMission;
import com.AutoStock.AutoStockVersion1.model.Mission;
import com.AutoStock.AutoStockVersion1.model.Vehicule;
import com.AutoStock.AutoStockVersion1.service.MissionService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {
    private static final Logger logger = LoggerFactory.getLogger(MissionServiceImpl.class);
    private final Connection connection;

    @Autowired
    private ChauffeurRepository chauffeurRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private HistoriqueMissionRepository historiqueMissionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public MissionServiceImpl() throws SQLException {
        this.connection = DBUtil.getConnection();
    }

    private Long getVehiculeIdByNumeroImmatriculation(String numeroImmatriculation) {
        String query = "SELECT ID_Vehicule FROM vehicule WHERE numéro_immatriculation = ?";
        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setString(1, numeroImmatriculation);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getLong("ID_Vehicule");
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL lors de la récupération de l'ID du véhicule par numéro d'immatriculation.", e);
        }
        return null;
    }

    @Override
    public List<Vehicule> getUnassignedVehicles() throws SQLException {
        List<Vehicule> vehicules = new ArrayList<>();
        String query = "SELECT * \n" +
                "FROM vehicule v \n" +
                "WHERE v.ID_Vehicule NOT IN (\n" +
                "    SELECT c.Vehicule_ID \n" +
                "    FROM chauffeur c\n" +
                "    UNION\n" +
                "    SELECT m.id_vehicule \n" +
                "    FROM mission m\n" +
                ")\n";
        try (PreparedStatement pr = connection.prepareStatement(query)) {
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Vehicule vehicule = new Vehicule();
                vehicule.setIdVehicule(rs.getLong("ID_Vehicule"));
                vehicule.setTypePermis(rs.getString("type_permis"));
                vehicule.setMarque(rs.getString("marque"));
                vehicule.setModele(rs.getString("modele"));
                vehicule.setEtat(rs.getString("etat"));
                vehicule.setAnnee(rs.getInt("Annee"));
                vehicule.setTypeCarburant(rs.getString("type_carburant"));
                vehicule.setNumeroImmatriculation(rs.getString("numéro_immatriculation"));
                vehicule.setKilometrageActuel(rs.getInt("Kilometrage_Actuel"));
                vehicule.setDateAcquisition(rs.getDate("date_acquisition"));
                vehicule.setCarteGrise(rs.getString("carte_grise"));
                vehicule.setAssurance(rs.getString("assurance"));
                vehicule.setVignette(rs.getString("vignette"));
                vehicule.setPhoto(rs.getString("photo"));
                vehicules.add(vehicule);
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL lors de la récupération des véhicules non assignés.", e);
        }
        return vehicules;
    }

    @Override
    public ResponseEntity<String> addMission(Mission mission) {
        Long idVehicule = getVehiculeIdByNumeroImmatriculation(mission.getImmatriculation());
        if (idVehicule == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Numéro d'immatriculation invalide.");
        }

        String query = "INSERT INTO mission (nom_employee, prenom_employee, cin_employee, id_vehicule, ville_depart, ville_arrivee, date_depart, date_arrivee, montant_recharge_carburant) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mission.getNomEmployee());
            preparedStatement.setString(2, mission.getPrenomEmployee());
            preparedStatement.setString(3, mission.getCinEmployee());
            preparedStatement.setLong(4, idVehicule);
            preparedStatement.setString(5, mission.getVilleDepart());
            preparedStatement.setString(6, mission.getVilleArrivee());
            preparedStatement.setDate(7, mission.getDateDepart());
            preparedStatement.setDate(8, mission.getDateArrivee());
            preparedStatement.setBigDecimal(9, mission.getMontantRechargeCarburant());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Mission ajoutée avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la mission.");
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL lors de l'ajout de la mission.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur SQL lors de l'ajout de la mission.");
        }
    }


    @Override
    public Mission getMissionById(Long id) {
        return missionRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<String> updateMission(Long id, Mission mission) {
        Long idVehicule = getVehiculeIdByNumeroImmatriculation(mission.getImmatriculation());
        if (idVehicule == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Numéro d'immatriculation invalide.");
        }

        if (missionRepository.existsById(id)) {
            mission.setId(id);
            mission.setIdVehicule(idVehicule);
            missionRepository.save(mission);
            return ResponseEntity.ok("Mission mise à jour avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mission non trouvée.");
        }
    }

    private RowMapper<Mission> missionRowMapper = (rs, rowNum) -> {
        Mission mission = new Mission();
        mission.setId(rs.getLong("id"));
        mission.setNomEmployee(rs.getString("nom_employee"));
        mission.setPrenomEmployee(rs.getString("prenom_employee"));
        mission.setCinEmployee(rs.getString("cin_employee"));
        mission.setIdVehicule(rs.getLong("id_vehicule"));
        mission.setVilleDepart(rs.getString("ville_depart"));
        mission.setVilleArrivee(rs.getString("ville_arrivee"));
        mission.setDateDepart(rs.getDate("date_depart"));
        mission.setDateArrivee(rs.getDate("date_arrivee"));
        mission.setMontantRechargeCarburant(rs.getBigDecimal("montant_recharge_carburant"));
        return mission;
    };

    private RowMapper<HistoriqueMission> historiqueMissionRowMapper = (rs, rowNum) -> {
        HistoriqueMission historique = new HistoriqueMission();
        historique.setId(rs.getLong("id"));
        historique.setMissionId(rs.getLong("mission_id"));
        historique.setNomEmployee(rs.getString("nom_employee"));
        historique.setPrenomEmployee(rs.getString("prenom_employee"));
        historique.setCinEmployee(rs.getString("cin_employee"));
        historique.setIdVehicule(rs.getLong("id_vehicule"));
        historique.setVilleDepart(rs.getString("ville_depart"));
        historique.setVilleArrivee(rs.getString("ville_arrivee"));
        historique.setDateDepart(rs.getDate("date_depart"));
        historique.setDateArrivee(rs.getDate("date_arrivee"));
        historique.setMontantRechargeCarburant(rs.getBigDecimal("montant_recharge_carburant"));
        return historique;
    };

    @Override
    public List<Mission> getAllMissions() {
        String sql = "SELECT * FROM mission";
        try {
            return jdbcTemplate.query(sql, missionRowMapper);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des missions.", e);
            return new ArrayList<>();
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteMission(Long id) {
        try {
            // Fetch the mission
            String selectMissionQuery = "SELECT * FROM mission WHERE id = ?";
            Mission mission = jdbcTemplate.queryForObject(selectMissionQuery, new Object[]{id}, missionRowMapper);

            if (mission != null) {
                // Insert into historique_mission
                String insertHistoriqueQuery = "INSERT INTO historique_mission (mission_id, nom_employee, prenom_employee, cin_employee, id_vehicule, ville_depart, ville_arrivee, date_depart, date_arrivee, montant_recharge_carburant) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try {
                    jdbcTemplate.update(insertHistoriqueQuery,
                            id,
                            mission.getNomEmployee(),
                            mission.getPrenomEmployee(),
                            mission.getCinEmployee(),
                            mission.getIdVehicule(),
                            mission.getVilleDepart(),
                            mission.getVilleArrivee(),
                            mission.getDateDepart(),
                            mission.getDateArrivee(),
                            mission.getMontantRechargeCarburant());
                    logger.info("Inserted record into historique_mission successfully");
                } catch (DataAccessException e) {
                    logger.error("Failed to insert record into historique_mission", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'insertion dans historique_mission: " + e.getMessage());
                }

                // Delete from mission
                String deleteMissionQuery = "DELETE FROM mission WHERE id = ?";
                int rowsAffected = jdbcTemplate.update(deleteMissionQuery, id);
                logger.info("Rows affected by delete: " + rowsAffected);

                if (rowsAffected > 0) {
                    return ResponseEntity.ok("Mission supprimée avec succès");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de la mission.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mission non trouvée.");
            }
        } catch (DataAccessException e) {
            // Handle exceptions such as SQL errors
            logger.error("Erreur lors de la suppression de la mission", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de la mission: " + e.getMessage());
        }
    }




}
