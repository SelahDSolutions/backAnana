package com.example.backAnana.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "detallePromocion")
public class DetallePromocion extends Base{

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "promocionId")
    @JsonIgnoreProperties("detallePromociones")
    private Promocion promocion;

    @ManyToOne
    @JoinColumn(name = "productoId")
    @JsonIgnoreProperties("detallePromociones")
    private Producto producto;

}
