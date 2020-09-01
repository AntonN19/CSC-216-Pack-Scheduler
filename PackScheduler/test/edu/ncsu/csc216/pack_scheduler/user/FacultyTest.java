package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Contains unit tests for the Faculty class and its methods, based off of the unit tests in StudentTest
 * 
 * @author Ethan Mancini
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 */
public class FacultyTest {
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
	private static final int MAX_COURSES = 2;
	
	/**
	 * Tests the Student constructor with all field parameters
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {

		Faculty f = null; // Initialize a student reference to null
		try {
			f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		} catch (IllegalArgumentException e) {
			fail();
		}
		// Testing for null first name
		f = null;
		try {
			f = new Faculty(null, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		} catch (IllegalArgumentException e) {
			assertNull(f);
		}
		// Testing for null last name
		f = null;
		try {
			f = new Faculty(FIRST_NAME, null, ID, EMAIL, PASSWORD, MAX_COURSES);
		} catch (IllegalArgumentException e) {
			assertNull(f);
		}
		// Testing for null id
		f = null;
		try {
			f = new Faculty(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_COURSES);
		} catch (IllegalArgumentException e) {
			assertNull(f);
		}
		// Testing for null email
		f = null;
		try {
			f = new Faculty(FIRST_NAME, LAST_NAME, ID, null, PASSWORD, MAX_COURSES);
		} catch (IllegalArgumentException e) {
			assertNull(f);
		}
		// Testing for null password
		f = null;
		try {
			f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, MAX_COURSES);
		} catch (IllegalArgumentException e) {
			assertNull(f);
		}

	}
	
	/**
	 * Test setFirstName().
	 */
	@Test
	public void testSetFirstName() {
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		// Test that setting FirstName to null does not change the firstName (or
		// anything else)
		try {
			f.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}

		// Test that setting FirstName to empty string does not change the firstName (or
		// anything else)
		try {
			f.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}

		// Test valid first name
		f.setFirstName("John");
		assertEquals("John", f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(EMAIL, f.getEmail());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(MAX_COURSES, f.getMaxCourses());

	}

	/**
	 * Test setLastName().
	 */
	@Test
	public void testSetLastName() {
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		// Test that setting LastName to null does not change the lastName (or
		// anything else)
		try {
			f.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		// Test that setting LastName to empty string does not change the lastName (or
		// anything else)
		try {
			f.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}

		// Test valid last name
		f.setLastName("Smith");
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals("Smith", f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(EMAIL, f.getEmail());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(MAX_COURSES, f.getMaxCourses());
	}

	/**
	 * Test setEmail().
	 */
	@Test
	public void testSetEmail() {
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		// Test that setting email to null does not change the email (or
		// anything else)
		try {
			f.setEmail(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		// Test that setting email to empty string does not change the email (or
		// anything else)
		try {
			f.setEmail("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		// Test that setting email without @
		try {
			f.setEmail("email.ncsu.edu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		// Test that setting email without "."
		try {
			f.setEmail("email@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		// Test that "." before @
		try {
			f.setEmail("first.last@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		
		
		// Test valid email
		f.setEmail("faculty@ncsu.edu");
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals("faculty@ncsu.edu", f.getEmail());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(MAX_COURSES, f.getMaxCourses());

	}

	/**
	 * Test setPassword().
	 */
	@Test
	public void testSetPassword() {
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		// Test that setting password to null does not change the password (or
		// anything else)
		try {
			f.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		// Test that setting password to empty string does not change the password (or
		// anything else)
		try {
			f.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}

		// Test valid password
		f.setPassword("password");
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(EMAIL, f.getEmail());
		assertEquals("password", f.getPassword());
		assertEquals(MAX_COURSES, f.getMaxCourses());
	}

	/**
	 * Test setMaxCredits
	 */
	@Test
	public void testSetMaxCredits() {
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		// Test above max credit limit
		try {
			f.setMaxCourses(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		// Test below min credit
		try {
			f.setMaxCourses(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}

		// Test valid min credit limit
		f.setMaxCourses(1);
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(EMAIL, f.getEmail());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(1, f.getMaxCourses());

		// Test valid max credit limit
		f.setMaxCourses(3);
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(EMAIL, f.getEmail());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(3, f.getMaxCourses());

		// Test valid credit
		f.setMaxCourses(2);
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(EMAIL, f.getEmail());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(2, f.getMaxCourses());
	}

	/**
	 * Test hashCode().
	 */
	@Test
	public void testHashCode() {
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s3 = new Faculty(FIRST_NAME, "john", ID, EMAIL, PASSWORD, MAX_COURSES);
		User s4 = new Faculty(FIRST_NAME, LAST_NAME, "ids", EMAIL, PASSWORD, MAX_COURSES);
		User s5 = new Faculty(FIRST_NAME, LAST_NAME, ID, "notValid@ncsu.edu", PASSWORD, MAX_COURSES);
		User s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "word", MAX_COURSES);
		User s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 3);

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
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s3 = new Faculty(FIRST_NAME, "john", ID, EMAIL, PASSWORD, MAX_COURSES);
		User s4 = new Faculty(FIRST_NAME, LAST_NAME, "ids", EMAIL, PASSWORD, MAX_COURSES);
		User s5 = new Faculty(FIRST_NAME, LAST_NAME, ID, "notValid@ncsu.edu", PASSWORD, MAX_COURSES);
		User s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "word", MAX_COURSES);
		User s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 3);

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
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 3);
		User s2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		String string1 = FIRST_NAME + "," + LAST_NAME + "," + ID + "," + EMAIL + "," + PASSWORD + "," + 3;
		String string2 = FIRST_NAME + "," + LAST_NAME + "," + ID + "," + EMAIL + "," + PASSWORD + "," + MAX_COURSES;
		assertEquals(string1, s1.toString());
		assertEquals(string2, s2.toString());
	}
	
}
