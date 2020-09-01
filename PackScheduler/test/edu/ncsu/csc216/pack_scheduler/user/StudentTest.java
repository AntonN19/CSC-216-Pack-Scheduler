/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Class created to test the Student.java class
 * 
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 */
public class StudentTest {
	/** First name */
	private static final String FIRST_NAME = "first";
	/** Last Name */
	private static final String LAST_NAME = "last";
	/** Id */
	private static final String ID = "id";
	/** Email */
	private static final String EMAIL = "email@ncsu.edu";
	/** Password */
	private static final String PASSWORD = "hashedpassword";
	/** Max Credit */
	private static final int MAX_CREDIT = 18;

	/**
	 * Tests the Student constructor with all field parameters
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {

		Student s = null; // Initialize a student reference to null
		try {
			s = new Student(null, "last", "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		s = null;
		try {
			s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(15, s.getMaxCredits());
		} catch (IllegalArgumentException e) {
			fail();
		}
		// Testing for null first name
		s = null;
		try {
			s = new Student(null, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDIT);
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// Testing for null last name
		s = null;
		try {
			s = new Student(FIRST_NAME, null, ID, EMAIL, PASSWORD, MAX_CREDIT);
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// Testing for null id
		s = null;
		try {
			s = new Student(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_CREDIT);
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// Testing for null email
		s = null;
		try {
			s = new Student(FIRST_NAME, LAST_NAME, ID, null, PASSWORD, MAX_CREDIT);
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// Testing for null password
		s = null;
		try {
			s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, null, MAX_CREDIT);
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}

	}

	/**
	 * Test the Student constructor with 5 parameters
	 */
	@Test
	public void testStudentStringStringStringStringString() {
		Student s = null;
		try {
			s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test setFirstName().
	 */
	@Test
	public void testSetFirstName() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		// Test that setting FirstName to null does not change the firstName (or
		// anything else)
		try {
			s.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}

		// Test that setting FirstName to empty string does not change the firstName (or
		// anything else)
		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}

		// Test valid first name
		s.setFirstName("John");
		assertEquals("John", s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(MAX_CREDIT, s.getMaxCredits());

	}

	/**
	 * Test setLastName().
	 */
	@Test
	public void testSetLastName() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		// Test that setting LastName to null does not change the lastName (or
		// anything else)
		try {
			s.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}
		// Test that setting LastName to empty string does not change the lastName (or
		// anything else)
		try {
			s.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}

		// Test valid last name
		s.setLastName("Smith");
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals("Smith", s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(MAX_CREDIT, s.getMaxCredits());
	}

	/**
	 * Test setEmail().
	 */
	@Test
	public void testSetEmail() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		// Test that setting email to null does not change the email (or
		// anything else)
		try {
			s.setEmail(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}
		// Test that setting email to empty string does not change the email (or
		// anything else)
		try {
			s.setEmail("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}
		// Test that setting email without @
		try {
			s.setEmail("email.ncsu.edu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}
		// Test that setting email without "."
		try {
			s.setEmail("email@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}
		// Test that "." before @
		try {
			s.setEmail("first.last@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}
		
		
		// Test valid email
		s.setEmail("student@ncsu.edu");
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals("student@ncsu.edu", s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(MAX_CREDIT, s.getMaxCredits());

	}

	/**
	 * Test setPassword().
	 */
	@Test
	public void testSetPassword() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		// Test that setting password to null does not change the password (or
		// anything else)
		try {
			s.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}
		// Test that setting password to empty string does not change the password (or
		// anything else)
		try {
			s.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}

		// Test valid password
		s.setPassword("password");
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals("password", s.getPassword());
		assertEquals(MAX_CREDIT, s.getMaxCredits());
	}

	/**
	 * Test setMaxCredits
	 */
	@Test
	public void testSetMaxCredits() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		// Test above max credit limit
		try {
			s.setMaxCredits(19);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());
		}
		// Test below min credit
		try {
			s.setMaxCredits(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDIT, s.getMaxCredits());

		}

		// Test valid min credit limit
		s.setMaxCredits(3);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(3, s.getMaxCredits());

		// Test valid max credit limit
		s.setMaxCredits(18);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(18, s.getMaxCredits());

		// Test valid credit
		s.setMaxCredits(13);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(13, s.getMaxCredits());
	}

	/**
	 * Test hashCode().
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDIT);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDIT);
		User s3 = new Student(FIRST_NAME, "john", ID, EMAIL, PASSWORD, MAX_CREDIT);
		User s4 = new Student(FIRST_NAME, LAST_NAME, "ids", EMAIL, PASSWORD, MAX_CREDIT);
		User s5 = new Student(FIRST_NAME, LAST_NAME, ID, "notValid@ncsu.edu", PASSWORD, MAX_CREDIT);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "word", MAX_CREDIT);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);

		// Test for the same hash code for the same values
		assertEquals(s1.hashCode(), s2.hashCode());

		// Test for each of the fields
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
	}

	/**
	 * Test equalsObject().
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDIT);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDIT);
		User s3 = new Student(FIRST_NAME, "john", ID, EMAIL, PASSWORD, MAX_CREDIT);
		User s4 = new Student(FIRST_NAME, LAST_NAME, "ids", EMAIL, PASSWORD, MAX_CREDIT);
		User s5 = new Student(FIRST_NAME, LAST_NAME, ID, "notValid@ncsu.edu", PASSWORD, MAX_CREDIT);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "word", MAX_CREDIT);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);

		// Test for equality in both directions
		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));

		// Test for each of the fields
		assertFalse(s1.equals(s3));
		assertFalse(s1.equals(s4));
		assertFalse(s1.equals(s5));
		assertFalse(s1.equals(s6));
		assertFalse(s1.equals(s7));
	}

	/**
	 * Test toString().
	 */
	@Test
	public void testToString() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		String string1 = FIRST_NAME + "," + LAST_NAME + "," + ID + "," + EMAIL + "," + PASSWORD + "," + 15;
		String string2 = FIRST_NAME + "," + LAST_NAME + "," + ID + "," + EMAIL + "," + PASSWORD + "," + MAX_CREDIT;
		assertEquals(string1, s1.toString());
		assertEquals(string2, s2.toString());
	}
	
	/**
	 * Test the comparable function for Student
	 * 
	 */
	@Test
	public void testCompareTo() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s3 = new Student("john", "smith", "jsmith", EMAIL, PASSWORD);
		Student s4 = new Student("abby", "box", "abox", EMAIL, PASSWORD);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, "notId", EMAIL, PASSWORD);
		
		try {
			s1.compareTo(null);
			fail();
		}
		catch(NullPointerException e) {
			assertEquals(0, s1.compareTo(s2));
			
			assertTrue(s1.compareTo(s3) < 0);
			assertTrue(s1.compareTo(s4) > 0);
			assertTrue(s1.compareTo(s5) < 0);
		}
	}
	
	/**
	 * Test Student.canAdd()
	 */
	@Test
	public void testCanAdd() {
		// Construct Courses for use in test		
		final Course course1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith4", 50, "MW", 1330, 1445);
		final Course course2 = new Course("CSC116", "Introduction to Programming - Java", "002", 3, "sesmith5", 50, "TH", 1230, 1325);
		final Course course3 = new Course("CSC236", "Introduction to Programming - Java", "002", 3, "sesmith5", 50, "TH", 1230, 1345);
		
		// Create a Student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);
		
		// Assert that COURSE_1 and COURSE_2 can be added to the schedule, then add them
		assertTrue(s.canAdd(course1));
		assertTrue(s.canAdd(course2));
		assertTrue(s.getSchedule().addCourseToSchedule(course1));
		assertTrue(s.getSchedule().addCourseToSchedule(course2));
		// Assert that a passing a null Course returns false
		assertFalse(s.canAdd(null));
		// Assert that a duplicate Course cannot be added to the schedule
		assertFalse(s.canAdd(course1));
		// Assert that a Course with a time conflict cannot be added to the Schedule
		assertFalse(s.canAdd(course3));
		// Remove COURSE_2 from the Student's Schedule
		assertTrue(s.getSchedule().removeCourseFromSchedule(course2));
		// Set the Student's max credits to a value less than the total credits of COURSE_1 and COURSE_2
		s.setMaxCredits(6);
		// Assert that a Course cannot be added to the Student's Schedule if it would cause the Schedule to exceed the Student's maximum allowable credits
		assertFalse(s.canAdd(course2));
	}

}
