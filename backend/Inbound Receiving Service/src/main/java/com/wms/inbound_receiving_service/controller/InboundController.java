package com.wms.inbound_receiving_service.controller;

import com.wms.inbound_receiving_service.dto.InboundRequestDTO;
import com.wms.inbound_receiving_service.dto.InboundResponseDTO;
import com.wms.inbound_receiving_service.service.InboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inbound")
@RequiredArgsConstructor
public class InboundController {

    private final InboundService inboundService;

    @PostMapping("/receive")
    @ResponseStatus(HttpStatus.CREATED)
    public InboundResponseDTO receive(@Valid @RequestBody InboundRequestDTO request) {
        return inboundService.receiveShipment(request);
    }

    @GetMapping("/{id}")
    public InboundResponseDTO getById(@PathVariable Long id) {
        return inboundService.getShipmentById(id);
    }

    @GetMapping("/all")
    public List<InboundResponseDTO> getAll() {
        return inboundService.getAllShipments();
    }

    @PatchMapping("/{id}/status")
    public InboundResponseDTO updateStatus(@PathVariable Long id, @RequestParam String status) {
        return inboundService.updateShipmentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inboundService.deleteShipment(id);
    }
}