package com.wms.inbound_receiving_service.repository;

import com.wms.inbound_receiving_service.model.InboundReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InboundReceiptRepository extends JpaRepository<InboundReceipt, Long> {

    List<InboundReceipt> findAllByOrderByReceiptDateDesc();
}
