package com.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.VoteService;

@RestController
@RequestMapping("/vote")
@CrossOrigin(origins = "http://localhost:3000")
public class VoteController {

    private final VoteService voteService;

    // 🔥 Constructor Injection
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public String castVote(@RequestParam Long userId,
                           @RequestParam Long leaderId) {
        return voteService.castVote(userId, leaderId);
    }
}
