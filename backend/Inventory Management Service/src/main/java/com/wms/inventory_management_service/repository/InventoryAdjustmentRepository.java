package com.wms.inventory_management_service.repository;

import com.wms.inventory_management_service.model.InventoryAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryAdjustmentRepository extends JpaRepository<InventoryAdjustment, Long> {
    List<InventoryAdjustment> findByInventoryInventoryId(Long inventoryId);
    List<InventoryAdjustment> findByAdjustmentType(InventoryAdjustment.AdjustmentType adjustmentType);
}
