package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Test made for the CourseRoll class
 * 
 * @author Anton
 */
public class CourseRollTest {

	/**
	 * Testing the CourseRoll class
	 */
	@Test
	public void testCourseRoll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "MW", 1330, 1445);
		CourseRoll newRoll = c.getCourseRoll();
		try {
			newRoll.setEnrollmentCap(251);
		} catch(IllegalArgumentException e) {
			assertEquals(10, newRoll.getEnrollmentCap());
		}
		try {
			newRoll.setEnrollmentCap(9);
		} catch(IllegalArgumentException e) {
			assertEquals(10, newRoll.getEnrollmentCap());
		}
		
		//testing enroll
		Student nullS = null;
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Student s2 = new Student("firstN", "lastN", "idN", "flast@ncsu.edu", "password");
		newRoll.enroll(s1);
		newRoll.enroll(s2);
		assertEquals(8, newRoll.getOpenSeats());
		try {
			newRoll.enroll(nullS);
			fail("cant add a null student");
		} catch (IllegalArgumentException e) {
			assertEquals(8, newRoll.getOpenSeats());
		}
		newRoll.drop(s1);
		assertEquals(9, newRoll.getOpenSeats());
		assertTrue(newRoll.canEnroll(s1));
		
		// Testing new waitlist implementation
		int seats = newRoll.getOpenSeats();
		for (int i = 0; i < seats; i++) {
			newRoll.enroll(new Student("first" + i, "last" + i, "id" + i, "email" + i + "@ncsu.edu", "hashedpassword"));
		}
		assertEquals(0, newRoll.getOpenSeats());
		Student w1 = new Student("wait1", "list1", "waitlist1", "emailw1@ncsu.edu", "hashedpassword");
		Student w2 = new Student("wait2", "list2", "waitlist2", "emailw2@ncsu.edu", "hashedpassword");
		Student w3 = new Student("wait3", "list3", "waitlist3", "emailw3@ncsu.edu", "hashedpassword");
		assertTrue(newRoll.canEnroll(w1));
		assertTrue(newRoll.canEnroll(w2));
		assertTrue(newRoll.canEnroll(w3));
		assertFalse(newRoll.canEnroll(s2));
		newRoll.enroll(w1);
		newRoll.enroll(w2);
		assertFalse(newRoll.canEnroll(w1));
		assertFalse(newRoll.canEnroll(w2));
		assertTrue(newRoll.canEnroll(w3));
		newRoll.enroll(w3);
		for (int i = 0; i < 10; i++) {
			try {
				newRoll.enroll(new Student("wait" + (4 + i), "list3" + (4 + i), "waitlist3" + (4 + i), "emailw" + (4 + i) + "@ncsu.edu", "hashedpassword"));
			}
			catch (Exception e) {
				// Do nothing
			}
		}
		newRoll.drop(s2);
		newRoll.drop(w3);
	}
}
