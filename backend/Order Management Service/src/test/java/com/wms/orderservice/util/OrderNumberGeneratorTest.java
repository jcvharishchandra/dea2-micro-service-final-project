package com.wms.orderservice.util;

import com.wms.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderNumberGeneratorTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderNumberGenerator orderNumberGenerator;

    @Test
    @DisplayName("Should generate first order number of the year")
    void generateOrderNumber_firstOrder() {
        when(orderRepository.findMaxSequenceByPrefix(anyString())).thenReturn(0);

        String orderNumber = orderNumberGenerator.generateOrderNumber();

        int year = Year.now().getValue();
        assertEquals("ORD-" + year + "-000001", orderNumber);
    }

    @Test
    @DisplayName("Should generate sequential order number")
    void generateOrderNumber_sequential() {
        when(orderRepository.findMaxSequenceByPrefix(anyString())).thenReturn(42);

        String orderNumber = orderNumberGenerator.generateOrderNumber();

        int year = Year.now().getValue();
        assertEquals("ORD-" + year + "-000043", orderNumber);
    }

    @Test
    @DisplayName("Order number should match expected pattern")
    void generateOrderNumber_matchesPattern() {
        when(orderRepository.findMaxSequenceByPrefix(anyString())).thenReturn(999);

        String orderNumber = orderNumberGenerator.generateOrderNumber();

        assertTrue(orderNumber.matches("^ORD-\\d{4}-\\d{6}$"),
                "Order number should match pattern ORD-YYYY-NNNNNN, got: " + orderNumber);
    }
}
