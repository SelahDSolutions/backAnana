package com.example.backAnana.Repositories;

import com.example.backAnana.Entities.Producto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {

    // Método para verificar si el código ya existe
    boolean existsByCodigo(String codigo);

    // Método para buscar por código
    Optional<Producto> findByCodigo(String codigo);

    //Método para buscar por denominacion
    Optional<Producto> findByDenominacion(String denominacion);

}
