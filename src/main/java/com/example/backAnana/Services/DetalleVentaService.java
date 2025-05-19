package com.example.backAnana.Services;

import com.example.backAnana.Entities.DetalleVenta;

import java.util.List;

public interface DetalleVentaService extends BaseService<DetalleVenta, Long> {

    List<DetalleVenta> findAllByVentaId(Long id) throws Exception;

}
