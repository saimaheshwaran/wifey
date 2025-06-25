package com.sm.newswave.service;

import com.sm.newswave.model.User;
import com.sm.newswave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) throw new RuntimeException("Username is already taken");
        if (userRepository.existsByEmail(user.getEmail())) throw new RuntimeException("Email is already in use");
        user.setName("NA");
        user.setPhone("000-000-0000");
        userRepository.save(user);
    }
}
