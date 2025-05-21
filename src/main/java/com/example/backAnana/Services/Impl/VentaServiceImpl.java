package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.DetalleVenta;
import com.example.backAnana.Entities.Venta;
import com.example.backAnana.Repositories.VentaRepository;
import com.example.backAnana.Services.DetalleVentaService;
import com.example.backAnana.Services.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    @Autowired
    private VentaRepository repository;
    @Autowired
    private DetalleVentaService detalleVentaService;

    public VentaServiceImpl(VentaRepository repository) {
        super(repository);
    }

    @Transactional
    public Venta calcularTotal(Long ventaId) throws Exception {
        try {
            // Buscar la venta por su ID
            Venta venta = repository.findById(ventaId)
                    .orElseThrow(() -> new Exception("Pedido no encontrado"));

            // Obtener los detalles de la venta
            List<DetalleVenta> detalles = detalleVentaService.findAllByVentaId(ventaId);
            Double total = 0.0;

            // Calcular el total sumando los subtotales de los detalles
            if (detalles != null && !detalles.isEmpty()) {
                total = detalles.stream()
                        .mapToDouble(DetalleVenta::getSubTotal)
                        .sum();
            }

            // Establecer el total en la venta y guardarla
            venta.setTotal(total);
            return repository.save(venta);

        } catch (Exception ex) {
            throw new Exception("Error al calcular el total del pedido: " + ex.getMessage());
        }
    }

}
