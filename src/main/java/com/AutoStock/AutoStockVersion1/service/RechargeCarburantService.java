package com.AutoStock.AutoStockVersion1.service;


import com.AutoStock.AutoStockVersion1.model.RechargeCarburant;

import java.util.List;

public interface RechargeCarburantService {
    List<RechargeCarburant> getAllRechargeCarburant();
    RechargeCarburant getRechargeCarburantById(Long id);
    RechargeCarburant createRechargeCarburant(RechargeCarburant rechargeCarburant);
    RechargeCarburant updateRechargeCarburant(Long id, RechargeCarburant rechargeCarburant);
    void deleteRechargeCarburant(Long id);
}
