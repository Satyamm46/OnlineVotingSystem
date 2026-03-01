package com.example.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.security.AadhaarEncryptionService;
import com.example.security.JwtUtil;
import com.example.service.EmailService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AadhaarEncryptionService encryptionService;
    private final EmailService emailService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager,
                          AadhaarEncryptionService encryptionService,
                          EmailService emailService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.encryptionService = encryptionService;
        this.emailService = emailService;
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody User request) {

        if (request.getAadhaarNumber() == null ||
                !request.getAadhaarNumber().matches("^\\d{12}$")) {
            return "Aadhaar must be exactly 12 digits";
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already registered!";
        }

        String encryptedAadhaar =
                encryptionService.encrypt(request.getAadhaarNumber());

        Optional<User> existing =
                userRepository.findByAadhaarNumber(encryptedAadhaar);

        if (existing.isPresent()) {
            return "Aadhaar already registered!";
        }

        String otp = generateOTP();

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAadhaarNumber(encryptedAadhaar);
        user.setRole("ROLE_USER");
        user.setHasVoted(false);
        user.setOtp(otp);
        user.setOtpVerified(false);

        userRepository.save(user);

        emailService.sendOtp(user.getEmail(), otp);

        return "OTP Sent to Email!";
    }

    @PostMapping("/verify-otp")
public String verifyOtp(@RequestBody Map<String, String> request) {

    String email = request.get("email");
    String otp = request.get("otp");

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!user.getOtp().equals(otp)) {
        return "Invalid OTP!";
    }

    user.setOtpVerified(true);
    user.setOtp(null);
    userRepository.save(user);

    return "Registration Completed Successfully!";
}

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isOtpVerified()) {
            throw new RuntimeException("Please verify OTP first!");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return Map.of("token", token);
    }

    private String generateOTP() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}