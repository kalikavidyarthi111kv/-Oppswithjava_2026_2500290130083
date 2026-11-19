package com.example.student_merit.repository;

import com.example.student_merit.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByEmailIgnoreCase(String email);

    List<Student> findByCourseContainingIgnoreCase(String course);
}
