package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.Persona;
import com.example.backAnana.Repositories.PersonaRepository;
import com.example.backAnana.Services.PersonaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaServiceImpl extends BaseServiceImpl<Persona, Long> implements PersonaService {

    @Autowired
    private PersonaRepository repository;

    public PersonaServiceImpl(PersonaRepository repository) {
        super(repository);
    }

    @Transactional
    public Persona findByUserId(Long id) throws Exception{
        return repository.findByUserId(id);
    }

}
