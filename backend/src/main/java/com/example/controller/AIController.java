package com.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Leader;
import com.example.service.AIService;


@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/recommend")
    public Leader recommend(
            @RequestParam int w1,
            @RequestParam int w2,
            @RequestParam int w3) {

        return aiService.recommend(w1, w2, w3);

        
    }
}

