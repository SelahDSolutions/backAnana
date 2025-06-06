package com.example.backAnana.Entities;

import com.example.backAnana.Entities.Enums.EstadoVenta;
import com.example.backAnana.Entities.Enums.FormaPago;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "venta")
public class Venta extends Base{

    private LocalDate fecha;
    private Double total;
    private FormaPago formaPago;
    private Double envio;
    private EstadoVenta estadoVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @JsonIgnoreProperties(value = {"venta"}, allowSetters = true)
    private Set<DetalleVenta> detalleVentas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    // Método auxiliar para agregar detalle y sincronizar la relación bidireccional
    public void addDetalleVenta(DetalleVenta detalle) {
        detalle.setVenta(this);
        this.detalleVentas.add(detalle);
    }

}
