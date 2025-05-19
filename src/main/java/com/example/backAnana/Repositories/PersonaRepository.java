package com.example.backAnana.Repositories;

import com.example.backAnana.Entities.Persona;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends BaseRepository<Persona, Long> {

    Persona findByUserId(Long id);

}
