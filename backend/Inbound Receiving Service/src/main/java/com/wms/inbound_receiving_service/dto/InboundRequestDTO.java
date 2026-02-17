package com.wms.inbound_receiving_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InboundRequestDTO {
    @NotBlank(message = "Supplier name is required")
    private String supplierName;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}