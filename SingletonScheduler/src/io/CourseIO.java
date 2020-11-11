package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import course.Course;
import manager.CourseMgr;
import student.Student;
import util.ArrayList;

/**
 * Provides static methods for loading data into a CourseMgr and saving data to
 * file.
 * 
 * @author Patrick
 *
 */
public class CourseIO {

	/**
	 * Loads course enrollments from comma-delimited file. Each line should be an
	 * individual course enrollment and formatted as:
	 * [student_id],[student_last_name],[student_first_name],[course_code],[course_name]
	 * 
	 * @param manager
	 * @param file
	 */
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

		for (int i = 0; i < lines.size(); i++) {
			try {
				Scanner s = new Scanner(lines.get(i));
				s.useDelimiter(",");
				String id = s.next();
				String last = s.next();
				String first = s.next();
				String code = s.hasNext() ? s.next() : null;
				String courseName = s.hasNext() ? s.next() : null;
				Student student = new Student(last, first, id);
				if (manager.getStudents().getStudentById(id) == null) {
					manager.getStudents().addStudentToList(student);
				}
				if (code != null && courseName != null) {
					Course course = new Course(courseName, code);
					if (manager.getCourses().getCourseByName(courseName) == null) {
						manager.getCourses().addCourseToList(course);
					}
					manager.getStudents().getStudentById(id)
							.addCourse(manager.getCourses().getCourseByName(courseName));
					manager.getCourses().getCourseByName(courseName)
							.addStudent(manager.getStudents().getStudentById(id));
				}
				s.close();
			} catch (InputMismatchException err) {
				System.out.println("Input incorrectly formatted.");
				System.exit(1);
			} catch (NoSuchElementException err) {
				System.out.println("Null or invalid field.");
				System.exit(1);
			}
		}

	}

	/**
	 * Loads courses and periods from a comma-delimited file created by saving data
	 * from a previous session. Each entry should be on a separate line and
	 * formatted as: [course_title],[course_period]
	 * 
	 * @param manager
	 * @param file
	 * @param useListMode
	 */
	public static void loadPeriods(CourseMgr manager, String file, boolean useListMode) {

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
			for (int i = 0; i < lines.size(); i++) {
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
			for (int i = 0; i < lines.size(); i++) {
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

	/**
	 * Saves changes to enrollment file for use in future session. Creates file if
	 * one does not already exist.
	 * 
	 * @param manager
	 * @param file
	 */
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
		for (int i = 0; i < manager.getStudents().getStudents().size(); i++) {
			Student s = manager.getStudents().getStudents().get(i);
			int numClasses = s.getCourses().size();
			if (numClasses == 0) {
				filewriter.println(s.getId() + "," + s.getLastName() + "," + s.getFirstName() + "," + "," + ",");
			} else {
				for (int j = 0; j < numClasses; j++) {
					Course c = s.getCourses().get(j);
					filewriter.println(s.getId() + "," + s.getLastName() + "," + s.getFirstName() + ","
							+ c.getCourseCode() + "," + c.getTitle());
				}
			}
		}
	}

	/**
	 * Saves changes to course periods file for use in future session. Creates file if
	 * one does not already exist.
	 * 
	 * @param manager
	 * @param file
	 */
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
		for (int i = 0; i < manager.getCourses().size(); i++) {
			Course c = manager.getCourses().getCourses().get(i);
			filewriter.println(c.getTitle() + "," + c.getPeriod());
		}
	}

}
