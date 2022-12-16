package com.gabrielsousa.dscatalog.dto;

import com.gabrielsousa.dscatalog.services.validation.UserInsertValid;

import javax.validation.constraints.NotBlank;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

    @NotBlank
    private String password;

    public UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
