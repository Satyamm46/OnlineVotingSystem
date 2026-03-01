package com.example.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Leader;
import com.example.repository.LeaderRepository;

@Service
public class AIService {

    private final LeaderRepository leaderRepo;

    public AIService(LeaderRepository leaderRepo) {
        this.leaderRepo = leaderRepo;
    }

    public double calculateScore(Leader leader) {

        double score = 0;

        score += leader.getExperience() * 3;

        if (leader.getAchievements() != null) {
            score += leader.getAchievements().length() / 15.0;
        }

        score += getEducationBonus(leader.getEducation());

        score -= leader.getCorruptionCases() * 7;

        score += leader.getVotes() * 1.5;

        return score;
    }

    private double getEducationBonus(String education) {

        if (education == null) return 0;

        education = education.toLowerCase();

        if (education.contains("phd")) return 20;
        if (education.contains("master")) return 15;
        if (education.contains("graduate")) return 10;

        return 5;
    }

    public Leader recommendBestLeader() {

        List<Leader> leaders = leaderRepo.findAll();

        if (leaders.isEmpty()) {
            throw new RuntimeException("No leaders available");
        }

        return leaders.stream()
                .max(Comparator.comparingDouble(this::calculateScore))
                .orElseThrow();
    }

    public List<Leader> rankLeaders() {

        List<Leader> leaders = leaderRepo.findAll();

        leaders.sort((l1, l2) ->
                Double.compare(calculateScore(l2), calculateScore(l1))
        );

        return leaders;
    }
}