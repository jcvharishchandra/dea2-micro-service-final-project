package com.wms.inventory_management_service.repository;

import com.wms.inventory_management_service.model.StorageLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageLocationRepository extends JpaRepository<StorageLocation, Long> {
    Optional<StorageLocation> findByZoneAndRackNoAndBinNo(String zone, String rackNo, String binNo);
}
