package com.wms.workforce_equipment_service.service;

import com.wms.workforce_equipment_service.dto.request.MaintenanceLogRequest;
import com.wms.workforce_equipment_service.dto.response.MaintenanceLogResponse;

import java.util.List;

public interface IMaintenanceLogService {

    List<MaintenanceLogResponse> getAllMaintenanceLogs();

    MaintenanceLogResponse getMaintenanceLogById(Long id);

    List<MaintenanceLogResponse> getMaintenanceLogsByEquipmentId(Long equipmentId);

    MaintenanceLogResponse createMaintenanceLog(MaintenanceLogRequest request);

    MaintenanceLogResponse updateMaintenanceLog(Long id, MaintenanceLogRequest request);

    void deleteMaintenanceLog(Long id);
}
