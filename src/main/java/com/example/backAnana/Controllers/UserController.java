package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.User;
import com.example.backAnana.Services.Impl.UserServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/user")
public class UserController extends BaseControllerImpl<User, UserServiceImpl> {

    private UserController(UserServiceImpl service) {
        super(service);
    }

}
