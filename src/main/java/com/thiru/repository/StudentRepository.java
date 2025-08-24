package com.thiru.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thiru.entities.Student;

import jakarta.transaction.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	Optional<Student> findByRegNumber(String regNumber);

	// JPQL query using correct entity field names
	@Query("SELECT s FROM Student s WHERE s.name = :name AND s.branch = :branch")
	Student findByNameAndBranch(@Param("name") String name, @Param("branch") String branch);

	// JPQL query to get students by branch
	@Query("SELECT s FROM Student s WHERE s.branch = :branch")
	List<Student> findByBranch(@Param("branch") String branch);

	// Native query to get students by branch
	@Query(value = "SELECT * FROM student WHERE branch = :branch", nativeQuery = true)
	List<Student> findByBranchNative(@Param("branch") String branch);

	// Update branch by student name
	@Modifying
	@Transactional
	@Query("UPDATE Student s SET s.branch = :newBranch WHERE s.name = :name")
	int updateBranchByName(@Param("name") String name, @Param("newBranch") String newBranch);

	// Delete student by name
	@Modifying
	@Transactional
	@Query("DELETE FROM Student s WHERE s.name = :name")
	int deleteByName(@Param("name") String name);
}
