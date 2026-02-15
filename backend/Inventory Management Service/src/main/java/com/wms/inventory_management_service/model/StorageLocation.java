package com.wms.inventory_management_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "storage_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "zone", nullable = false)
    private String zone;

    @Column(name = "rack_no", nullable = false)
    private String rackNo;

    @Column(name = "bin_no", nullable = false)
    private String binNo;

    @OneToMany(mappedBy = "storageLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventory> inventories;
}
