package com.wms.inventory_management_service.controller;

import com.wms.inventory_management_service.dto.request.StorageLocationRequest;
import com.wms.inventory_management_service.dto.response.StorageLocationResponse;
import com.wms.inventory_management_service.service.StorageLocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/storage-locations")
@RequiredArgsConstructor
public class StorageLocationController {

    private final StorageLocationService storageLocationService;

    @GetMapping
    public ResponseEntity<List<StorageLocationResponse>> getAllStorageLocations() {
        return ResponseEntity.ok(storageLocationService.getAllStorageLocations());
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<StorageLocationResponse> getStorageLocationById(@PathVariable Long locationId) {
        return ResponseEntity.ok(storageLocationService.getStorageLocationById(locationId));
    }

    @PostMapping
    public ResponseEntity<StorageLocationResponse> createStorageLocation(@Valid @RequestBody StorageLocationRequest request) {
        StorageLocationResponse created = storageLocationService.createStorageLocation(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<StorageLocationResponse> updateStorageLocation(@PathVariable Long locationId,
                                                                          @Valid @RequestBody StorageLocationRequest request) {
        return ResponseEntity.ok(storageLocationService.updateStorageLocation(locationId, request));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteStorageLocation(@PathVariable Long locationId) {
        storageLocationService.deleteStorageLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}
