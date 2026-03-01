package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.VoteSlipDTO;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.VoteService;

@RestController
@RequestMapping("/vote")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class VoteController {

    private final VoteService voteService;
    private final UserRepository userRepository;

    public VoteController(VoteService voteService,
                          UserRepository userRepository) {
        this.voteService = voteService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{leaderId}")
    public VoteSlipDTO vote(@PathVariable Long leaderId,
                            Authentication authentication) {

        String email = authentication.getName();

        return voteService.castVote(email, leaderId);
    }

    @GetMapping("/status")
    public boolean getVoteStatus(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.isHasVoted();
    }
}