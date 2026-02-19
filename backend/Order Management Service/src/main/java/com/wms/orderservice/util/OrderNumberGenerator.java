package com.wms.orderservice.util;

import com.wms.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@RequiredArgsConstructor
public class OrderNumberGenerator {

    private final OrderRepository orderRepository;

    /**
     * Generates order numbers in the format: ORD-YYYY-NNNNNN
     * Example: ORD-2026-000001
     */
    public String generateOrderNumber() {
        int currentYear = Year.now().getValue();
        String prefix = "ORD-" + currentYear + "-";
        String likePrefix = prefix + "%";

        int maxSequence = orderRepository.findMaxSequenceByPrefix(likePrefix);
        int nextSequence = maxSequence + 1;

        return prefix + String.format("%06d", nextSequence);
    }
}
