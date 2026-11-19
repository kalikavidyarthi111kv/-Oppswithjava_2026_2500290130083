package com.example.student_merit.service;

import com.example.student_merit.model.MeritEntry;
import com.example.student_merit.model.PerformanceUpdateRequest;
import com.example.student_merit.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class StudentService {

    private final Map<Long, Student> studentStore = new LinkedHashMap<>();

    public Student addStudent(Student student) {
        if (student == null || student.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is required.");
        }
        if (studentStore.containsKey(student.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate Student ID: " + student.getId());
        }
        validateScores(student.getProgrammingScore(), student.getMathematicsScore());

        List<String> cleanedSkills = sanitizeSkills(student.getSkills());
        Student newStudent = new Student(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse(),
                student.getProgrammingScore(),
                student.getMathematicsScore(),
                cleanedSkills
        );
        studentStore.put(newStudent.getId(), newStudent);
        return newStudent;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentStore.values());
    }

    public Student getStudentById(Long id) {
        Student student = studentStore.get(id);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with ID: " + id);
        }
        return student;
    }

    public Student updatePerformanceAndSkills(Long id, PerformanceUpdateRequest request) {
        Student existing = getStudentById(id);

        double updatedProg = (request.getProgrammingScore() != null)
                ? request.getProgrammingScore()
                : existing.getProgrammingScore();
        double updatedMath = (request.getMathematicsScore() != null)
                ? request.getMathematicsScore()
                : existing.getMathematicsScore();

        validateScores(updatedProg, updatedMath);
        existing.setProgrammingScore(updatedProg);
        existing.setMathematicsScore(updatedMath);

        if (request.getSkills() != null) {
            existing.setSkills(sanitizeSkills(request.getSkills()));
        }
        return existing;
    }

    public void deleteStudent(Long id) {
        if (!studentStore.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with ID: " + id);
        }
        studentStore.remove(id);
    }

    public double calculateAverageScore(Student student) {
        return (student.getProgrammingScore() + student.getMathematicsScore()) / 2.0;
    }

    public String assignPerformanceCategory(double average) {
        if (average >= 85.0) {
            return "Excellent";
        } else if (average >= 70.0) {
            return "Good";
        } else if (average >= 50.0) {
            return "Satisfactory";
        } else {
            return "Needs Improvement";
        }
    }

    public String identifyStrongerSubject(Student student) {
        if (student.getProgrammingScore() > student.getMathematicsScore()) {
            return "Programming";
        } else if (student.getMathematicsScore() > student.getProgrammingScore()) {
            return "Mathematics";
        } else {
            return "Equal";
        }
    }

    public List<Student> searchStudents(Long id, String email, String course) {
        List<Student> results = new ArrayList<>();
        for (Student s : studentStore.values()) {
            boolean matches = true;
            if (id != null && !Objects.equals(s.getId(), id)) {
                matches = false;
            }
            if (email != null && !email.isBlank() &&
                    (s.getEmail() == null || !s.getEmail().equalsIgnoreCase(email.trim()))) {
                matches = false;
            }
            if (course != null && !course.isBlank() &&
                    (s.getCourse() == null || !s.getCourse().toLowerCase().contains(course.trim().toLowerCase()))) {
                matches = false;
            }
            if (matches) {
                results.add(s);
            }
        }
        return results;
    }

    public List<MeritEntry> generateMeritList() {
        List<Student> sorted = new ArrayList<>(studentStore.values());
        sorted.sort((a, b) -> Double.compare(calculateAverageScore(b), calculateAverageScore(a)));

        List<MeritEntry> meritList = new ArrayList<>();
        int rank = 1;
        for (Student s : sorted) {
            double avg = calculateAverageScore(s);
            meritList.add(new MeritEntry(
                    rank++,
                    s.getId(),
                    s.getName(),
                    s.getCourse(),
                    s.getProgrammingScore(),
                    s.getMathematicsScore(),
                    avg,
                    assignPerformanceCategory(avg),
                    identifyStrongerSubject(s),
                    new ArrayList<>(s.getSkills())
            ));
        }
        return meritList;
    }

    public Map<String, List<Student>> findStudentsWithSharedSkills() {
        Map<String, List<Student>> skillToStudents = new LinkedHashMap<>();

        for (Student student : studentStore.values()) {
            if (student.getSkills() == null) continue;
            Set<String> uniqueSkillsForStudent = new LinkedHashSet<>();
            for (String skill : student.getSkills()) {
                if (skill != null && !skill.isBlank()) {
                    uniqueSkillsForStudent.add(skill.trim());
                }
            }
            for (String skill : uniqueSkillsForStudent) {
                skillToStudents.computeIfAbsent(skill, k -> new ArrayList<>()).add(student);
            }
        }

        Map<String, List<Student>> sharedSkills = new LinkedHashMap<>();
        for (Map.Entry<String, List<Student>> entry : skillToStudents.entrySet()) {
            if (entry.getValue().size() > 1) {
                sharedSkills.put(entry.getKey(), entry.getValue());
            }
        }
        return sharedSkills;
    }

    public List<Student> findProgrammingImprovementWithStrongMath() {
        List<Student> result = new ArrayList<>();
        for (Student s : studentStore.values()) {
            if (s.getProgrammingScore() < 50.0 && s.getMathematicsScore() >= 75.0) {
                result.add(s);
            }
        }
        return result;
    }

    private void validateScores(double programmingScore, double mathematicsScore) {
        if (programmingScore < 0.0 || programmingScore > 100.0 ||
                mathematicsScore < 0.0 || mathematicsScore > 100.0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Programming and Mathematics scores must be between 0 and 100."
            );
        }
    }

    private List<String> sanitizeSkills(List<String> skills) {
        if (skills == null) return new ArrayList<>();
        Set<String> unique = new LinkedHashSet<>();
        for (String skill : skills) {
            if (skill != null && !skill.isBlank()) {
                unique.add(skill.trim());
            }
        }
        return new ArrayList<>(unique);
    }
}