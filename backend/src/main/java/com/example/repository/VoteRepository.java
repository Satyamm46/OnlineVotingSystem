package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {}
