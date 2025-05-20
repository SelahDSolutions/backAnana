package com.example.backAnana.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "persona")
public class Persona extends Base {

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    @OneToOne
    private Domicilio domicilio;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private User user;

}
