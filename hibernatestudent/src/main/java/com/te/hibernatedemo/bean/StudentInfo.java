package com.te.hibernatedemo.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class StudentInfo {
	@Id
private int id;
private String name;
private double marks;

}