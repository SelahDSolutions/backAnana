package com.example.backAnana.Services;

import com.example.backAnana.Entities.Producto;

public interface ProductoService extends BaseService<Producto, Long> {

    Producto updateStock(String codigo, int stock);

    void validarCodigoUnico(String codigo, Long id);

    Producto findByCodigo(String codigo);
    Producto findByDenominacion(String denominacion);
}
