package com.example.backAnana.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "promocion")
public class Promocion extends Base{

    private String denominacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private Double porcentajeDescuento;

    @OneToMany(mappedBy = "promocion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("promocion")
    private Set<DetallePromocion> detallePromociones = new HashSet<>();

}
