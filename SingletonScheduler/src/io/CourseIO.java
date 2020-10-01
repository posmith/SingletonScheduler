package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import course.Course;
import manager.CourseMgr;
import student.Student;
import util.ArrayList;

public class CourseIO {

	public static Boolean useListMode = true; // Toggle true for reading and writing courses as a csv list, false for
												// reading and writing courses as a csv schedule

	public static void loadCourseEnrollments(CourseMgr manager, String file) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			Scanner fileReader = new Scanner(new FileInputStream(file));
			while (fileReader.hasNextLine()) {
				lines.add(fileReader.nextLine());
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		// System.out.println("Lines read: " + lines.size()); // Uncomment to print
		// number of lines in input for debugging

		for (int i = 1; i < lines.size(); i++) {
			try {
				Scanner s = new Scanner(lines.get(i));
				s.useDelimiter(",");
				String id = s.next();
				String last = s.next();
				String first = s.next();
				String code = s.next();
				String courseName = s.next();
				Student student = new Student(last, first, id);
				Course course = new Course(courseName, code);
				if (manager.getStudents().getStudentById(id) == null) {
					manager.getStudents().addStudentToList(student);
				}
				if (manager.getCourses().getCourseByName(courseName) == null) {
					manager.getCourses().addCourseToList(course);
				}
				manager.getStudents().getStudentById(id).addCourse(manager.getCourses().getCourseByName(courseName));
				manager.getCourses().getCourseByName(courseName).addStudent(manager.getStudents().getStudentById(id));
				s.close();
			} catch (InputMismatchException err) {
				System.out.println(err.getMessage());
				System.exit(0);
			}
		}

	}

	public static void loadPeriods(CourseMgr manager, String file) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			Scanner fileReader = new Scanner(new FileInputStream(file));
			while (fileReader.hasNextLine()) {
				lines.add(fileReader.nextLine());
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}

		if (useListMode) {
			for (int i = 1; i < lines.size(); i++) {
				Scanner s = new Scanner(lines.get(i));
				s.useDelimiter(",");
				try {
					String title = s.next();
					int period = s.nextInt();
					if (manager.getCourses().getCourseByName(title) != null) {
						manager.getCourses().getCourseByName(title).setPeriod(period);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.exit(0);
				}
				s.close();
			}
		}

		if (!useListMode) {
			for (int i = 1; i < lines.size(); i++) {
				Scanner s = new Scanner(lines.get(i));
				s.useDelimiter(",");
				for (int j = 0; j <= 7; j++) {
					if (s.hasNext()) {
						String title = s.next();
						int period = j;
						if (manager.getCourses().getCourseByName(title) != null) {
							try {
								manager.getCourses().getCourseByName(title).setPeriod(period);
							} catch (Exception e) {
								System.out.println(e.getMessage());
								System.exit(0);
							}
						}
					}
				}
				s.close();
			}
		}
		System.out.println("Courses read: " + manager.getCourses().size());
	}

	public static void writeEnrollmentsToFile(CourseMgr manager, String file) {
		if (!file.substring(file.length() - 4).equals(".txt") && !file.substring(file.length() - 4).equals(".csv")) {
			throw new IllegalArgumentException("Save file must be .txt or .csv");
		}
		PrintStream filewriter;
		try {
			filewriter = new PrintStream(new File(file));
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		filewriter.println("Student ID,Last Name,First Name,Course Code,Course Title");
		for (int i = 0; i < manager.getCourses().size(); i++) {
			Course c = manager.getCourses().getCourses().get(i);
			for (int j = 0; j < c.getStudents().size(); j++) {
				Student s = c.getStudents().get(j);
				filewriter.println(s.getId() + "," + s.getLastName() + "," + s.getFirstName() + "," + c.getCourseCode()
						+ "," + c.getTitle());
			}
		}
	}

	public static void writePeriodsToFile(CourseMgr manager, String file) {
		if (!file.substring(file.length() - 4).equals(".txt") && !file.substring(file.length() - 4).equals(".csv")) {
			throw new IllegalArgumentException("Save file must be .txt or .csv");
		}
		PrintStream filewriter;
		try {
			filewriter = new PrintStream(new File(file));
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		filewriter.println("Course, Period");
		for (int i = 0; i < manager.getCourses().size(); i++) {
			Course c = manager.getCourses().getCourses().get(i);
			filewriter.println(c.getTitle() + "," + c.getPeriod());
		}
	}

}
