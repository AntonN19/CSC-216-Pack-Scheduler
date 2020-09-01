/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Tests for the LinkedStack Class
 * 
 * @author Anton
 *
 */
public class LinkedStackTest {

	/**
	 * Testing the LinkedStack functionality
	 */
	@Test
	public void testLinkedStack() {
		LinkedStack<String> stack = new LinkedStack<String>(5);
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
			assertEquals(4, stack.size());
		}
		try {
			stack.push("STR");
			fail();
		} catch(IllegalArgumentException e) {
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
