package com.wms.inventory_management_service.dto.response;

import com.wms.inventory_management_service.model.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LowStockAlertResponse {

    private List<InventoryResponse> lowStockItems;
    private Integer totalLowStockItems;
    private String alertMessage;
}
