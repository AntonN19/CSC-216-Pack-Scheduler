/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * test conflict exceptions two main constructors on whether they actually pass the correct messages.
 * @author stephenwelsh
 */
public class ConflictExceptionTest {

	/**
	 * Test method for conflict exception to see if the custom message functions
	 */
	@Test
	public void testConflictExceptionString() {
		ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for conflict exception to see if the standard message is the same.
	 */
	@Test
	public void testConflictException() {
		ConflictException ce = new ConflictException();
		assertEquals("Schedule conflict.", ce.getMessage());
	}

}
