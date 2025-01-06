package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.application.services.UserRepository;
import com.example.progetto.backend.User;

@Component
public class DatabaseTest implements CommandLineRunner {

    private final UserRepository userRepository;

    public DatabaseTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        // Aggiungere un nuovo utente
        User user = new User();
        user.setName("Mario Rossi");
        user.setEmail("mario.rossi@example.com");
        user.setPassword("password");
        user.setRole("CLIENT");
        userRepository.save(user);

        // Recuperare gli utenti
        userRepository.findAll().forEach(u -> System.out.println(u.getName()));
    }
}
