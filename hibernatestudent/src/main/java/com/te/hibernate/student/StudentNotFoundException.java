package com.te.hibernate.student;
public class StudentNotFoundException extends RuntimeException {
	String msg;

	public StudentNotFoundException(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return this.msg;
	}

}

