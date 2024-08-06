package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.Repository.MaterielTechniqueRepository;
import com.AutoStock.AutoStockVersion1.model.MaterielTechnique;
import com.AutoStock.AutoStockVersion1.service.MaterielTechniqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterielTechniqueServiceImpl implements MaterielTechniqueService {

    @Autowired
    private MaterielTechniqueRepository materielTechniqueRepository;

    @Override
    public List<MaterielTechnique> getAllMateriels() {
        return materielTechniqueRepository.findAll();
    }

    @Override
    public MaterielTechnique getMaterielById(Long id) {
        return materielTechniqueRepository.findById(id).orElse(null);
    }

    @Override
    public MaterielTechnique saveMateriel(MaterielTechnique materiel) {
        return materielTechniqueRepository.save(materiel);
    }
    @Override
    public MaterielTechnique updateMateriel(Long id, MaterielTechnique materiel) {
        if (materielTechniqueRepository.existsById(id)) {
            materiel.setId(id);
            return materielTechniqueRepository.save(materiel);
        } else {
            return null;
        }
    }

    @Override
    public void deleteMateriel(Long id) {
        if (materielTechniqueRepository.existsById(id)) {
            materielTechniqueRepository.deleteById(id);
        }
    }
}
