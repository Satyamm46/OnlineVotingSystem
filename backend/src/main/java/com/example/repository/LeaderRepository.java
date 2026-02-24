package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Leader;

public interface LeaderRepository extends JpaRepository<Leader, Long> {}

