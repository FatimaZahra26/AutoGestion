package com.AutoStock.AutoStockVersion1.serviceImpl;


import com.AutoStock.AutoStockVersion1.Repository.MaterielBureautiqueRepository;
import com.AutoStock.AutoStockVersion1.model.MaterielBureautique;
import com.AutoStock.AutoStockVersion1.service.MaterielBureautiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MaterielBureautiqueServiceImpl implements MaterielBureautiqueService {

    @Autowired
    private MaterielBureautiqueRepository materielBureautiqueRepository;

    public List<MaterielBureautique> getAllMateriels() {
        return materielBureautiqueRepository.findAll();
    }

    public MaterielBureautique getMaterielById(Long id) {
        return materielBureautiqueRepository.findById(id).orElse(null);
    }

    public MaterielBureautique saveMateriel(MaterielBureautique materiel) {
        return materielBureautiqueRepository.save(materiel);
    }
    @Override
    public MaterielBureautique updateMateriel(Long id, MaterielBureautique materiel) {
        if (materielBureautiqueRepository.existsById(id)) {
            materiel.setId(id);
            return materielBureautiqueRepository.save(materiel);
        } else {
            return null;
        }
    }

    public boolean deleteMateriel(Long id) {
        materielBureautiqueRepository.deleteById(id);
        return false;
    }

    @Override
    public Long getTotalMateriels() {
        return materielBureautiqueRepository.countMateriels();
    }
}
