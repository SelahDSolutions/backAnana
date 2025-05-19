package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.Domicilio;
import com.example.backAnana.Repositories.DomicilioRepository;
import com.example.backAnana.Services.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomicilioServceImpl extends BaseServiceImpl<Domicilio, Long> implements DomicilioService {

    @Autowired
    private DomicilioRepository repository;

    public DomicilioServceImpl(DomicilioRepository repository) {
        super(repository);
    }

}
