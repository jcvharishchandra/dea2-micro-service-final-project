package com.wms.inventory_management_service.dto.response;

import com.wms.inventory_management_service.model.InventoryAdjustment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAdjustmentResponse {

    private Long adjustmentId;
    private InventoryAdjustment.AdjustmentType adjustmentType;
    private Integer quantityChange;
    private String reason;
    private String adjustedBy;
    private Long inventoryId;
    private String batchNo;
    private String productName;
    private LocalDateTime createdAt;
}
