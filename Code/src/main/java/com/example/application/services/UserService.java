package com.example.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.progetto.backend.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private String password;

    public User authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }
    public void saveUser(User user) {
        userRepository.save(user); 
    }
}
