package com.example.backAnana.Repositories;

import com.example.backAnana.Entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    User findByNameAndPassword(String usuario, String clave);

    boolean existsByName(String usuario);

}
