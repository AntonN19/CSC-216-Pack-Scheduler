/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests for the LinkedQueue Class
 * 
 * @author Anton
 */
public class LinkedQueueTest {

	/**
	 * Testing the LinkedQueue methods
	 */
	@Test
	public void test() {
		LinkedQueue<String> queue = new LinkedQueue<String>(5);
		queue.enqueue("String 1");
		queue.enqueue("String 2");
		queue.enqueue("String 3");
		queue.enqueue("String 4");
		assertEquals(4, queue.size());
		queue.setCapacity(4);
		try {
			queue.setCapacity(3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, queue.size());
		}
		try {
			queue.enqueue("STR");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, queue.size());
		}

		assertEquals("String 1", queue.dequeue());
		assertEquals("String 2", queue.dequeue());
		assertEquals("String 3", queue.dequeue());
		assertEquals("String 4", queue.dequeue());
		assertEquals(0, queue.size());

		try {
			queue.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertTrue(queue.isEmpty());
		}
	}
}
