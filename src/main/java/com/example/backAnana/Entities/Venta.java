package com.example.backAnana.Entities;

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

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("venta")
    private Set<DetalleVenta> detalleVentas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
