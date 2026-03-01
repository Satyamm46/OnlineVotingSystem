package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "leaders")
public class Leader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String party;

    @Column(columnDefinition = "TEXT")
    private String achievements; 

    private int experience;

    private String education;

    @Column(name = "logo")
private String logo;

public String getLogo() {
    return logo;
}

public void setLogo(String logo) {
    this.logo = logo;
}

    @Column(name = "corruption_cases")
    private int corruptionCases = 0;

    private int votes = 0;

    public Leader() {}


    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }

    public String getAchievements() { return achievements; }
    public void setAchievements(String achievements) { this.achievements = achievements; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public int getCorruptionCases() { return corruptionCases; }
    public void setCorruptionCases(int corruptionCases) { this.corruptionCases = corruptionCases; }

    public int getVotes() { return votes; }
    public void setVotes(int votes) { this.votes = votes; }
}