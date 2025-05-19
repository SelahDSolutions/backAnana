package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.Venta;
import com.example.backAnana.Repositories.VentaRepository;
import com.example.backAnana.Services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    @Autowired
    private VentaRepository repository;

    public VentaServiceImpl(VentaRepository repository) {
        super(repository);
    }

}
