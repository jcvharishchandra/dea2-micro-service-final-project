package com.wms.workforce_equipment_service.service;

import com.wms.workforce_equipment_service.dto.request.EquipmentRequest;
import com.wms.workforce_equipment_service.dto.response.EquipmentResponse;

import java.util.List;

public interface IEquipmentService {

    List<EquipmentResponse> getAllEquipments();

    EquipmentResponse getEquipmentById(Long id);

    EquipmentResponse createEquipment(EquipmentRequest request);

    EquipmentResponse updateEquipment(Long id, EquipmentRequest request);

    void deleteEquipment(Long id);
}
