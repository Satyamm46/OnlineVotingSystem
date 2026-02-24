package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Leader;
import com.example.repository.LeaderRepository;

@Service
public class AIService {

    private final LeaderRepository leaderRepo;

    // 🔥 Constructor Injection (VERY IMPORTANT)
    public AIService(LeaderRepository leaderRepo) {
        this.leaderRepo = leaderRepo;
    }

    public Leader recommend(int w1, int w2, int w3) {

        List<Leader> leaders = leaderRepo.findAll();

        Leader best = null;
        double maxScore = -1;

        for (Leader l : leaders) {

            double achievementScore = l.getAchievements() / 20.0;

            double score =
                    (l.getExperience() * w1) +
                    (l.getRating() * w2) +
                    (achievementScore * w3);

            if (score > maxScore) {
                maxScore = score;
                best = l;
            }
        }

        return best;
    }
}
