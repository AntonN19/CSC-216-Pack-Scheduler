/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test; 

/**
 * JUnit Test Class containing unit tests for ClassNameValidator
 * 
 * @author Art Schell
 * @author Ethan Mancini
 * @version February 25, 2020
 */
public class CourseNameValidatorTest {

	/** All possible variations of a valid course name */
	String[] validCourseNames = {
			"L000",
			"L000S",
			"LL000",
			"LL000S",
			"LLL000",
			"LLL000S",
			"LLLL000",
			"LLLL000S",
			"AZ059A",
			"AZ059Z"
	};

	
	/** An array of invalid course names for testing the CourseNameValidator FSM */
	String[] invalidCourseNames = {
			"",
			"L00",
			"L0000",
			"L0S",
			"L00S",
			"L0000S",
			"000",
			"LLLLL000",
			"L000SS",
			"L000S0",
			"L000S!",
			"@000S",
			"[000S",
			"L0/0S",
			"L0:0S",
			"L0:0@",
			"L0:0["
	};
	
	/**
	 * Tests the CourseNameValidator FSM programming pattern through its isValid() method
	 */
	@Test
	public void testIsValid() {
		CourseNameValidator validator = new CourseNameValidator();
		for (String courseName : validCourseNames) {
			try {
				assertTrue(courseName + " should be valid.", validator.isValid(courseName));
			} catch (InvalidTransitionException e) {
				fail(courseName + " should be valid.");
			}
		}
		for (String courseName : invalidCourseNames) {
			try {
				boolean result = validator.isValid(courseName);
				//If no exception is thrown, it must have returned false.
				assertFalse(courseName + " should be invalid.", result);
			} catch (InvalidTransitionException e) {
				//Success, this is expected
			}
		}
	}

}
