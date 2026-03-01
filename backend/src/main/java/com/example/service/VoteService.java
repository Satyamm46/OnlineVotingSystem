package com.example.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.VoteSlipDTO;
import com.example.entity.Leader;
import com.example.entity.User;
import com.example.repository.LeaderRepository;
import com.example.repository.UserRepository;
import com.example.security.AadhaarEncryptionService;

@Service
public class VoteService {

    private final UserRepository userRepository;
    private final LeaderRepository leaderRepository;
    private final AadhaarEncryptionService encryptionService;

    public VoteService(UserRepository userRepository,
                       LeaderRepository leaderRepository,
                       AadhaarEncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.leaderRepository = leaderRepository;
        this.encryptionService = encryptionService;
    }

    @Transactional
    public VoteSlipDTO castVote(String email, Long leaderId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isHasVoted()) {
            throw new RuntimeException("You have already voted!");
        }

        Leader leader = leaderRepository.findById(leaderId)
                .orElseThrow(() -> new RuntimeException("Leader not found"));

        leader.setVotes(leader.getVotes() + 1);
        leaderRepository.save(leader);

        user.setHasVoted(true);
        userRepository.save(user);

        String decryptedAadhaar =
                encryptionService.decrypt(user.getAadhaarNumber());

        String maskedAadhaar =
                "XXXX-XXXX-" + decryptedAadhaar.substring(8);

        VoteSlipDTO slip = new VoteSlipDTO();
        slip.setVoteId("VOTE-" + System.currentTimeMillis());
        slip.setVoterEmail(email);
        slip.setLeaderName(leader.getName());
        slip.setParty(leader.getParty());
        slip.setVotedAt(LocalDateTime.now());
        slip.setAadhaar(maskedAadhaar);

        return slip;
    }
}