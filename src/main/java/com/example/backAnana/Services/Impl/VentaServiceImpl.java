package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.DetalleVenta;
import com.example.backAnana.Entities.Venta;
import com.example.backAnana.Repositories.ProductoRepository;
import com.example.backAnana.Repositories.VentaRepository;
import com.example.backAnana.Services.DetalleVentaService;
import com.example.backAnana.Services.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    private final VentaRepository repository;
    private final DetalleVentaService detalleVentaService;
    private final ProductoRepository productoRepository;

    @Autowired
    public VentaServiceImpl(VentaRepository repository, DetalleVentaService detalleVentaService, ProductoRepository productoRepository) {
        super(repository);
        this.repository = repository;
        this.detalleVentaService = detalleVentaService;
        this.productoRepository = productoRepository;
    }

    @Transactional
    @Override
    public Venta update(Venta venta) throws Exception {
        try {

            // Solo si hay detalles, procesarlos
            if (venta.getDetalleVentas() != null && !venta.getDetalleVentas().isEmpty()) {
                double total = 0.0;

                List<DetalleVenta> detalles = detalleVentaService.findAllByVentaId(venta.getId());

                for (DetalleVenta detalle : detalles) {
                    detalle.setVenta(venta); // Asignar la venta al detalle

                    if (detalle.getProducto() != null && detalle.getProducto().getId() != null) {
                        var producto = productoRepository.findById(detalle.getProducto().getId())
                                .orElseThrow(() -> new Exception("Producto no encontrado"));

                        int nuevoStock = producto.getStock() - detalle.getCantidad();
                        if (nuevoStock < 0) {
                            throw new Exception("Stock insuficiente para el producto: " + producto.getDenominacion());
                        }

                        System.out.println("Actualizando producto: " + producto.getDenominacion() +
                                " | Nuevo stock: " + producto.getStock() +
                                " | CantVendida: " + producto.getCantidadVendida());

                        producto.setStock(nuevoStock);
                        producto.setCantidadVendida(producto.getCantidadVendida() + detalle.getCantidad());
                        productoRepository.save(producto);
                    }

                    // Acumular subtotal
                    total += detalle.getSubTotal();

                    // Guardar detalle
                    detalleVentaService.save(detalle);
                }

                // Actualizar total con envío (si existe)
                venta.setTotal(total + (venta.getEnvio() != null ? venta.getEnvio() : 0));
            } else {
                // Si no hay detalles, solo agregar envío (si existe)
                venta.setTotal(venta.getEnvio() != null ? venta.getEnvio() : 0);
            }

            return repository.save(venta);
        } catch (Exception e) {
            throw new Exception("Error guardando la venta: " + e.getMessage());
        }
    }

}
