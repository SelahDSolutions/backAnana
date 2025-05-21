package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.Dtos.LoginRequest;
import com.example.backAnana.Entities.Dtos.LoginResponse;
import com.example.backAnana.Entities.User;
import com.example.backAnana.Services.Impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserServiceImpl userServiceImpl;

    public AuthController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Login request received for user: {}", loginRequest.getUsuario());
            User user = userServiceImpl.authenticate(loginRequest.getUsuario(), loginRequest.getClave());
            if (user != null) {
                logger.info("Login successful for user: {}", user.getUsuario());
                return ResponseEntity.ok(new LoginResponse(user.getId(), user.getUsuario(), "Login exitoso", user.getRol()));
            } else {
                logger.info("Login failed for user: {}", loginRequest.getUsuario());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            logger.error("Internal server error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}




