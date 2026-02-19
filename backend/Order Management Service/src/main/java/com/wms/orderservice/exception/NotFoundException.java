package com.wms.orderservice.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String entityName, Object id) {
        super(entityName + " not found with id: " + id);
    }
}
