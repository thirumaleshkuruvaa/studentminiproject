package com.thiru.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiru.ExceptionalHandling.StudentNotFoundException;
import com.thiru.entities.Student;
import com.thiru.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentrepository;

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    public StudentServiceImpl(StudentRepository studentrepository) {
        super();
        this.studentrepository = studentrepository;
    }

    public StudentServiceImpl() {
        super();
    }

    @Override
    public Student createStudent(Student student) {
        log.info("Creating student: {}", student);
        Student savedStudent = studentrepository.save(student);
        log.info("Student created successfully: {}", savedStudent);
        return savedStudent;
    }

    @Override
    public List<Student> createStudents(Student student) {
        log.info("Creating student: {}", student);
        Student savedStudent = studentrepository.save(student);
        log.info("Student created successfully: {}", savedStudent);
        return List.of(savedStudent); //  list with one element
    }


    @Override
    public Optional<Student> getStudentById(int id) {
        log.info("Fetching student by ID: {}", id);
        Optional<Student> studentOpt = studentrepository.findById(id);
        if(studentOpt.isPresent()) {
            log.info("Student found: {}", studentOpt.get());
        } else {
            log.warn("Student with ID {} not found", id);
        }
        return studentOpt;
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
                .orElseThrow(() -> {
                    log.error("Student not found with ID {}", id);
                    return new StudentNotFoundException("Student not found with id: " + id);
                });

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
                .orElseThrow(() -> {
                    log.error("Student not found with ID {}", id);
                    return new StudentNotFoundException("Student not found with id: " + id);
                });

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
        try {
            studentrepository.deleteById(id);
            log.info("Student deleted successfully with ID: {}", id);
            return "Student deleted Successfully";
        } catch (Exception e) {
            log.error("Error deleting student with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public String determineBranchAndName(String regNumber) {
        log.info("Determining branch and name for regNumber: {}", regNumber);
        if (regNumber == null || regNumber.trim().isEmpty()) {
            log.warn("regNumber is empty");
            return "UNKNOWN, UNKNOWN";
        }

        regNumber = regNumber.toUpperCase();

        Optional<Student> studentOpt = studentrepository.findByRegNumber(regNumber);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            log.info("Student found in DB: {}", student);
            return student.getName() + ", " + student.getBranch();
        }

        if (regNumber.matches("^23G31A05\\d{2}$")) {
            log.info("Branch inferred as CSE");
            return "UNKNOWN, CSE";
        }
        if (regNumber.matches("^23G31A04\\d{2}$")) {
            log.info("Branch inferred as ECE");
            return "UNKNOWN, ECE";
        }

        log.warn("Branch and name could not be determined for regNumber: {}", regNumber);
        return "UNKNOWN, UNKNOWN";
    }
}
