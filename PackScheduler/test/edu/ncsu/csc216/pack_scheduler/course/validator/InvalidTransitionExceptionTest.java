package edu.ncsu.csc216.pack_scheduler.course.validator;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test made to ensure that the invalid transition exception is thrown as expected
 * 
 * @author Anton
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Test method for InvalidSTransitionException with a string
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ce = new InvalidTransitionException("Custom exception message");
		assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for InvalidTransitionException
	 */
	@Test
	public void testInvalidTransitionException() {
		InvalidTransitionException ce = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ce.getMessage());
	}

}
