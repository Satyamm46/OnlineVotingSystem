package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Leader;
import com.example.repository.LeaderRepository;

@RestController
@RequestMapping("/leader")
@CrossOrigin(origins = "http://localhost:3000")
public class LeaderController {

@Autowired 
    private final LeaderRepository leaderRepo;

    // 🔥 Constructor Injection
    public LeaderController(LeaderRepository leaderRepo) {
        this.leaderRepo = leaderRepo;
    }

    @GetMapping("/all")
    public List<Leader> getAllLeaders() {
        return leaderRepo.findAll();
    }

    @PostMapping
    public Leader addLeader(@RequestBody Leader leader) {
        return leaderRepo.save(leader);
    }
}
