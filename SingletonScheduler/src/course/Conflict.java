package course;

import util.SortedLinkedList;

/**
 * Conflicts are defined as a set of two courses scheduled for the same period
 * within a single student's schedule. The same set of two courses can cause
 * multiple conflicts if multiple students are scheduled for both courses.
 * 
 * @author Patrick
 *
 */
public class Conflict implements Comparable<Conflict> {

	/** Student with the scheduling conflict */
	private String studentName;
	/** Set of two courses which conflict */
	private CourseList conflictingCourses;

	/**
	 * Constructor with name and courses parameter.
	 * 
	 * @param name
	 * @param courses
	 */
	public Conflict(String name, CourseList courses) {
		setStudentName(name);
		conflictingCourses = courses;
	}

	/**
	 * Constructor with empty course list
	 * 
	 * @param name
	 */
	public Conflict(String name) {
		this(name, new CourseList());
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the conflictingCourses
	 */
	public SortedLinkedList<Course> getConflictingCourses() {
		return conflictingCourses.getCourses();
	}

	/**
	 * Adds a course to set of conflicting courses
	 * 
	 * @param c
	 */
	public void addConflictingCourse(Course c) {
		if (c != null) {
			conflictingCourses.getCourses().add(c);
		}
	}

	/**
	 * Adds a course to set of conflicting courses by the course title
	 * 
	 * @param title
	 */
	public void addConflictingCourse(String title) {
		Course c = conflictingCourses.getCourseByName(title);
		addConflictingCourse(c);
	}

	/**
	 * Returns string with student name and conflicting course titles
	 */
	public String toString() {
		return studentName + " is in the following courses:\n" + conflictingCourses.getCourses().toString();
	}

	/**
	 * Compares Conflicts for sorting, first by period then title, then student name
	 */
	@Override
	public int compareTo(Conflict o) {
		if (this.getConflictingCourses().get(0).getPeriod() < o.getConflictingCourses().get(0).getPeriod()) {
			return -1;
		}
		if (this.getConflictingCourses().get(0).getPeriod() > o.getConflictingCourses().get(0).getPeriod()) {
			return 1;
		}
		if (this.getConflictingCourses().get(0).getTitle().compareTo(o.getConflictingCourses().get(0).getTitle()) < 0) {
			return -1;
		}
		if (this.getConflictingCourses().get(0).getTitle().compareTo(o.getConflictingCourses().get(0).getTitle()) > 0) {
			return 1;
		}
		if (this.getConflictingCourses().get(1).getTitle().compareTo(o.getConflictingCourses().get(1).getTitle()) < 0) {
			return -1;
		}
		if (this.getConflictingCourses().get(1).getTitle().compareTo(o.getConflictingCourses().get(1).getTitle()) > 0) {
			return 1;
		}
		if (this.studentName.compareTo(o.studentName) < 0) {
			return -1;
		}
		if (this.studentName.compareTo(o.studentName) > 0) {
			return 1;
		}
		return 0;
	}

}
