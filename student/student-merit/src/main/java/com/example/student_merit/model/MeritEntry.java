package com.example.student_merit.model;

import java.util.List;

public class MeritEntry {
    private int rank;
    private Long id;
    private String name;
    private String course;
    private double programmingScore;
    private double mathematicsScore;
    private double averageScore;
    private String performanceCategory;
    private String strongerSubject;
    private List<String> skills;

    public MeritEntry() {
    }

    public MeritEntry(int rank, Long id, String name, String course,
                      double programmingScore, double mathematicsScore,
                      double averageScore, String performanceCategory,
                      String strongerSubject, List<String> skills) {
        this.rank = rank;
        this.id = id;
        this.name = name;
        this.course = course;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.averageScore = averageScore;
        this.performanceCategory = performanceCategory;
        this.strongerSubject = strongerSubject;
        this.skills = skills;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getPerformanceCategory() {
        return performanceCategory;
    }

    public void setPerformanceCategory(String performanceCategory) {
        this.performanceCategory = performanceCategory;
    }

    public String getStrongerSubject() {
        return strongerSubject;
    }

    public void setStrongerSubject(String strongerSubject) {
        this.strongerSubject = strongerSubject;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}