package com.example.student_merit.controller;

import com.example.student_merit.model.MeritEntry;
import com.example.student_merit.model.PerformanceUpdateRequest;
import com.example.student_merit.model.Student;
import com.example.student_merit.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student created = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}/performance")
    public ResponseEntity<Student> updatePerformance(@PathVariable Long id,
                                                     @RequestBody PerformanceUpdateRequest request) {
        return ResponseEntity.ok(studentService.updatePerformanceAndSkills(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/merit-list")
    public ResponseEntity<List<MeritEntry>> getMeritList() {
        return ResponseEntity.ok(studentService.generateMeritList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String course) {
        return ResponseEntity.ok(studentService.searchStudents(id, email, course));
    }

    @GetMapping("/shared-skills")
    public ResponseEntity>> getSharedSkills() {
        return ResponseEntity.ok(studentService.findStudentsWithSharedSkills());
    }

    @GetMapping("/programming-improvement-strong-math")
    public ResponseEntity<List<Student>> getProgrammingImprovementWithStrongMath() {
        return ResponseEntity.ok(studentService.findProgrammingImprovementWithStrongMath());
    }
}