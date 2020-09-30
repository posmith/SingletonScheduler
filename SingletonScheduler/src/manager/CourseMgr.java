package manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

import course.Conflict;
import course.Course;
import course.CourseList;
import student.Student;
import student.StudentList;
import util.ArrayList;
import util.SortedLinkedList;

public class CourseMgr {

	private static CourseList courses;
	private static StudentList students;
	private static SortedLinkedList<Conflict> conflicts;
	private static CourseMgr instance;

	private CourseMgr() {
		courses = new CourseList();
		students = new StudentList();
	}

	public static CourseMgr getInstance() {
		if (instance == null) {
			instance = new CourseMgr();
		}
		return instance;
	}

	public CourseList getCourses() {
		return courses;
	}

	public StudentList getStudents() {
		return students;
	}

	public void readCourses(String file) {
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
		// System.out.println("Lines read: " + lines.size());

		for (int i = 1; i < lines.size(); i++) {
			try {
				Scanner s = new Scanner(lines.get(i));
				s.useDelimiter(",");
				String id = s.next();
				String last = s.next();
				String first = s.next();
				s.next();
				String courseName = s.next();
				Student student = new Student(last, first, id);
				Course course = new Course(courseName);
				if (students.getStudentById(id) == null) {
					students.addStudentToList(student);
				}
				if (courses.getCourseByName(courseName) == null) {
					courses.addCourseToList(course);
				}
				students.getStudentById(id).addCourse(courses.getCourseByName(courseName));
				courses.getCourseByName(courseName).addStudent(students.getStudentById(id));
				s.close();
			} catch (InputMismatchException err) {
				System.out.println(err.getMessage());
				System.exit(0);
				/**
				 * } catch (Exception e) { throw new IllegalArgumentException("Error reading
				 * file.");
				 */
			}
		}

	}

	public void loadPeriods(String file) {
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

		for (int i = 1; i < lines.size(); i++) {
			Scanner s = new Scanner(lines.get(i));
			s.useDelimiter(",");
			for (int j = 0; j <= 7; j++) {
				if (s.hasNext()) {
					String title = s.next();
					int period = j;
					if (courses.getCourseByName(title) != null) {
						try {
							courses.getCourseByName(title).setPeriod(period);
						} catch (Exception e) {
							System.out.println(e.getMessage());
							System.exit(0);
						}
					}
				}
			}
			s.close();
		}
		System.out.println("Courses read: " + courses.size());
	}

	public static void saveCourses(String file) {
		// TODO: Insert Method
	}

	public static void runConflicts() {

		// Traverse list of students

		// Traverse each student's list of courses

		// Compare period of each course with each successive course

		conflicts = new SortedLinkedList<Conflict>();
		for (int i = 0; i < students.size(); i++) {
			Student s = students.getStudents().get(i);
			for (int j = 0; j < s.getCourses().size(); j++) {
				Course c = s.getCourses().get(j);
				for (int k = j + 1; k < s.getCourses().size(); k++) {
					Course c2 = s.getCourses().get(k);
					if (c.getPeriod() == c2.getPeriod()) {
						CourseList list = new CourseList();
						list.addCourseToList(c);
						list.addCourseToList(c2);
						conflicts.add(new Conflict(s.getName(), list));
					}
				}
			}
		}
		if (conflicts.size() == 0) {
			System.out.println("\nNo conflicts.");

		} else {
			/**
			 * System.out.println(); for (int i = 0; i < conflicts.size(); i++) {
			 * System.out.println(conflicts.get(i).toString()); } System.out.println();
			 */
			for (int i = 0; i < conflicts.size(); i++) {
				System.out.println(conflicts.get(i).getStudentName() + ","
						+ conflicts.get(i).getConflictingCourses().get(0).getTitle() + ","
						+ conflicts.get(i).getConflictingCourses().get(1).getTitle() + ","
						+ conflicts.get(i).getConflictingCourses().get(0).getPeriod());
			}
			System.out.println("Total number of conflicts: " + conflicts.size());
		}
	}

	public static void getCourseConflicts() {
		conflicts = new SortedLinkedList<Conflict>();
		TreeSet<Course> coursesInConflict = new TreeSet<Course>();
		for (int i = 0; i < students.size(); i++) {
			Student s = students.getStudents().get(i);
			for (int j = 0; j < s.getCourses().size(); j++) {
				Course c = s.getCourses().get(j);
				for (int k = j + 1; k < s.getCourses().size(); k++) {
					Course c2 = s.getCourses().get(k);
					if (c.getPeriod() == c2.getPeriod()) {
						CourseList list = new CourseList();
						list.addCourseToList(c);
						list.addCourseToList(c2);
						conflicts.add(new Conflict(s.getName(), list));
						coursesInConflict.add(c);
						coursesInConflict.add(c2);
					}
				}
			}
		}
		if (conflicts.size() == 0) {
			System.out.println("\nNo conflicts.");

		} else {
			for (int i = 1; i <= 7; i++) {
				System.out.println("COURSE CONFLICTS FOR PERIOD " + i);
				Iterator<Course> it = coursesInConflict.iterator();
				while (it.hasNext()) {
					Course c = it.next();
					if (c.getPeriod() == i) {
						int count = 0;
						for (int k = 0; k < conflicts.size(); k++) {
							if (conflicts.get(k).getConflictingCourses().get(0).equals(c)
									|| conflicts.get(k).getConflictingCourses().get(1).equals(c)) {
								count++;
							}
						}
						if (count > 0) {
							StringBuilder s = new StringBuilder();
							s.append("\t").append(c.getTitle()).append(": ");
							while (s.length() < 40) {
								s.append(" ");
							}
							s.append(count).append(" conflicts");
							System.out.println(s);
						}
					}
				}
			}
			System.out.println("\nTotal number of conflicts: " + conflicts.size());
		}
	}

	/**
	 * public void createCourses() {
	 * getInstance().getStudents().addStudentToList(new Student("Smith", "Patrick",
	 * 1)); getInstance().getStudents().addStudentToList(new Student("Jones", "Don",
	 * 2)); getInstance().getStudents().addStudentToList(new Student("Proctor",
	 * "Belinda", 3)); getInstance().getStudents().addStudentToList(new
	 * Student("Bullock", "Marla", 4));
	 * getInstance().getStudents().addStudentToList(new Student("Do", "Harrison",
	 * 5)); getInstance().getStudents().addStudentToList(new Student("Thomas",
	 * "Clifton", 6));
	 * 
	 * getInstance().getCourses().addCourseToList("Test Course 1", 1);
	 * getInstance().getCourses().addCourseToList("Test Course 2", 2);
	 * 
	 * try { getInstance().getCourses().getCourseByName("Test Course 1")
	 * .addStudent(getInstance().getStudents().getStudentById(1));
	 * getInstance().getCourses().getCourseByName("Test Course 1")
	 * .addStudent(getInstance().getStudents().getStudentById(2));
	 * getInstance().getCourses().getCourseByName("Test Course 1")
	 * .addStudent(getInstance().getStudents().getStudentById(3));
	 * getInstance().getCourses().getCourseByName("Test Course 2")
	 * .addStudent(getInstance().getStudents().getStudentById(4));
	 * getInstance().getCourses().getCourseByName("Test Course 2")
	 * .addStudent(getInstance().getStudents().getStudentById(5));
	 * getInstance().getCourses().getCourseByName("Test Course 2")
	 * .addStudent(getInstance().getStudents().getStudentById(6)); } catch
	 * (NullPointerException e) { System.out.println("Error in createCourses()"); }
	 * }
	 */

}
