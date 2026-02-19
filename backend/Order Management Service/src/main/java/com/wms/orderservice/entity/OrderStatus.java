package com.wms.orderservice.entity;

public enum OrderStatus {
    CREATED,
    VALIDATED,
    APPROVED,
    PARTIALLY_APPROVED,
    REJECTED,
    CANCELLED,
    PICKING_REQUESTED,
    PACKED,
    DISPATCHED,
    DELIVERED
}
