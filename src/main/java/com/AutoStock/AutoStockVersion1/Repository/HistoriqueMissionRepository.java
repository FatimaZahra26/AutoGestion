package com.AutoStock.AutoStockVersion1.Repository;

import com.AutoStock.AutoStockVersion1.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueMissionRepository extends JpaRepository<Mission, Long> {
}
