package com.AutoStock.AutoStockVersion1.service;


import com.AutoStock.AutoStockVersion1.model.MaterielBureautique;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterielBureautiqueService {
    public List<MaterielBureautique> getAllMateriels();
    public MaterielBureautique getMaterielById(Long id);
    public MaterielBureautique saveMateriel(MaterielBureautique materiel);
    public boolean deleteMateriel(Long id);
    public Long getTotalMateriels();
    public MaterielBureautique updateMateriel(Long id, MaterielBureautique materiel);
}
