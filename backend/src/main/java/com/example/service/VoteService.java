package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.entity.Vote;
import com.example.repository.UserRepository;
import com.example.repository.VoteRepository;

@Service
public class VoteService {

    private final VoteRepository voteRepo;
    private final UserRepository userRepo;

    // 🔥 Constructor Injection (IMPORTANT)
    public VoteService(VoteRepository voteRepo,
                       UserRepository userRepo) {
        this.voteRepo = voteRepo;
        this.userRepo = userRepo;
    }

    public String castVote(Long userId, Long leaderId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isHasVoted()) {
            return "You have already voted!";
        }

        Vote vote = new Vote();
        vote.setUserId(user.getId());
        vote.setLeaderId(leaderId);

        voteRepo.save(vote);

        user.setHasVoted(true);
        userRepo.save(user);

        return "Vote Cast Successfully!";
    }
}
