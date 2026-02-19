package com.wms.inbound_receiving_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundResponseDTO {
    private Long id;
    private String supplierName;
    private String productName;
    private int quantity;
    private String status;
}