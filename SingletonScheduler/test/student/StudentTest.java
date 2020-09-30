package student;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import course.Course;
import util.SortedLinkedList;

public class StudentTest {

	Student s1;
	Student s2;
	Student s3;
	SortedLinkedList<Course> courses1;
	SortedLinkedList<Course> courses2;
	SortedLinkedList<Course> courses3;
	Course c1 = new Course("AP English", 1);
	Course c2 = new Course("AP Calculus", 2);
	Course c3 = new Course("AP Chemistry", 3);
	Course c4 = new Course("Wind Ensemble", 4);
	Course c5 = new Course("Dance Company", 5);
	Course c6 = new Course("Chamber Ensemble", 6);
	
	@Before
	public void setup() {
		courses1 = new SortedLinkedList<Course>();
		courses2 = new SortedLinkedList<Course>();
		courses3 = new SortedLinkedList<Course>();
		courses1.add(c1);
		courses1.add(c2);
		courses1.add(c3);
		courses2.add(c2);
		courses2.add(c3);
		courses2.add(c4);
		courses3.add(c4);
		courses3.add(c5);
		courses3.add(c6);
		/**
		s1 = new Student("Smith", "Patrick", 1, courses1);
		s2 = new Student("Jones", "Don", 2, courses2);
		s3 = new Student("Do", "Harrison", 3, courses3);
		*/
	}
	
	@Test
	public void testStudent() {
		assertEquals("NAME: Smith, Patrick\tID: 1", s1.toString());
		assertEquals("NAME: Jones, Don\tID: 2", s2.toString());
		assertEquals("NAME: Do, Harrison\tID: 3", s3.toString());
	}

	@Test
	public void testGetFirstName() {
		assertEquals("Patrick", s1.getFirstName());
		assertEquals("Don", s2.getFirstName());
		assertEquals("Harrison", s3.getFirstName());
	}

	@Test
	public void testGetLastName() {
		assertEquals("Smith", s1.getLastName());
		assertEquals("Jones", s2.getLastName());
		assertEquals("Do", s3.getLastName());
	}

	@Test
	public void testGetId() {
		assertEquals(1, s1.getId());
		assertEquals(2, s2.getId());
		assertEquals(3, s3.getId());
	}

	/**
	@Test
	public void testSetId() {
		s3.setId(4);
		assertEquals(4, s3.getId());
	}
	*/

	@Test
	public void testGetCourses() {
		assertEquals("-TITLE: AP Calculus\tPERIOD: 2\n-TITLE: AP Chemistry\tPERIOD: 3\n-TITLE: AP English\tPERIOD: 1", s1.getCourses().toString());
		assertEquals("-TITLE: AP Calculus\tPERIOD: 2\n-TITLE: AP Chemistry\tPERIOD: 3\n-TITLE: Wind Ensemble\tPERIOD: 4", s2.getCourses().toString());
		assertEquals("-TITLE: Chamber Ensemble\tPERIOD: 6\n-TITLE: Dance Company\tPERIOD: 5\n-TITLE: Wind Ensemble\tPERIOD: 4", s3.getCourses().toString());
	}

	@Test
	public void testSetCourses() {
		s1.setCourses(courses2);
		assertEquals("-TITLE: AP Calculus\tPERIOD: 2\n-TITLE: AP Chemistry\tPERIOD: 3\n-TITLE: Wind Ensemble\tPERIOD: 4", s1.getCourses().toString());
	}

	@Test
	public void testToString() {
		assertEquals("NAME: Smith, Patrick\tID: 1", s1.toString());
		assertEquals("NAME: Jones, Don\tID: 2", s2.toString());
		assertEquals("NAME: Do, Harrison\tID: 3", s3.toString());
	}

	/**
	@Test
	public void testCompareTo() {
		assertTrue(s1.compareTo(s2) > 0);
		assertTrue(s2.compareTo(s1) < 0);
		assertTrue(s1.compareTo(s3) > 0);
		assertTrue(s3.compareTo(s1) < 0);
		assertTrue(s2.compareTo(s3) > 0);
		assertTrue(s3.compareTo(s2) < 0);
		assertTrue(s1.compareTo(s1) == 0);
		Student s4 = new Student("Smith", "Patrick", 1, courses2);
		assertTrue(s1.compareTo(s4) == 0);
		assertTrue(s4.compareTo(s1) == 0);
	}

	@Test
	public void testEqualsObject() {
		assertFalse(s1.equals(s2));
		Student s4 = new Student("Smith", "Patrick", 1, courses2);
		assertTrue(s1.equals(s4));
		assertTrue(s4.equals(s1));
	}
	*/

	@Test
	public void testAddCourse() {
		s1.addCourse(c4);
		assertEquals("-TITLE: AP Calculus\tPERIOD: 2\n-TITLE: AP Chemistry\tPERIOD: 3\n-TITLE: AP English\tPERIOD: 1\n-TITLE: Wind Ensemble\tPERIOD: 4", s1.getCourses().toString());
	}

}
