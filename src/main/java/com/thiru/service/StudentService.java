package com.thiru.service;

import java.util.List;
import java.util.Optional;

import com.thiru.entities.Student;

import jakarta.validation.Valid;

public interface StudentService {

	// CREATE
    Student createStudent(@Valid Student student);

  //  List<Student> createStudents(@Valid Student students);
	List<Student> createStudents(@Valid List<Student> students);

    
	// READ

	// fetch one student
	Student getStudentById(int id);

	// fetch all students
	List<Student> getAllStudents();

	// UPDATE
	Student updateStudentById(int id , Student student);
	
	Student updateStudentByPartially(int id,Student student);

	// DELETE
	String deleteStudentById(int id);
	

	String determineBranchAndName(String regNumber);


	
}