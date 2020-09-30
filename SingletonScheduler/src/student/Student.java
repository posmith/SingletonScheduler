package student;


import course.Course;
import util.SortedLinkedList;

public class Student implements Comparable<Student> {
	private String lastName;
	private String firstName;
	private String id;
	private SortedLinkedList<Course> courses;
	
	public Student(String lastName, String firstName, String id, SortedLinkedList<Course> courses) {
		if (firstName == null || lastName == null || id == null) {
			throw new IllegalArgumentException();
		}
		setLastName(lastName);
		setFirstName(firstName);
		setId(id);
		setCourses(courses);
	}
	
	public Student(String lastName, String firstName, String id) {
		this(lastName, firstName, id, new SortedLinkedList<Course>());
	}
	
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private void setLastName(String lastName) {
		this.lastName = lastName;		
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id2 the id to set
	 */
	public void setId(String id2) {
		this.id = id2;
	}
	

	/**
	 * @return the courses
	 */
	public SortedLinkedList<Course> getCourses() {
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(SortedLinkedList<Course> courses) {
		this.courses = courses;
	}	

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

	public String getName() {
		return getFirstName() + " " + getLastName();
	}

	public String getCoursesAsString() {
		String c = "";
		int count = 0;
		for (int i = 0; i < courses.size(); i++) {
			c += count + 1 + ". " + courses.get(i).toString() + "\n";
			count++;
		}
		return c;
	}


}
