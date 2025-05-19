package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.Producto;
import com.example.backAnana.Repositories.ProductoRepository;
import com.example.backAnana.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    public ProductoServiceImpl(ProductoRepository repository) {
        super(repository);
    }

}
