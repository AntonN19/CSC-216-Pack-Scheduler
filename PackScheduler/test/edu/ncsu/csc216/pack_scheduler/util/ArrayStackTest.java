/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * A JUnit test class which tests the ArrayStack class' functionality
 * 
 * @author Anton
 *
 */
public class ArrayStackTest {

	/**
	 * Testing the ArraySTack functionality
	 */
	@Test
	public void testArrayStack() {
		ArrayStack<String> stack = new ArrayStack<String>(5);
		stack.push("String 1");
		stack.push("String 2");
		stack.push("String 3");
		stack.push("String 4");
		assertEquals(4, stack.size());
		stack.setCapacity(4);
		try {
			stack.setCapacity(3);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals("Capacity can not be lower than size", e.getMessage());
		}
		try {
			stack.push("STR");
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals("Can not add to full stack", e.getMessage());
			assertEquals(4, stack.size());
		}
		
		assertEquals("String 4", stack.pop());
		assertEquals("String 3", stack.pop());
		assertEquals("String 2", stack.pop());
		assertEquals("String 1", stack.pop());
		assertEquals(0, stack.size());
		
		try {
			stack.pop();
			fail();
		} catch (EmptyStackException e) {
			assertTrue(stack.isEmpty());
		}
	}

}
