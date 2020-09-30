package course;

import util.SortedLinkedList;

public class Conflict implements Comparable<Conflict> {
	
	private String studentName;
	private CourseList conflictingCourses;
	
	public Conflict(String name, CourseList courses) {
		setStudentName(name);
		conflictingCourses = courses;
	}
	
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
	
	public void addConflictingCourse(Course c) {
		if (c != null) {
			conflictingCourses.getCourses().add(c);
		}
	}
	
	public void addConflictingCourse(String title) {
		Course c = conflictingCourses.getCourseByName(title);
		addConflictingCourse(c);
	}
	
	public String toString( ) {
		return studentName + " is in the following courses:\n" + conflictingCourses.getCourses().toString(); 
	}

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
