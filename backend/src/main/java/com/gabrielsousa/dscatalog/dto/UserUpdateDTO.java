package com.gabrielsousa.dscatalog.dto;

import com.gabrielsousa.dscatalog.services.validation.UserUpdateValid;

import java.io.Serial;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO {
    @Serial
    private static final long serialVersionUID = 1L;
}
