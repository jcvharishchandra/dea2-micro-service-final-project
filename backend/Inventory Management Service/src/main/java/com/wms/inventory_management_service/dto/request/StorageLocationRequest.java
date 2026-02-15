package com.wms.inventory_management_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageLocationRequest {

    @NotBlank(message = "Zone is required")
    private String zone;

    @NotBlank(message = "Rack number is required")
    private String rackNo;

    @NotBlank(message = "Bin number is required")
    private String binNo;
}
