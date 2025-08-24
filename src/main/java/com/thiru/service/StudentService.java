package com.thiru.service;

import java.util.List;
import java.util.Optional;

import com.thiru.entities.Student;

public interface StudentService {

	// CREATE
	Student createStudent(Student student);

	List<Student> createStudents(Student student);
	// READ

	// fetch one student
	Optional<Student> getStudentById(int id);

	// fetch all students
	List<Student> getAllStudents();

	// UPDATE
	Student updateStudentById(int id , Student student);
	
	Student updateStudentByPartially(int id,Student student);

	// DELETE
	String deleteStudentById(int id);
	

	String determineBranchAndName(String regNumber);

	
}
