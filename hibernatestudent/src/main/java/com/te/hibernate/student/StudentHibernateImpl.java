package com.te.hibernate.student;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.te.hibernatedemo.bean.StudentInfo;
public class StudentHibernateImpl {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		StudentInfo studentInfo=new StudentInfo();
		while(true) {
			System.out.println("Do You Want To Display The Data PRESS\n Yes or No");
			String input=scanner.next();
			if(input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("y")) {
				System.out.println("Press 1 to see all data\n Press 2 to see any particular data\n Press 3 to update any particular data\n press 4 to delete data\n press 5 to exit");
				String choice=scanner.next();
				EntityManagerFactory factory=Persistence.createEntityManagerFactory("emp");
				EntityManager manager=factory.createEntityManager();
				EntityTransaction transaction=manager.getTransaction();
				switch(choice) {
				case "1":
					String findAll="from StudentInfo ";
					Query query=manager.createQuery(findAll);
					List<StudentInfo> list=query.getResultList();
					for (StudentInfo studentInfo1 : list) {
						System.out.println(studentInfo1);
					}
					break;
				case"2":
					System.out.println("Enter The Id");
					int id = scanner.nextInt();
					studentInfo = manager.find(StudentInfo.class, id);
					if (manager.contains(studentInfo)) {
						Query query1 = manager.createQuery("from StudentInfo where id=:id");
						query1.setParameter("id", id);
						studentInfo = (StudentInfo) query1.getSingleResult();
						System.out.println("-----------Displaying Data By Id -------------");
						System.out.println(studentInfo);
					} else {
						try {
							throw new StudentNotFoundException("Given Student Id Not Present In The Database");
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
					break;
				case"3":
					String newName = null;
					double newMarks =0;
					String choiceOne = null;
					System.out.println("Enter the Id To Update The Employee Details");
					id = scanner.nextInt();
					if (manager.contains(manager.find(StudentInfo.class, id))) {
						System.out.println("Do You Want to update Student Name ( yes or no)");
						choiceOne = scanner.next();
						if (choiceOne.equalsIgnoreCase("yes") || choiceOne.equalsIgnoreCase("y")) {
							System.out.println("Enter the New Name To Update For The Student Id: " + id);
							newName = scanner.next();
						}
						System.out.println("Do You Want to Update Student Marks ( yes or no)");
						choiceOne = scanner.next();
						if (choiceOne.equalsIgnoreCase("yes") || choiceOne.equalsIgnoreCase("y")) {
							System.out.println("Enter the New Marks To Update For The Student Id: " + id);
							newMarks = scanner.nextDouble();
						}
						if (newName != null) {
							transaction.begin();
							query = manager.createQuery("update StudentInfo set name=:name where id=:id");
							query.setParameter("name", newName);
							query.setParameter("id", id);
							query.executeUpdate();
							manager.persist(studentInfo);
							transaction.commit();
						}
						if (newMarks != 0) {
							transaction.begin();
							query = manager.createQuery("update StudentInfo set marks=:marks where id=:id");
							query.setParameter("id", id);
							query.setParameter("marks", newMarks);
							query.executeUpdate();
							manager.persist(studentInfo);
							transaction.commit();
						}
						if (newName != null && newMarks != 0) {
							transaction.begin();
							query = manager.createQuery("update StudentInfo set name=:name,marks=:marks where id=:id");
							query.setParameter("name", newName);
							query.setParameter("marks", newMarks);
							query.setParameter("id", id);
							query.executeUpdate();
							manager.persist(studentInfo);
							transaction.commit();
						}
					} else {
						try {
							throw new StudentNotFoundException("Given Student Id Not Present In The Database");
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
					break;
				case "4":
					System.out.println("Enter the Id To Display The Student Details");
					id = scanner.nextInt();
					studentInfo = manager.find(StudentInfo.class, id);
					if (manager.contains(studentInfo)) {
						transaction.begin();
						query = manager.createQuery("delete from StudentInfo where id=:id");
						query.setParameter("id", id);
						query.executeUpdate();
						transaction.commit();
						System.out.println("The Id " + id + " Student Detail Deleted Sucessfully");
					} else {
						try {
							throw new StudentNotFoundException("Given Student Id Not Present In The Database");
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
					break;
				case "5":
					System.exit(0);
					break;
				default:
					try {
						throw new StudentNotFoundException("Please Check The Input Value..! & Select The Given Options\n\n");
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			} else {
				if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
					System.exit(0);
				}
				try {
					throw new StudentNotFoundException("Please Check the Input..!!");
				} catch (
						Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
}
