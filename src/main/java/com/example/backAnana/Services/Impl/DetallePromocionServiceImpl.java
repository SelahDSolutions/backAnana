package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.DetallePromocion;
import com.example.backAnana.Repositories.DetallePromocionRepository;
import com.example.backAnana.Services.DetallePromocionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePromocionServiceImpl extends BaseServiceImpl<DetallePromocion, Long> implements DetallePromocionService {

    @Autowired
    private DetallePromocionRepository repository;

    public DetallePromocionServiceImpl(DetallePromocionRepository repository) {
        super(repository);
    }

    @Transactional
    public List<DetallePromocion> findAllByPromocionId(Long id) throws Exception{
        return repository.findAllByPromocionId(id);
    }

}
