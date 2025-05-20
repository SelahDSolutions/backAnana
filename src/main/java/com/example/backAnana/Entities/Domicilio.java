package com.example.backAnana.Entities;

import com.example.backAnana.Entities.Enums.Localidad;
import com.example.backAnana.Entities.Enums.Provincia;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "domicilio")
public class Domicilio extends Base{

    private String calle;
    private int numero;
    private Localidad localidad;
    private Provincia provincia;

}
