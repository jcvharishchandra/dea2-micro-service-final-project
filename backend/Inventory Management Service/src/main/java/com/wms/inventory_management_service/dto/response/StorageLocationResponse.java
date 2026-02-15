package com.wms.inventory_management_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageLocationResponse {

    private Long locationId;
    private String zone;
    private String rackNo;
    private String binNo;
}
