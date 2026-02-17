package com.wms.inbound_receiving_service.service;

import com.wms.inbound_receiving_service.dto.InboundRequestDTO;
import com.wms.inbound_receiving_service.dto.InboundResponseDTO;
import java.util.List;

public interface InboundService {
    InboundResponseDTO receiveShipment(InboundRequestDTO request);
    InboundResponseDTO getShipmentById(Long id);
    List<InboundResponseDTO> getAllShipments(); // Added for CRUD
    InboundResponseDTO updateShipmentStatus(Long id, String status); // Added for CRUD
    void deleteShipment(Long id); // Added for CRUD
}