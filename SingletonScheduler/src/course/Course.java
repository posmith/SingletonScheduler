package course;

import student.Student;
import util.SortedLinkedList;

/**
 * POJO for a course which contains a title, a list of students enrolled, a
 * period, and a unique course code.
 * 
 * @author Patrick
 *
 */
public class Course implements Comparable<Course> {
	/** List of students in the course */
	private SortedLinkedList<Student> students;
	/** Title of course */
	private String title;
	/** Period course is offered */
	private int period;
	/** Unique course code of course */
	private String courseCode;

	/**
	 * Constructor with parameters to specify all elements of a course.
	 * 
	 * @param students
	 * @param title
	 * @param period
	 * @param courseCode
	 */
	public Course(SortedLinkedList<Student> students, String title, int period, String courseCode) {
		this.students = students;
		setTitle(title);
		setPeriod(period);
		setCourseCode(courseCode);
	}

	/**
	 * Constructor with parameters for title and period, and creates an empty
	 * student list.
	 * 
	 * @param title
	 * @param period
	 */
	public Course(String title, int period) {
		this(new SortedLinkedList<Student>(), title, period, null);
	}

	/**
	 * Constructor with parameter for title only, creates empty student list and
	 * period 0 (unusable)
	 * 
	 * @param title
	 */
	public Course(String title) {
		this(new SortedLinkedList<Student>(), title, 0, null);
	}

	/**
	 * Constructor with parameters for title and course code, creates empty student
	 * list and period 0 (unusable)
	 * 
	 * @param title
	 * @param courseCode
	 */
	public Course(String title, String courseCode) {
		this(new SortedLinkedList<Student>(), title, 0, courseCode);
	}

	/**
	 * Adds a new student name to course. Returns true if added.
	 * 
	 * @param student name of student
	 * @return true if student can be added to course
	 */
	public boolean addStudent(Student s) {
		if (students.contains(s)) {
			return false;
		} else {
			try {
				students.add(s);
				s.addCourse(this);
				return true;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
	}

	/**
	 * Returns list of students enrolled in course as a String.
	 * 
	 * @return String of list of students (one per line)
	 */
	public String getStudentsAsString() {
		String s = "";
		int count = 0;
		for (int i = 0; i < students.size(); i++) {
			s += count + 1 + ". " + students.get(i).toString() + "\n";
			count++;
		}
		return s;
	}

	/**
	 * Returns list of students enrolled in course as a list.
	 * 
	 * @return List of students in the course
	 */
	public SortedLinkedList<Student> getStudents() {
		return students;
	}

	/**
	 * Sets title of course. Throws IllegalArgumentException if title is null.
	 * 
	 * @param title of course
	 */
	private void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Must enter title");
		}
		this.title = title;
	}

	/**
	 * Returns title of course.
	 * 
	 * @return title of course
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets period course meets. Throws IllegalArgumentException if period is less
	 * than 1 or greater than 7.
	 * 
	 * @param period course meets
	 */
	public void setPeriod(int period) {
		if (period < 0 || period > 7) {
			throw new IllegalArgumentException("Period must be between 0 and 7");
		}
		this.period = period;
	}

	/**
	 * Returns period course meets.
	 * 
	 * @return period course meets
	 */
	public int getPeriod() {
		return period;
	}

	public int compareTo(Course c) {
		if (this.title.compareTo(c.getTitle()) < 0) {
			return -1;
		}
		if (this.title.compareTo(c.getTitle()) > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * Returns course as string with title and period
	 */
	public String toString() {
		String title = getTitle();
		if (title.length() > 20) {
			title = title.substring(0, 19);
		}
		while (title.length() < 20) {
			title += " ";
		}
		return "TITLE: " + title + "\tPERIOD: " + getPeriod();
	}

	/**
	 * Returns hashcode for this object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Returns true if parameter matches this object, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * @return the courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * @param courseCode the courseCode to set
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

}
