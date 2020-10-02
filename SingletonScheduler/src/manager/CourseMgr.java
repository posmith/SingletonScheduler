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

public class CourseMgr {

	private static CourseList courses;
	private static StudentList students;
	private static SortedLinkedList<Conflict> conflicts;
	private static CourseMgr instance;
	private boolean changesMade;

	private CourseMgr() {
		courses = new CourseList();
		students = new StudentList();
		setChangesMade(false);
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
	
	/**
	 * @return the changesMade
	 */
	public boolean isChangesMade() {
		return changesMade;
	}

	/**
	 * @param changesMade the changesMade to set
	 */
	public void setChangesMade(boolean b) {
		changesMade = b;
	}

	public void loadCourseEnrollments(String file) {
		CourseIO.loadCourseEnrollments(instance, file);
	}

	public void loadPeriods(String file, boolean useListMode) {
		CourseIO.loadPeriods(instance, file, useListMode);
	}

	public void saveChanges(String enrollFile, String periodFile) {
		CourseIO.writeEnrollmentsToFile(instance, enrollFile);
		CourseIO.writePeriodsToFile(instance, periodFile);
	}

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

	public void createTestCourses() {
		getInstance().getStudents().addStudentToList(new Student("Smith", "Patrick", "1"));
		getInstance().getStudents().addStudentToList(new Student("Jones", "Don", "2"));
		getInstance().getStudents().addStudentToList(new Student("Proctor", "Belinda", "3"));
		getInstance().getStudents().addStudentToList(new Student("Bullock", "Marla", "4"));
		getInstance().getStudents().addStudentToList(new Student("Do", "Harrison", "5"));
		getInstance().getStudents().addStudentToList(new Student("Thomas", "Clifton", "6"));

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
