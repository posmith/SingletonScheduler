package course;

import util.SortedLinkedList;

public class CourseList {
	/** List of courses */
	private SortedLinkedList<Course> courses;

	public CourseList() {
		courses = new SortedLinkedList<Course>();
	}

	public boolean addCourseToList(Course c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		if (courses.contains(c) ) {
			return false;
		}
		courses.add(c);
		return true;
	}

	public void addCourseToList(String title) {
		Course c = new Course(title);
		addCourseToList(c);
	}

	public void addCourseToList(String title, int period) {
		Course c = new Course(title, period);
		addCourseToList(c);
	}

	public void removeCourseFromList(int index) {
		courses.remove(index);
	}

	public String toString() {
		String s = "";
		int count = 0;
		for (int i = 0; i < courses.size(); i++) {
			s += count + 1 + ". " + courses.get(i).toString() + "\n";
			count++;
		}
		return s;
	}

	public int size() {
		return courses.size();
	}

	public SortedLinkedList<Course> getCourses() {
		return courses;
	}
	
	public Course getCourseByName(String title) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getTitle().equals(title)) {
				return courses.get(i);
			}
		}
		return null;
	}
}
