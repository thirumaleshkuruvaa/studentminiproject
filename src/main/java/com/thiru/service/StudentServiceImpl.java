package com.thiru.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiru.ExceptionalHandling.StudentAlreadyExistsException;
import com.thiru.ExceptionalHandling.StudentNotFoundException;
import com.thiru.entities.Student;
import com.thiru.repository.StudentRepository;

import jakarta.validation.Valid;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentrepository;

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    public StudentServiceImpl(StudentRepository studentrepository) {
        this.studentrepository = studentrepository;
    }

    public StudentServiceImpl() {}

    @Override
    public Student createStudent(Student student) {
        log.info("Creating student: {}", student);

        // ✅ check if student already exists by regNumber
        studentrepository.findByRegNumber(student.getRegNumber())
                .ifPresent(s -> {
                    log.error("Student already exists with regNumber: {}", student.getRegNumber());
                    throw new StudentAlreadyExistsException("Student already exists with regNumber: " + student.getRegNumber());
                });

        Student savedStudent = studentrepository.save(student);
        log.info("Student created successfully: {}", savedStudent);
        return savedStudent;
    }

    @Override
    public List<Student> createStudents(@Valid List<Student> students) {
        log.info("Creating multiple students");

        for (Student student : students) {
            studentrepository.findByRegNumber(student.getRegNumber())
                    .ifPresent(s -> {
                        log.error("Student already exists with regNumber: {}", student.getRegNumber());
                        throw new StudentAlreadyExistsException("Student already exists with regNumber: " + student.getRegNumber());
                    });
        }

        List<Student> savedStudents = studentrepository.saveAll(students);
        log.info("Students created successfully: {}", savedStudents);
        return savedStudents;
    }

    @Override
    public Student getStudentById(int id) {
        log.info("Fetching student by ID: {}", id);

        return studentrepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student with ID {} not found", id);
                    return new StudentNotFoundException("Student with ID " + id + " not found");
                });
    }


    @Override
    public List<Student> getAllStudents() {
        log.info("Fetching all students");
        List<Student> students = studentrepository.findAll();
        log.info("Total students fetched: {}", students.size());
        return students;
    }

    @Override
    public Student updateStudentById(int id, Student student) {
        log.info("Updating student with ID {}: {}", id, student);
        Student existingStudent = studentrepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        existingStudent.setName(student.getName());
        existingStudent.setBranch(student.getBranch());
        Student updatedStudent = studentrepository.save(existingStudent);
        log.info("Student updated successfully: {}", updatedStudent);
        return updatedStudent;
    }

    @Override
    public Student updateStudentByPartially(int id, Student student) {
        log.info("Partially updating student with ID {}: {}", id, student);
        Student existingStudent = studentrepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        if (student.getName() != null && !student.getName().isEmpty()) {
            existingStudent.setName(student.getName());
        }
        if (student.getBranch() != null && !student.getBranch().isEmpty()) {
            existingStudent.setBranch(student.getBranch());
        }

        Student updatedStudent = studentrepository.save(existingStudent);
        log.info("Student partially updated successfully: {}", updatedStudent);
        return updatedStudent;
    }

    @Override
    public String deleteStudentById(int id) {
        log.info("Deleting student with ID: {}", id);
        if (!studentrepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentrepository.deleteById(id);
        log.info("Student deleted successfully with ID: {}", id);
        return "Student deleted Successfully";
    }

    @Override
    public String determineBranchAndName(String regNumber) {
        log.info("Determining branch and name for regNumber: {}", regNumber);
        if (regNumber == null || regNumber.trim().isEmpty()) {
            return "UNKNOWN, UNKNOWN";
        }

        regNumber = regNumber.toUpperCase();

        Optional<Student> studentOpt = studentrepository.findByRegNumber(regNumber);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            return student.getName() + ", " + student.getBranch();
        }

        if (regNumber.matches("^23G31A05\\d{2}$")) {
            return "UNKNOWN, CSE";
        }
        if (regNumber.matches("^23G31A04\\d{2}$")) {
            return "UNKNOWN, ECE";
        }

        return "UNKNOWN, UNKNOWN";
    }

}
