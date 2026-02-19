package com.wms.orderservice.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTests {

    @Test
    @DisplayName("NotFoundException with entity name and id")
    void notFoundException_withEntityAndId() {
        NotFoundException ex = new NotFoundException("Order", "abc-123");
        assertEquals("Order not found with id: abc-123", ex.getMessage());
    }

    @Test
    @DisplayName("NotFoundException with message")
    void notFoundException_withMessage() {
        NotFoundException ex = new NotFoundException("Custom message");
        assertEquals("Custom message", ex.getMessage());
    }

    @Test
    @DisplayName("BusinessException with message")
    void businessException_withMessage() {
        BusinessException ex = new BusinessException("Invalid transition");
        assertEquals("Invalid transition", ex.getMessage());
    }

    @Test
    @DisplayName("BusinessException with cause")
    void businessException_withCause() {
        RuntimeException cause = new RuntimeException("root cause");
        BusinessException ex = new BusinessException("Wrapped", cause);
        assertEquals("Wrapped", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    @DisplayName("ExternalServiceException with message")
    void externalServiceException_withMessage() {
        ExternalServiceException ex = new ExternalServiceException("Service down");
        assertEquals("Service down", ex.getMessage());
    }

    @Test
    @DisplayName("ExternalServiceException with cause")
    void externalServiceException_withCause() {
        RuntimeException cause = new RuntimeException("timeout");
        ExternalServiceException ex = new ExternalServiceException("Failed", cause);
        assertEquals("Failed", ex.getMessage());
        assertSame(cause, ex.getCause());
    }
}
