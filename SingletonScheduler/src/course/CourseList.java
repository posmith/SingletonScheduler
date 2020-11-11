package course;

import util.SortedLinkedList;

/**
 * Custom list of courses for abstraction
 * 
 * @author Patrick
 *
 */
public class CourseList {
	/** List of courses */
	private SortedLinkedList<Course> courses;

	/**
	 * Constructs a new course list
	 */
	public CourseList() {
		courses = new SortedLinkedList<Course>();
	}

	/**
	 * Adds a course to the course list
	 * 
	 * @param c
	 * @return
	 */
	public boolean addCourseToList(Course c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		if (courses.contains(c)) {
			return false;
		}
		courses.add(c);
		return true;
	}

	/**
	 * Adds a new course to the course list using the course title
	 * 
	 * @param title
	 */
	public void addCourseToList(String title) {
		Course c = new Course(title);
		addCourseToList(c);
	}

	/**
	 * Adds a new course to the course list using the course title and period
	 * 
	 * @param title
	 * @param period
	 */
	public void addCourseToList(String title, int period) {
		Course c = new Course(title, period);
		addCourseToList(c);
	}

	/**
	 * Removes a course from the list based on its index
	 * 
	 * @param index
	 */
	public void removeCourseFromList(int index) {
		courses.remove(index);
	}

	/**
	 * Returns course list as a string. Each course title is numbered in order on a
	 * separate line.
	 */
	public String toString() {
		String s = "";
		int count = 0;
		for (int i = 0; i < courses.size(); i++) {
			s += count + 1 + ". " + courses.get(i).toString() + "\n";
			count++;
		}
		return s;
	}

	/**
	 * Returns number of courses in course list
	 * 
	 * @return
	 */
	public int size() {
		return courses.size();
	}

	/**
	 * Returns the course list as a SortedLinkedList
	 * 
	 * @return
	 */
	public SortedLinkedList<Course> getCourses() {
		return courses;
	}

	/**
	 * Returns a course on the course list by its title. If the specified title
	 * isn't on the course list, returns null.
	 * 
	 * @param title
	 * @return
	 */
	public Course getCourseByName(String title) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getTitle().equals(title)) {
				return courses.get(i);
			}
		}
		return null;
	}
}
