package manager;

import java.util.Iterator;
import java.util.TreeSet;

import course.Conflict;
import course.Course;
import course.CourseList;
import io.CourseIO;
import student.Student;
import student.StudentList;
import util.SortedLinkedList;

/**
 * Creates Singleton use of program to assist with use over multiple sessions.
 * CourseMgr abstracts functions from CourseList and allows for IO on specific
 * instance.
 * 
 * @author Patrick
 *
 */
public class CourseMgr {

	/** List of courses */
	private static CourseList courses;
	/** List of students */
	private static StudentList students;
	/** List of conflicts */
	private static SortedLinkedList<Conflict> conflicts;
	/** Instance used in Singleton design */
	private static CourseMgr instance;
	/** Tracks changes in order to prompt save before closing */
	private boolean changesMade;

	/**
	 * Constructs new CourseMgr with empty course and student lists. Private to
	 * prevent multiple instances at once.
	 */
	private CourseMgr() {
		courses = new CourseList();
		students = new StudentList();
		setChangesMade(false);
	}

	/**
	 * Static call to getInstance ensures that only a single instance of CourseMgr
	 * can exist at a time.
	 * 
	 * @return
	 */
	public static CourseMgr getInstance() {
		if (instance == null) {
			instance = new CourseMgr();
		}
		return instance;
	}

	/**
	 * Returns course list
	 * 
	 * @return
	 */
	public CourseList getCourses() {
		return courses;
	}

	/**
	 * Returns student list
	 * 
	 * @return
	 */
	public StudentList getStudents() {
		return students;
	}

	/**
	 * Returns whether or not changes have been made
	 * 
	 * @return the changesMade
	 */
	public boolean isChangesMade() {
		return changesMade;
	}

	/**
	 * Sets when changes have been made
	 * 
	 * @param changesMade the changesMade to set
	 */
	public void setChangesMade(boolean b) {
		changesMade = b;
	}

	/**
	 * Loads course enrollments from file
	 * 
	 * @param file
	 */
	public void loadCourseEnrollments(String file) {
		CourseIO.loadCourseEnrollments(instance, file);
	}

	/**
	 * Loads course periods from file
	 * 
	 * @param file
	 * @param useListMode
	 */
	public void loadPeriods(String file, boolean useListMode) {
		CourseIO.loadPeriods(instance, file, useListMode);
	}

	/**
	 * Writes changes to enrollment and period files. Creates files if they don't
	 * already exist.
	 * 
	 * @param enrollFile
	 * @param periodFile
	 */
	public void saveChanges(String enrollFile, String periodFile) {
		CourseIO.writeEnrollmentsToFile(instance, enrollFile);
		CourseIO.writePeriodsToFile(instance, periodFile);
	}

	/**
	 * Runs conflicts based on current course periods and student enrollments.
	 * Prints student names, conflicting courses, and course periods.
	 */
	public static void runConflicts() {
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
			for (int i = 0; i < conflicts.size(); i++) {
				System.out.println(conflicts.get(i).getStudentName() + ","
						+ conflicts.get(i).getConflictingCourses().get(0).getTitle() + ","
						+ conflicts.get(i).getConflictingCourses().get(1).getTitle() + ","
						+ conflicts.get(i).getConflictingCourses().get(0).getPeriod());
			}
			System.out.println("Total number of conflicts: " + conflicts.size());
		}
	}

	/**
	 * Prints conflicts by period.
	 */
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
	 * Used to debug CourseMgr functionality
	 */
	public void createTestCourses() {
		getInstance().getStudents().addStudentToList(new Student("Bradbury", "Ray", "1"));
		getInstance().getStudents().addStudentToList(new Student("Adams", "Douglas", "2"));
		getInstance().getStudents().addStudentToList(new Student("Tolkien", "John", "3"));
		getInstance().getStudents().addStudentToList(new Student("Herbert", "Frank", "4"));
		getInstance().getStudents().addStudentToList(new Student("Heinlein", "Robert", "5"));
		getInstance().getStudents().addStudentToList(new Student("Asimov", "Isaac", "6"));

		getInstance().getCourses().addCourseToList("Test Course 1", 1);
		getInstance().getCourses().addCourseToList("Test Course 2", 2);

		try {
			getInstance().getCourses().getCourseByName("Test Course 1")
					.addStudent(getInstance().getStudents().getStudentById("1"));
			getInstance().getCourses().getCourseByName("Test Course 1")
					.addStudent(getInstance().getStudents().getStudentById("2"));
			getInstance().getCourses().getCourseByName("Test Course 1")
					.addStudent(getInstance().getStudents().getStudentById("3"));
			getInstance().getCourses().getCourseByName("Test Course 2")
					.addStudent(getInstance().getStudents().getStudentById("4"));
			getInstance().getCourses().getCourseByName("Test Course 2")
					.addStudent(getInstance().getStudents().getStudentById("5"));
			getInstance().getCourses().getCourseByName("Test Course 2")
					.addStudent(getInstance().getStudents().getStudentById("6"));
		} catch (NullPointerException e) {
			System.out.println("Error in createCourses()");
		}
	}

}
