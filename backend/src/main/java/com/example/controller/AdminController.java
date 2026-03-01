package com.example.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Leader;
import com.example.repository.LeaderRepository;

@RestController
@RequestMapping("/admin/leader")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

    private final LeaderRepository leaderRepository;

    public AdminController(LeaderRepository leaderRepository) {
        this.leaderRepository = leaderRepository;
    }

    @PostMapping("/add")
    public Leader addLeader(@RequestBody Leader leader) {
        return leaderRepository.save(leader);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLeader(@PathVariable Long id) {
        leaderRepository.deleteById(id);
        return "Leader deleted successfully";
    }

    @PostMapping("/{id}/upload-logo")
    public String uploadLogo(@PathVariable Long id,
                             @RequestParam("file") MultipartFile file) throws Exception {

        Leader leader = leaderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leader not found"));

        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis()
                + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        leader.setLogo(fileName);
        leaderRepository.save(leader);

        return "Logo uploaded successfully";
    }

    @GetMapping("/all")
    public List<Leader> getAll() {
        return leaderRepository.findAll();
    }
}