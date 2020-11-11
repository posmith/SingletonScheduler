package student;

import student.Student;
import util.SortedLinkedList;

/**
 * Custom list of courses for abstraction
 * 
 * @author Patrick
 *
 */
public class StudentList {
	/** List of students */
	private SortedLinkedList<Student> students;

	/**
	 * Constructs a new student list
	 */
	public StudentList() {
		students = new SortedLinkedList<Student>();
	}

	/**
	 * Adds a student to the student list
	 * 
	 * @param s
	 * @return
	 */
	public boolean addStudentToList(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		if (students.contains(s)) {
			return false;
		}
		students.add(s);
		return true;
	}

	/**
	 * Adds a new student to the student list using their first and last names and a
	 * unique ID number
	 * 
	 * @param lastName
	 * @param firstName
	 * @param id
	 */
	public void addStudentToList(String lastName, String firstName, String id) {
		Student s = new Student(lastName, firstName, id);
		addStudentToList(s);
	}

	/**
	 * Removes a student from the list
	 * 
	 * @param index
	 */
	public void removeStudentFromList(int index) {
		students.remove(index);
	}

	/**
	 * Returns a string of the student list, with each student's name and ID
	 * numbered and on a separate line
	 */
	public String toString() {
		String s = "";
		int count = 0;
		for (int i = 0; i < students.size(); i++) {
			s += count + 1 + ". " + students.get(i).toString() + "\n";
			count++;
		}
		return s;
	}

	/**
	 * Returns the size of the student list
	 * 
	 * @return
	 */
	public int size() {
		return students.size();
	}

	/**
	 * Returns the student list as a SortedLinkedList
	 * 
	 * @return
	 */
	public SortedLinkedList<Student> getStudents() {
		return students;
	}

	/**
	 * Returns a student with the given ID number. Returns null if no student with
	 * the ID number is in the list.
	 * 
	 * @param id
	 * @return
	 */
	public Student getStudentById(String id) {
		for (int i = 0; i < students.size(); i++) {
			if (id.equals(students.get(i).getId())) {
				return students.get(i);
			}
		}
		return null;
	}

}
