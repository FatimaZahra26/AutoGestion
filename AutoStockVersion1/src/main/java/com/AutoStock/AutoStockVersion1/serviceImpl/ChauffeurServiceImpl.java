package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.Repository.ChauffeurRepository;
import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import com.AutoStock.AutoStockVersion1.model.Chauffeur;
import com.AutoStock.AutoStockVersion1.service.ChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ChauffeurRepository chauffeurRepository;

    public ChauffeurServiceImpl() throws SQLException {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Chauffeur> getChauffeurList() throws SQLException {
        return chauffeurRepository.findAll();
    }
    @Override
    public Long countChauffeurs() throws SQLException {
        return chauffeurRepository.count();
    }

    @Override
    public List<Chauffeur> getAllChauffeursByVehiculeId(Long vehiculeId) {
        return chauffeurRepository.findAllByVehiculeId(vehiculeId);
    }
}
