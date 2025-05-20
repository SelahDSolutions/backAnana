package com.example.backAnana.Entities;

import com.example.backAnana.Entities.Enums.RolesUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "user")
public class User extends Base {

    private String usuario;
    private String clave;

    @Enumerated(EnumType.STRING)
    private RolesUsuario rol;

    public void setClave(String clave) {
        this.clave = encriptarClave(clave);
    }

    private String encriptarClave(String clave) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(clave.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
