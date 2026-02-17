package com.wms.inbound_receiving_service.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Header of a Goods Receiving Note (GRN).
 * This entity tracks who sent the goods and when they arrived.
 */
@Entity
@Table(name = "inbound_receipts")
@Getter
@Setter
public class InboundReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    private LocalDate receiptDate;
    private String status; // e.g., RECEIVED, VERIFIED
    private String grnNumber; // Unique tracking reference

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    // CascadeType.ALL ensures items are saved automatically when the receipt is saved
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InboundReceiptItem> items = new ArrayList<>();
}