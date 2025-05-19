package com.example.backAnana.Repositories;

import com.example.backAnana.Entities.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {
}
