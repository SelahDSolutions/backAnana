package com.example.backAnana.Entities.Dtos;

import com.example.backAnana.Entities.Enums.RolesUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Long userId;
    private String usuario;
    private String message;
    private RolesUsuario rol;

    public LoginResponse(Long userId, String usuario, String message, RolesUsuario rol) {
        this.userId = userId;
        this.usuario = usuario;
        this.message = message;
        this.rol = rol;
    }

}
