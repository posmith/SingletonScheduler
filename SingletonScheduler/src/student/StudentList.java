package student;

import student.Student;
import util.SortedLinkedList;

public class StudentList {
	/** List of students */
	private SortedLinkedList<Student> students;

	public StudentList() {
		students = new SortedLinkedList<Student>();
	}

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
	
	public void addStudentToList(String lastName, String firstName, String id) {
		Student s = new Student(lastName, firstName, id);
		addStudentToList(s);
	}

	public void removeStudentFromList(int index) {
		students.remove(index);
	}
	
	public String toString() {
		String s = "";
		int count = 0;
		for (int i = 0; i < students.size(); i++) {
			s += count + 1 + ". " + students.get(i).toString() + "\n";
			count++;
		}
		return s;
	}

	public int size() {
		return students.size();
	}

	public SortedLinkedList<Student> getStudents() {
		return students;
	}
	
	public Student getStudentById(String id) {
		for (int i = 0; i < students.size(); i++) {
			if (id.equals(students.get(i).getId())) {
				return students.get(i);
			}
		}
		return null;
	}

}
