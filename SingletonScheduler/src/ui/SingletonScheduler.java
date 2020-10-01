package ui;

import java.util.*;

import course.Course;
import manager.CourseMgr;
import student.Student;

public class SingletonScheduler {
	private static CourseMgr manager;
	private static final String DEFAULT_ENR = "input/test2.csv";
	private static final String DEFAULT_PER = "input/testperiods2.csv"; // Change to load/save from/to a different file
	private static final String WORKING_ENR = "input/savedEnrollments.csv";
	private static final String WORKING_PER = "input/savedPeriods.csv";

	public static void main(String[] args) {
		manager = CourseMgr.getInstance();
		// manager.createTestCourses(); // Uncomment for debugging using test input in
		// CourseMgr class
		System.out.println("UI, ver. 1.3");
		Scanner console = new Scanner(System.in);

		String selection = "";

		System.out.println("Use default input (D)");
		System.out.println("Select files to load from (S)");
		System.out.println("Enter courses manually (E)");
		System.out.println("\nSelect Option: ");
		selection = console.nextLine();
		if (selection.contentEquals("D")) {
			manager.loadCourseEnrollments(DEFAULT_ENR);
			manager.loadPeriods(DEFAULT_PER);
		} else if (selection.equals("S")) {
			System.out.println("Enter filename for course enrollments: ");
			selection = console.nextLine();
			try {
				manager.loadCourseEnrollments(selection);
				System.out.println("\nCourses loaded.");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
			System.out.println("Enter filename for course periods: ");
			selection = console.nextLine();
			try {
				manager.loadPeriods(selection);
				System.out.println("\nPeriods set.");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}

		mainMenu(console);
	}

	private static void mainMenu(Scanner console) {

		String selection = "";

		while (!selection.equals("Q")) {

			System.out.println();
			System.out.println("Course List (C)");
			System.out.println("Student List (S)");
			System.out.println("Print Course Conflicts (P)");
			System.out.println("Print Conflict Details (D)");
			System.out.println("Write Changes to File (W)");
			System.out.println("Quit Program (Q)");
			System.out.print("\nSelect Option: ");
			selection = console.nextLine();

			if (selection.equals("C")) {
				viewCourses(console);
			} else if (selection.equals("S")) {
				viewStudents(console);
			} else if (selection.equals("D")) {
				CourseMgr.runConflicts();
			} else if (selection.equals("P")) {
				CourseMgr.getCourseConflicts();
			} else if (selection.equals("W")) {
				save(console);
			} else if (selection.equals("Q")) {
				if (manager.isChangesMade()) {
					promptSave(console);
				}
				System.out.println("Program terminated");
				console.close();
				System.exit(0);
			} else {
				System.out.println("\nInvalid Option");
			}
		}
	}

	private static void promptSave(Scanner console) {
		System.out.println();
		String save = "";
		String discard = "";
		while (!discard.toUpperCase().equals("Y") && !discard.toUpperCase().equals("YES")) {
			System.out.println("Save Changes? (Y/N): ");
			save = console.nextLine();
			if (save.toUpperCase().equals("Y") || save.toUpperCase().equals("YES")) {
				save(console);
				discard = "Y";
			} else {
				System.out.println("Discard Changes? (Y/N): ");
				discard = console.nextLine();
			}
		}
	}

	private static void save(Scanner console) {
		System.out.println();
		System.out.println("Save Using Defaults? (Y/N): ");
		String s = console.nextLine();
		if (s.toUpperCase().equals("Y") || s.toUpperCase().equals("YES")) {
			manager.saveChanges(WORKING_ENR, WORKING_PER);
			manager.setChangesMade(false);
		} else {
			System.out.println("Save enrollments as: ");
			String enr = console.nextLine();
			System.out.println("Save periods as: ");
			String per = console.nextLine();
			try {
				manager.saveChanges(enr, per);
				manager.setChangesMade(false);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	private static void viewStudents(Scanner console) {

		String selection = "";

		while (!selection.contentEquals("M")) {
			System.out.println();
			System.out.println(CourseMgr.getInstance().getStudents().toString());

			System.out.println();
			System.out.println("Select a student number or one of the following options:");
			System.out.println("Add Student (A)");
			System.out.println("Delete Student (D)");
			System.out.println("Student List (S)");
			System.out.println("Main Menu (M)");
			System.out.print("\nSelect Option: ");

			selection = console.nextLine();
			Student s;

			if (selection.equals("A")) {
				addStudent(console);
			} else if (selection.equals("D")) {
				deleteStudent(console);
			} else if (selection.equals("M")) {
				mainMenu(console);
			} else if (selection.equals("S")) {
				System.out.print(CourseMgr.getInstance().getCourses().toString());
			} else {
				try {
					s = CourseMgr.getInstance().getStudents().getStudents().get(Integer.parseInt(selection) - 1);
					studentOptions(s, console);
				} catch (Exception e) {
					System.out.println("\nInvalid input.");
				}
			}

		}
	}

	private static void studentOptions(Student s, Scanner console) {
		String selection = "";

		while (!selection.equals("C") && !selection.equals("M")) {
			System.out.println();
			System.out.println("Student Options for " + s.getName() + ":");
			System.out.println("Course Enrollments (C)");
			System.out.println("Return to Student List (S)");
			System.out.println("Return to Main Menu (M)");
			System.out.print("\nSelect Option: ");

			selection = console.nextLine();

			if (selection.equals("S")) {
				viewStudents(console);
			} else if (selection.equals("C")) {
				System.out.print(s.getCoursesAsString() + "\n");
				studentEnrollmentOptions(s, console);
			} else if (selection.equals("M")) {
				mainMenu(console);
			} else {
				System.out.println("\nInvalid input.");
			}
		}
	}

	private static void studentEnrollmentOptions(Student s, Scanner console) {
		String selection = "";

		while (!selection.equals("S") && !selection.equals("M")) {

			System.out.println();
			System.out.println("Course Enrollments for " + s.getName() + ":");
			System.out.println("Add course to enrollments (A)");
			System.out.println("Remove course from enrollments (R)");
			System.out.println("Course Enrollments (C)");
			System.out.println("Return to Student Options (S)");
			System.out.println("Return to Main Menu (M)");
			System.out.print("\nSelect Option: ");
			selection = console.nextLine();

			if (selection.equals("A")) {
				System.out.print("Enter course title: ");
				String title = console.nextLine();
				try {
					if (!s.addCourse(manager.getCourses().getCourseByName(title))) {
						System.out.println("\nNo course exists.");
					} else {
						manager.setChangesMade(true);
					}
				} catch (Exception e) {
					System.out.println("\nInvalid input.");
					studentEnrollmentOptions(s, console);
				}
			} else if (selection.equals("R")) {

				System.out.print("Which course number: ");
				try {
					int course = console.nextInt();
					console.nextLine();
					System.out.print("Remove " + s.getCourses().get(course - 1).toString()
							+ " from the list of enrollments? (Y/N)");
					String confirm = console.nextLine();
					if (confirm.equals("Y")) {
						s.getCourses().remove(course - 1);
						manager.setChangesMade(true);
					} else {
						studentEnrollmentOptions(s, console);
					}
				} catch (Exception e) {
					System.out.println("\nInvalid input.\n");
					studentEnrollmentOptions(s, console);
				}
			} else if (selection.equals("C")) {
				System.out.print(s.getCoursesAsString() + "\n");
			} else if (selection.equals("S")) {
				studentOptions(s, console);
			} else if (selection.equals("M")) {
				mainMenu(console);
			}
		}

	}

	private static void addStudent(Scanner console) {
		String firstName = "";
		String lastName = "";
		String id;
		System.out.print("Enter First Name: ");
		try {
			firstName = console.nextLine();
		} catch (Exception e) {
			System.out.println("\nInvalid input.");
			viewStudents(console);
		}
		System.out.print("Enter LastName: ");
		try {
			lastName = console.nextLine();
		} catch (Exception e) {
			System.out.println("\nInvalid input.");
			viewStudents(console);
		}
		System.out.print("Enter ID: ");
		try {
			id = console.next();
			console.nextLine();
			CourseMgr.getInstance().getStudents().addStudentToList(new Student(lastName, firstName, id));
			manager.setChangesMade(true);
		} catch (Exception e) {
			System.out.println("\nInvalid input.");
		}
		viewStudents(console);
	}

	private static void deleteStudent(Scanner console) {
		System.out.print("Which student number: ");
		try {
			int student = console.nextInt();
			console.nextLine();
			System.out.print("Remove " + CourseMgr.getInstance().getStudents().getStudents().get(student - 1).getName()
					+ " from the student list? (Y/N): ");
			String confirm = console.nextLine();
			if (confirm.equals("Y")) {
				CourseMgr.getInstance().getStudents().removeStudentFromList(student - 1);
				manager.setChangesMade(true);
			}
		} catch (Exception e) {
			System.out.print("\nInvalid input.");
		}
		viewStudents(console);
	}

	private static void viewCourses(Scanner console) {

		String selection = "";

		while (!selection.equals("M")) {
			System.out.println();
			System.out.print(CourseMgr.getInstance().getCourses().toString());

			System.out.println();
			System.out.println("Select a course number or one of the following options:");
			System.out.println("Add Course (A)");
			System.out.println("Delete Course (D)");
			System.out.println("Course List (C)");
			System.out.println("Main Menu (M)");
			System.out.print("\nSelect Option: ");

			selection = console.nextLine();
			Course c;

			if (selection.equals("A")) {
				addCourse(console);
			} else if (selection.equals("D")) {
				deleteCourse(console);
			} else if (selection.equals("M")) {
				mainMenu(console);
			} else if (selection.equals("C")) {
				System.out.print(CourseMgr.getInstance().getCourses().toString());
			} else {
				try {
					c = CourseMgr.getInstance().getCourses().getCourses().get(Integer.parseInt(selection) - 1);
					courseOptions(c, console);
				} catch (Exception e) {
					System.out.println("\nInvalid input.");
				}
			}
		}
	}

	private static void deleteCourse(Scanner console) {
		System.out.print("Which course number: ");
		try {
			int course = console.nextInt();
			console.nextLine();
			System.out.print("Remove " + CourseMgr.getInstance().getCourses().getCourses().get(course - 1).getTitle()
					+ " from the course list? (Y/N)");
			String confirm = console.nextLine();
			if (confirm.equals("Y")) {
				CourseMgr.getInstance().getCourses().removeCourseFromList(course - 1);
				manager.setChangesMade(true);
			}
		} catch (Exception e) {
			System.out.print("\nInvalid input.");
		}
		viewCourses(console);
	}

	private static void addCourse(Scanner console) {
		String title = "";
		int period;
		System.out.print("Enter Course Title: ");
		try {
			title = console.nextLine();
		} catch (Exception e) {
			System.out.println("\nInvalid input.");
			viewCourses(console);
		}
		System.out.print("Enter Period: ");
		try {
			period = console.nextInt();
			console.nextLine();
			CourseMgr.getInstance().getCourses().addCourseToList(new Course(title, period));
			manager.setChangesMade(true);
		} catch (Exception e) {
			System.out.println("\nInvalid input.");
		}
		viewCourses(console);
	}

	private static void courseOptions(Course c, Scanner console) {

		String selection = "";

		while (!selection.equals("C") && !selection.equals("M")) {
			System.out.println();
			System.out.println("Course Options for " + c.getTitle() + ":");
			System.out.println("View Student Roster (S)");
			System.out.println("Change Period (P)");
			System.out.println("Return to Course List (C)");
			System.out.println("Return to Main Menu (M)");
			System.out.print("\nSelect Option: ");

			selection = console.nextLine();

			if (selection.equals("C")) {
				viewCourses(console);
			} else if (selection.equals("S")) {
				System.out.print(c.getStudentsAsString() + "\n");
				courseRosterOptions(c, console);
			} else if (selection.equals("P")) {
				System.out.print("Change to which Period?: ");
				try {
					c.setPeriod(console.nextInt());
					console.nextLine();
					manager.setChangesMade(true);
					System.out.println(c.getTitle() + " is now scheduled for Period " + c.getPeriod());
				} catch (Exception e) {
					System.out.println("\nInvalid input.");
					courseOptions(c, console);
				}
			} else if (selection.equals("M")) {
				mainMenu(console);
			} else {
				System.out.println("\nInvalid input.");
			}
		}
	}

	private static void courseRosterOptions(Course c, Scanner console) {

		String selection = "";

		while (!selection.equals("C") && !selection.equals("M")) {

			System.out.println();
			System.out.println("Student Options for " + c.getTitle() + ":");
			System.out.println("Add student to course (A)");
			System.out.println("Remove student from course (R)");
			System.out.println("Student Roster (S)");
			System.out.println("Return to Course Options (C)");
			System.out.println("Return to Main Menu (M)");
			System.out.print("\nSelect Option: ");
			selection = console.nextLine();

			if (selection.equals("A")) {
				String id;
				System.out.print("Enter Student ID: ");
				id = console.next();
				console.nextLine();
				try {
					if (!c.addStudent(manager.getStudents().getStudentById(id))) {
						System.out.println("No student exists.");
					} else {
						manager.setChangesMade(true);
					}
				} catch (Exception e) {
					System.out.println("\nInvalid input.");
					courseRosterOptions(c, console);
				}
			} else if (selection.equals("R")) {

				System.out.print("Which student number: ");
				try {
					int student = console.nextInt();
					console.nextLine();
					System.out.print(
							"Remove " + c.getStudents().get(student - 1).toString() + " from the course roster? (Y/N)");
					String confirm = console.nextLine();
					if (confirm.equals("Y")) {
						c.getStudents().remove(student - 1);
						manager.setChangesMade(true);
					} else {
						courseRosterOptions(c, console);
					}
				} catch (Exception e) {
					System.out.println("\nInvalid input.\n");
					courseRosterOptions(c, console);
				}
			} else if (selection.equals("S")) {
				System.out.print(c.getStudentsAsString() + "\n");
			} else if (selection.equals("C")) {
				courseOptions(c, console);
			} else if (selection.equals("M")) {
				mainMenu(console);
			}
		}

	}

}
