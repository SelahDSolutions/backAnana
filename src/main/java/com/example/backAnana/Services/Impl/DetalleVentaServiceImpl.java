package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.DetalleVenta;
import com.example.backAnana.Entities.Producto;
import com.example.backAnana.Repositories.DetalleVentaRepository;
import com.example.backAnana.Repositories.ProductoRepository;
import com.example.backAnana.Services.DetalleVentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaServiceImpl extends BaseServiceImpl<DetalleVenta, Long> implements DetalleVentaService {

    @Autowired
    private DetalleVentaRepository repository;

    @Autowired
    private ProductoRepository productoRepository;

    public DetalleVentaServiceImpl(DetalleVentaRepository repository) {
        super(repository);
    }

    @Transactional
    public List<DetalleVenta> findAllByVentaId(Long id) throws Exception{
        return repository.findAllByVentaId(id);
    }

    @Override
    @Transactional
    public boolean delete(Long idDetalleVenta) {
        DetalleVenta detalle = repository.findById(idDetalleVenta)
                .orElseThrow(() -> new RuntimeException("DetalleVenta no encontrado"));

        Producto producto = detalle.getProducto();
        if (producto != null && detalle.getCantidad() > 0) {
            // Devolver stock
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        repository.delete(detalle);
        return true;
    }

}
