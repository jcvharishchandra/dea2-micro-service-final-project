package com.wms.inventory_management_service.service;

import com.wms.inventory_management_service.dto.request.StorageLocationRequest;
import com.wms.inventory_management_service.dto.response.StorageLocationResponse;
import com.wms.inventory_management_service.exception.ConflictException;
import com.wms.inventory_management_service.exception.ResourceNotFoundException;
import com.wms.inventory_management_service.model.StorageLocation;
import com.wms.inventory_management_service.repository.StorageLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageLocationService {

    private final StorageLocationRepository storageLocationRepository;

    public List<StorageLocationResponse> getAllStorageLocations() {
        return storageLocationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public StorageLocationResponse getStorageLocationById(Long locationId) {
        StorageLocation location = storageLocationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("StorageLocation", "locationId", locationId));
        return mapToResponse(location);
    }

    @Transactional
    public StorageLocationResponse createStorageLocation(StorageLocationRequest request) {
        storageLocationRepository.findByZoneAndRackNoAndBinNo(
                request.getZone(), request.getRackNo(), request.getBinNo())
                .ifPresent(location -> {
                    throw new ConflictException("Storage location with zone '" + request.getZone() +
                            "', rack '" + request.getRackNo() + "', and bin '" + request.getBinNo() + "' already exists");
                });

        StorageLocation location = new StorageLocation();
        location.setZone(request.getZone());
        location.setRackNo(request.getRackNo());
        location.setBinNo(request.getBinNo());

        StorageLocation saved = storageLocationRepository.save(location);
        return mapToResponse(saved);
    }

    @Transactional
    public StorageLocationResponse updateStorageLocation(Long locationId, StorageLocationRequest request) {
        StorageLocation location = storageLocationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("StorageLocation", "locationId", locationId));

        storageLocationRepository.findByZoneAndRackNoAndBinNo(
                request.getZone(), request.getRackNo(), request.getBinNo())
                .ifPresent(existingLocation -> {
                    if (!existingLocation.getLocationId().equals(locationId)) {
                        throw new ConflictException("Storage location with zone '" + request.getZone() +
                                "', rack '" + request.getRackNo() + "', and bin '" + request.getBinNo() + "' already exists");
                    }
                });

        location.setZone(request.getZone());
        location.setRackNo(request.getRackNo());
        location.setBinNo(request.getBinNo());

        StorageLocation saved = storageLocationRepository.save(location);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteStorageLocation(Long locationId) {
        StorageLocation location = storageLocationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("StorageLocation", "locationId", locationId));
        storageLocationRepository.delete(location);
    }

    private StorageLocationResponse mapToResponse(StorageLocation location) {
        return new StorageLocationResponse(
                location.getLocationId(),
                location.getZone(),
                location.getRackNo(),
                location.getBinNo()
        );
    }
}
