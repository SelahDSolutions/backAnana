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

    private final VentaRepository repository;
    private final DetalleVentaService detalleVentaService;

    @Autowired
    public VentaServiceImpl(VentaRepository repository, DetalleVentaService detalleVentaService) {
        super(repository);
        this.repository = repository;
        this.detalleVentaService = detalleVentaService;
    }

    @Transactional
    @Override
    public Venta save(Venta venta) throws Exception {
        try {
            // Sincronizar relación bidireccional para cada detalle
            for (DetalleVenta detalle : venta.getDetalleVentas()) {
                detalle.setVenta(venta);
            }

            // Calcular total antes de guardar
            double total = venta.getDetalleVentas()
                    .stream()
                    .mapToDouble(DetalleVenta::getSubTotal)
                    .sum();

            venta.setTotal(total + (venta.getEnvio() != null ? venta.getEnvio() : 0));

            // Guardar venta junto con detalles (gracias a cascade ALL)
            return repository.save(venta);
        } catch (Exception e) {
            throw new Exception("Error guardando la venta: " + e.getMessage());
        }
    }

}
