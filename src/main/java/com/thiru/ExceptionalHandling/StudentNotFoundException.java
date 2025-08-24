package com.thiru.ExceptionalHandling;

public class StudentNotFoundException  extends  RuntimeException {
	
	public StudentNotFoundException(String msg) {
		
		super(msg);
	}
}
