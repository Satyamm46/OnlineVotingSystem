package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
private PasswordEncoder passwordEncoder;

@PostMapping("/register")
public String register(@RequestBody User user) {

    if(userRepository.findByEmail(user.getEmail()).isPresent()) {
        return "Email already exists";
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);

    return "Registered Successfully";
}


   @PostMapping("/login")
public String login(@RequestBody User user) {

    User dbUser = userRepository.findByEmail(user.getEmail()).orElse(null);

    if (dbUser == null) {
        return "User not found";
    }

    if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
        return "Invalid credentials";
    }

    return "Login Successful";
}

}

    

