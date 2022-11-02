package com.gabrielsousa.dscatalog.services.exceptions;

import java.io.Serial;

public class EntityNotFoundException extends RuntimeException {
    @Serial
    private static long serialVersionUID = 1L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
