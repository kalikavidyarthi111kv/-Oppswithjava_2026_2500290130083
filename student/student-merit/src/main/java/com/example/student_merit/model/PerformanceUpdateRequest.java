package com.example.student_merit.model;

import java.util.List;

public class PerformanceUpdateRequest {
    private Double programmingScore;
    private Double mathematicsScore;
    private List<String> skills;

    public PerformanceUpdateRequest() {
    }

    public PerformanceUpdateRequest(Double programmingScore, Double mathematicsScore, List<String> skills) {
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.skills = skills;
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}