package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Leader;
import com.example.repository.LeaderRepository;

@RestController
@RequestMapping("/leader")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LeaderController {

    private final LeaderRepository leaderRepo;

    public LeaderController(LeaderRepository leaderRepo) {
        this.leaderRepo = leaderRepo;
    }

    @GetMapping("/all")
    public List<Leader> getAllLeaders() {
        return leaderRepo.findAll();
    }

    @GetMapping("/{id}")
    public Leader getLeaderById(@PathVariable Long id) {
        return leaderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leader not found"));
    }
}