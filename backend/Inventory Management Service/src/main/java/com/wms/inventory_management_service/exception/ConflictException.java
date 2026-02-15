package com.wms.inventory_management_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends ServiceException {

    public ConflictException(String message) {
        super(message);
    }
}
