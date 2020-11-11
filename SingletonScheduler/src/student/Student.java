package student;

import course.Course;
import util.SortedLinkedList;

/**
 * POJO for student which contains a first and last name, unique id, and list of
 * course enrollments.
 * 
 * @author Patrick
 *
 */
public class Student implements Comparable<Student> {
	/** Student's last name */
	private String lastName;
	/** Student's first name */
	private String firstName;
	/** Student ID number */
	private String id;
	/** List of courses student is enrolled in */
	private SortedLinkedList<Course> courses;

	/**
	 * Constructor with parameters for all elements of a student.
	 * 
	 * @param lastName
	 * @param firstName
	 * @param id
	 * @param courses
	 */
	public Student(String lastName, String firstName, String id, SortedLinkedList<Course> courses) {
		if (firstName == null || lastName == null || id == null) {
			throw new IllegalArgumentException();
		}
		setLastName(lastName);
		setFirstName(firstName);
		setId(id);
		setCourses(courses);
	}

	/**
	 * Constructor for a student not yet enrolled in courses. Creates a new list for
	 * course enrollments.
	 * 
	 * @param lastName
	 * @param firstName
	 * @param id
	 */
	public Student(String lastName, String firstName, String id) {
		this(lastName, firstName, id, new SortedLinkedList<Course>());
	}

	/**
	 * Sets student's first name
	 * 
	 * @param firstName
	 */
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets student's last name
	 * 
	 * @param lastName
	 */
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns a student's first name
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns a student's last name
	 * 
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns a student's ID number
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets a student's ID number
	 * 
	 * @param id2 the id to set
	 */
	public void setId(String id2) {
		this.id = id2;
	}

	/**
	 * Returns a SortedLinkedList of the student's course enrollments
	 * 
	 * @return the courses
	 */
	public SortedLinkedList<Course> getCourses() {
		return courses;
	}

	/**
	 * Sets the student's course enrollments to a SortedLinkedList of courses
	 * 
	 * @param courses the courses to set
	 */
	public void setCourses(SortedLinkedList<Course> courses) {
		this.courses = courses;
	}

	/**
	 * Returns the Student object as a string, with first and last names and ID
	 * number
	 */
	public String toString() {
		String name = getLastName() + ", " + getFirstName();
		if (name.length() > 20) {
			name = name.substring(0, 19);
		}
		while (name.length() < 20) {
			name += " ";
		}
		return "NAME: " + name + "\tID: " + getId();
	}

	/**
	 * Compares this student against a specified student based on last name then
	 * first name then ID Number
	 * 
	 */
	public int compareTo(Student s) {
		if (this.lastName.compareTo(s.lastName) < 0) {
			return -1;
		}
		if (this.lastName.compareTo(s.lastName) > 0) {
			return 1;
		}
		if (this.firstName.compareTo(s.firstName) < 0) {
			return -1;
		}
		if (this.firstName.compareTo(s.firstName) > 0) {
			return 1;
		}
		if (this.id.compareTo(s.id) < 0) {
			return -1;
		}
		if (this.id.compareTo(s.id) > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * Returns whether or not this student is the same as a specified student
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Student))
			return false;
		Student other = (Student) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	/**
	 * Adds a course to the student's course enrollments
	 * 
	 * @param c
	 * @return
	 */
	public boolean addCourse(Course c) {
		if (courses.contains(c)) {
			return false;
		} else {
			try {
				courses.add(c);
				c.addStudent(this);
				return true;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
	}

	/**
	 * Returns a student's first and last name
	 * 
	 * @return
	 */
	public String getName() {
		return getFirstName() + " " + getLastName();
	}

	/**
	 * Returns the student's list of course enrollments as a string with each course
	 * numbered and on a separate line.
	 * 
	 * @return
	 */
	public String getCoursesAsString() {
		int numCourses = courses.size();
		if (numCourses == 0) {
			return "<No Courses>";
		}
		String c = "";
		int count = 0;
		for (int i = 0; i < numCourses; i++) {
			c += count + 1 + ". " + courses.get(i).toString() + "\n";
			count++;
		}
		return c;
	}

}
