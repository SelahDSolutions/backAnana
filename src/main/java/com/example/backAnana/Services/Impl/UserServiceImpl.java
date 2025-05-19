package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.User;
import com.example.backAnana.Repositories.UserRepository;
import com.example.backAnana.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

}
