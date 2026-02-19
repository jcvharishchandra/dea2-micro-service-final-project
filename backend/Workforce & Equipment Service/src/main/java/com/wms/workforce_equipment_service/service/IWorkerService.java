package com.wms.workforce_equipment_service.service;

import com.wms.workforce_equipment_service.dto.request.WorkerRequest;
import com.wms.workforce_equipment_service.dto.response.WorkerResponse;

import java.util.List;

public interface IWorkerService {

    List<WorkerResponse> getAllWorkers();

    WorkerResponse getWorkerById(Long id);

    WorkerResponse createWorker(WorkerRequest request);

    WorkerResponse updateWorker(Long id, WorkerRequest request);

    void deleteWorker(Long id);
}
