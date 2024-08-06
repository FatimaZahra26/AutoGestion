package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.MaterielTechnique;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MaterielTechniqueService {
    public List<MaterielTechnique> getAllMateriels() ;
    public MaterielTechnique getMaterielById(Long id);
    public MaterielTechnique saveMateriel(MaterielTechnique materiel);
    public void deleteMateriel(Long id);
    MaterielTechnique updateMateriel(Long id, MaterielTechnique materiel);


}
