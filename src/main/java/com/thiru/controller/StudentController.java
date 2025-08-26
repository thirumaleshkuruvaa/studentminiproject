package com.thiru.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiru.entities.Student;
import com.thiru.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentservice;

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @PostMapping("/createstudent")
    public String createStudent(@RequestBody @Valid Student student) {
        log.info("POST /api/createstudent called with payload: {}", student);
        studentservice.createStudent(student);
        return "Student created successfully";
    }

    @PostMapping("/createstudents")
    public ResponseEntity<String> createStudents(@Valid @RequestBody List<Student> students) {
        log.info("POST /api/createstudents called with payload: {}", students);

        studentservice.createStudents(students);

        return new ResponseEntity<>("Students are created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/fetchstudent/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Student student = studentservice.getStudentById(id);
        return ResponseEntity.ok(student);
    }


    @GetMapping("/fetchstudents")
    public List<Student> getAllStudents() {
        return studentservice.getAllStudents();
    }

    @GetMapping("/fetchBranchAndName/{regNumber}")
    public String determineBranchAndName(@PathVariable String regNumber) {
        return studentservice.determineBranchAndName(regNumber);
    }

    @PutMapping("/student/{id}")
    public String updateStudentById(@PathVariable int id, @RequestBody Student student) {
        studentservice.updateStudentById(id, student);
        return "Student with ID " + id + " has been updated successfully.";
    }

    @PatchMapping("/student/{id}")
    public String updateStudentByPartially(@PathVariable int id, @RequestBody Student student) {
        studentservice.updateStudentByPartially(id, student);
        return "Student with ID " + id + " has been partially updated successfully.";
    }

    @DeleteMapping("/student/{id}")
    public String deleteStudentById(@PathVariable int id) {
        studentservice.deleteStudentById(id);
        return "Student with ID " + id + " has been deleted successfully.";
    }
}

	/*
	 * package com.thiru.controller;
	 * 
	 * import java.util.List; import java.util.Optional;
	 * 
	 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
	 * org.springframework.beans.factory.annotation.Autowired; import
	 * org.springframework.http.HttpStatus; import import
	 * org.springframework.http.ResponseEntity; import
	 * org.springframework.web.bind.annotation.*;
	 * 
	 * import com.thiru.dto.ApiResponse; import com.thiru.entities.Student; import
	 * com.thiru.service.StudentService;
	 * 
	 * @RestController
	 * 
	 * @RequestMapping("/api") public class StudentController {
	 * 
	 * @Autowired private StudentService studentservice;
	 * 
	 * private static final Logger log =
	 * LoggerFactory.getLogger(StudentController.class);
	 */
	/*
	 * // CREATE
	 * 
	 * @PostMapping("/createstudents" public
	 * ResponseEntity<ApiResponse>createStudent(@RequestBody Student student) {
	 * 
	 * log.info("POST /api/createstudents called with payload: {}", student);
	 * 
	 * Student createdStudent = studentservice.createStudent(student);
	 * 
	 * log.info("Student created successfully: {}", createdStudent); ApiResponse
	 * response = new ApiResponse("Student created successfully", createdStudent);
	 * return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 }
	 * 
	 * // GET by ID
	 * 
	 * @GetMapping("/student/{id}") public
	 * ResponseEntity<Student>getStudentById(@PathVariable int id) {
	 * log.info("GET /api/student/{} called",id);
	 * 
	 * Optional<Student> student = studentservice.getStudentById(id);
	 * 
	 * if (student.isPresent()) { log.info("Fetched student: {}", student.get());
	 * return new ResponseEntity<>(student.get(), HttpStatus.OK); // 200 } else {
	 * log.warn("Student with ID {} not found", id); return new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 } }
	 * 
	 * 
	 * // GET all students
	 * 
	 * @GetMapping("/fetchstudents") public
	 * ResponseEntity<List<Student>>getAllStudents(){
	 * log.info("GET /api/fetchstudents called");
	 * 
	 * List<Student> students = studentservice.getAllStudents();
	 * 
	 * if (students.isEmpty()) { log.info("No students found"); return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 } else {
	 * log.info("Fetched {} students", students.size()); * return new
	 * ResponseEntity<>(students, HttpStatus.OK); // 200 } }
	 * 
	 * 
	 * // GET branch and name by reg number
	 * 
	 * @GetMapping("/fetchBranchAndName/{regNumber}") public ResponseEntity<String
	 * determineBranchAndName(@PathVariable String regNumber) {
	 * 
	 * log.info("GET /api/fetchBranchAndName/{} called", regNumber);
	 * 
	 * String result = studentservice.determineBranchAndName(regNumber);
	 * 
	 * if (result != null && !result.isEmpty()) { return new
	 * ResponseEntity<>(result, HttpStatus.OK); // 200 } else { return new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 } }
	 * 
	 * // UPDATE full
	 * 
	 * @PutMapping("/student/{id}") public
	 * ResponseEntity<ApiResponse>updateStudentById(@PathVariable int
	 * id, @RequestBody Student student) {
	 * 
	 * log.info("PUT /api/student/{} called with payload: {}", id, student);
	 * 
	 * Student updatedStudent = studentservice.updateStudentById(id, student);
	 * 
	 * log.info("Student with ID {} updated successfully", id); ApiResponse response
	 * = new ApiResponse("Student updated successfully", updatedStudent); return new
	 * ResponseEntity<>(response, HttpStatus.OK); // 200 }
	 * 
	 * // UPDATE partial
	 * 
	 * @PatchMapping("/student/{id}") public
	 * ResponseEntity<ApiResponse>updateStudentPartially(@PathVariable int
	 * id, @RequestBody Student student) {
	 * 
	 * log.info("PATCH /api/student/{} called with payload: {}", id, student);
	 * 
	 * Student updatedStudent = studentservice.updateStudentById(id, student);
	 * 
	 * log.info("Student with ID {} partially updated successfully", id);
	 * ApiResponse response = new
	 * ApiResponse("Student partially updated successfully", updatedStudent);
	 * 
	 * return ApiResponse("Student partially updated successfully", updatedStudent);
	 * return new ResponseEntity<>(response, HttpStatus.OK); // 200 }
	 * 
	 * // DELETE
	 * 
	 * @DeleteMapping("/student/{id}") public
	 * ResponseEntity<String>deleteStudentById(@PathVariable int id) {
	 * 
	 * log.info("DELETE /api/student/{} called", id);
	 * 
	 * studentservice.deleteStudentById(id);
	 * 
	 * log.info("Student with ID {} deleted successfully", id); return new
	 * ResponseEntity<>("Student deleted successfully", HttpStatus.NO_CONTENT); //
	 * 204 } }
	 */

