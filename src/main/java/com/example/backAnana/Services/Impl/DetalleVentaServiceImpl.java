package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.DetalleVenta;
import com.example.backAnana.Repositories.DetalleVentaRepository;
import com.example.backAnana.Services.DetalleVentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaServiceImpl extends BaseServiceImpl<DetalleVenta, Long> implements DetalleVentaService {

    @Autowired
    private DetalleVentaRepository repository;

    public DetalleVentaServiceImpl(DetalleVentaRepository repository) {
        super(repository);
    }

    @Transactional
    public List<DetalleVenta> findAllByVentaId(Long id) throws Exception{
        return repository.findAllByVentaId(id);
    }

}
