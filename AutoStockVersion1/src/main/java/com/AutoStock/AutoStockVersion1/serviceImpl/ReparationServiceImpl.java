        package com.AutoStock.AutoStockVersion1.serviceImpl;

        import com.AutoStock.AutoStockVersion1.Repository.RechargeCarburantRepository;
        import com.AutoStock.AutoStockVersion1.Repository.ReparationRepository;
        import com.AutoStock.AutoStockVersion1.model.Chauffeur;
        import com.AutoStock.AutoStockVersion1.model.Reparation;
        import com.AutoStock.AutoStockVersion1.service.ReparationService;
        import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.web.multipart.MultipartFile;
        import java.nio.file.Files;

        import java.io.File;
        import java.io.IOException;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;

        @Service
        public class ReparationServiceImpl implements ReparationService {

            private Connection connection;

            public ReparationServiceImpl() throws SQLException {
                connection = DBUtil.getConnection();
            }
            @Autowired
            ReparationRepository reparationRepository ;
            @Override
            public List<Reparation> getAllReparations() {
                List<Reparation> reparations = new ArrayList<>();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM réparation");
                    ResultSet rs = preparedStatement.executeQuery();
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
                        reparations.add(reparation);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return reparations;
            }

            @Override
            public Reparation saveReparation(Reparation reparation) {
                try {
                    PreparedStatement preparedStatement;
                    if (reparation.getIdReparation() != null) {
                        // Update existing reparation
                        preparedStatement = connection.prepareStatement(
                                "UPDATE réparation SET vehicule_id=?, type_réparation=?, date_réparation=?, " +
                                        "cout=?, fournisseur=?, facture=?, rapport_réparation=? WHERE id_réparation=?"
                        );
                        preparedStatement.setLong(8, reparation.getIdReparation());
                    } else {
                        // Insert new reparation
                        preparedStatement = connection.prepareStatement(
                                "INSERT INTO réparation (vehicule_id, type_réparation, date_réparation, " +
                                        "cout, fournisseur, facture, rapport_réparation) VALUES (?, ?, ?, ?, ?, ?, ?)"
                        );
                    }
                    preparedStatement.setInt(1, reparation.getVehiculeId());
                    preparedStatement.setString(2, reparation.getTypeReparation());
                    preparedStatement.setDate(3, new java.sql.Date(reparation.getDateReparation().getTime()));
                    preparedStatement.setBigDecimal(4, reparation.getCout());
                    preparedStatement.setString(5, reparation.getFournisseur());
                    preparedStatement.setString(6, reparation.getFacture());
                    preparedStatement.setString(7, reparation.getRapportReparation());

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return reparation;
            }

            @Override
            public Reparation updateReparation(Long id, Reparation updatedReparation) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "UPDATE réparation SET vehicule_id=?, type_réparation=?, date_réparation=?, " +
                                    "cout=?, fournisseur=?, facture=?, rapport_réparation=? WHERE id_réparation=?"
                    );
                    preparedStatement.setInt(1, updatedReparation.getVehiculeId());
                    preparedStatement.setString(2, updatedReparation.getTypeReparation());
                    preparedStatement.setDate(3, new java.sql.Date(updatedReparation.getDateReparation().getTime()));
                    preparedStatement.setBigDecimal(4, updatedReparation.getCout());
                    preparedStatement.setString(5, updatedReparation.getFournisseur());
                    preparedStatement.setString(6, updatedReparation.getFacture());
                    preparedStatement.setString(7, updatedReparation.getRapportReparation());
                    preparedStatement.setLong(8, id);

                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        return updatedReparation;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void deleteReparation(Long id) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM réparation WHERE id_réparation=?");
                    preparedStatement.setLong(1, id);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            private String uploadDir = "=C:\\\\Users\\\\MON PC\\\\Downloads\\\\AutoStockVersion1\\\\AutoStockVersion1\\\\uploads\\\reparations";
            @Override
            public String uploadFile(MultipartFile file) {
                try {
                    // Ensure the destination directory exists
                    File directory = new File(uploadDir);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    // Save the file to the destination directory
                    String filePath = uploadDir + File.separator + file.getOriginalFilename();
                    Path path = Paths.get(filePath);
                    Files.write(path, file.getBytes());

                    return filePath; // Return the absolute path of the saved file
                } catch (IOException e) {
                    e.printStackTrace();
                    return null; // Handle the error as per your requirement
                }
            }

            @Override
            public List<Reparation> getAllReparationByVehiculeId(Long vehiculeId) {

                    return reparationRepository.findAllByVehiculeId(vehiculeId);
                }
        }
