package com.example.backAnana.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "detalleVenta")
public class DetalleVenta extends Base{

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "ventaId")
    @JsonIgnoreProperties("detalleVentas")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "productoId")
    @JsonIgnoreProperties("detalleVentas")
    private Producto producto;

    //Método subtotal
    public Double getSubTotal(){
        if(producto != null){
            return cantidad * producto.getPrecio();
        }
        return 0.0;
    }

}
