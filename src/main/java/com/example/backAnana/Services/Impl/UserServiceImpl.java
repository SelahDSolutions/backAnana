package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.User;
import com.example.backAnana.Repositories.UserRepository;
import com.example.backAnana.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    private UserRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    public User authenticate(String usuario, String clave) {
        logger.info("Authenticating user with username: {}", usuario);
        // Encriptar la contraseña proporcionada por el usuario
        String encryptedPassword = encriptarClave(clave);
        logger.info("Encrypted password: {}", encryptedPassword);
        // Buscar al usuario en la base de datos con el nombre de usuario y la contraseña encriptada
        User user = repository.findByNameAndPassword(usuario, encryptedPassword);
        if (user != null) {
            logger.info("User found: {}", user.getUsuario());
        } else {
            logger.info("User not found for username: {}", usuario);
        }
        return user;
    }

    // Método para encriptar la contraseña (copiado de la clase User)
    private String encriptarClave(String clave) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(clave.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error encrypting password", e);
            throw new RuntimeException(e);
        }
    }

}
