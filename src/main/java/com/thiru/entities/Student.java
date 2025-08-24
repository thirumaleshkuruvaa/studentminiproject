package com.thiru.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "RegNumber is mandatory")
	@Size(min = 10, max = 10, message = "RegNumber must be exactly 10 characters")
	@Pattern(regexp = "23G31A[0-9]{4}", message = "RegNumber must start with 23G31A followed by 4 digits")
	private String regNumber;

	@NotBlank(message = "Name cannot be blank")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;

	@NotBlank(message = "Branch cannot be blank")
	@Pattern(regexp = "CSE|ECE|EEE|MECH", message = "Branch must be one of CSE, ECE, EEE, MECH")
	private String branch;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDateTime;
	@UpdateTimestamp
	private LocalDateTime updatedDateTime;

	// Required by JPA
	public Student() {
	}

	// Optional: convenience constructor
	public Student(int id, String regNumber, String name, String branch) {
		this.id = id;
		this.regNumber = regNumber;
		this.name = name;
		this.branch = branch;
	}

	// getters & setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegNumber() {
		return regNumber;

	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
}
