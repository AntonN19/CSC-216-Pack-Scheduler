package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit test class used to test LinkedAbstractList and its methods
 * 
 * @author Anton
 */
public class LinkedAbstractListTest {

	/** String to be added to LinkedAbstractList for testing */
	private final String str1 = "String 1";
	
	/** String to be added to LinkedAbstractList for testing */
	private final String str2 = "String 2";
	
	/** String to be added to LinkedAbstractList for testing */
	private final String str3 = "String 3";
	
	/** String to be added to LinkedAbstractList for testing */
	private final String str4 = "String 4";
	
	/** The list capacity */
	private static final int CAPACITY = 101;
	
	/**
	 * Test made to check that the size of LinkedAbstractList is set to 0 when it is first constructed
	 */
	@Test
	public void testConstructor() {
		LinkedAbstractList<String> newList = new LinkedAbstractList<String>(CAPACITY);
		assertEquals(0, newList.size());
	}
	
	/**
	 * Testing the LinkedAbstractList add/remove/get/set methods.
	 */
	@Test
	public void testAddRemoveGetSet() {
		LinkedAbstractList<String> newList = new LinkedAbstractList<String>(CAPACITY);
		//Test the valid add functionality.
		newList.add(0, str2); // 2
		newList.add(1, str4); // 2 4
		newList.add(0, str1); // 1 2 4
		newList.add(2, str3); // 1 2 3 4
		assertEquals("String 1", newList.get(0));
		assertEquals("String 2", newList.get(1));
		assertEquals("String 3", newList.get(2));
		assertEquals("String 4", newList.get(3));
		assertEquals(4, newList.size());
		//Test the valid remove functionality.
		assertEquals("String 1", newList.remove(0)); // 2 3 4
		assertEquals("String 3", newList.remove(1)); // 2 4
		assertEquals("String 2", newList.get(0));
		assertEquals("String 4", newList.get(1));
		assertEquals(2, newList.size());
		
		//Test the valid set functionality.
		assertEquals("String 2", newList.set(0, str1)); // 1 4
		assertEquals("String 1", newList.set(0, str2)); // 2 4
		assertEquals("String 2", newList.get(0));
		assertEquals("String 4", newList.get(1));
		newList.remove(0);
		assertEquals("String 4", newList.get(0));
		newList.add(0, "String 2");
		//Test the invalid add functionality.
		try {
			newList.add(2, str2);
			fail("This element is already part of the list.");
		} catch (IllegalArgumentException e) {
			assertEquals(2, newList.size());
		}
		try {
			newList.add(2, null);
			fail("This element is null.");
		} catch (NullPointerException e) {
			assertEquals(2, newList.size());
		}
		
		try {
			newList.add(3, str1);
			fail("Index of add cannot be 3 when size is 2.");
		} catch(IndexOutOfBoundsException e) {
			assertEquals(2, newList.size());
		}
		try {
			newList.add(-1, str1);
			fail("Index of add cannot be -1.");
		} catch(IndexOutOfBoundsException e) {
			assertEquals(2, newList.size());
		}
		String str = null;
		//Test the invalid get functionality.
		try {
			str = newList.get(-1);
			fail("Index of get cannot be -1.");
		} catch(IndexOutOfBoundsException e) {
			assertNull(str);
		}
		try {
			str = newList.get(2);
			fail("Index of get cannot be 2 when size is 2.");
		} catch(IndexOutOfBoundsException e) {
			assertNull(str);
		}
		//Test the invalid set functionality.
		try {
			str = newList.set(1, str2);
			fail("This element is already part of the list.");
		} catch(IllegalArgumentException e) {
			assertNull(str);
		}
		try {
			str = newList.set(1, null);
			fail("This element is null.");
		} catch(NullPointerException e) {
			assertNull(str);
		}
		try {
			str = newList.set(-1, str1);
			fail("Index of set cannot be -1.");
		} catch(IndexOutOfBoundsException e) {
			assertNull(str);
		}
		try {
			str = newList.set(2, str1);
			fail("Index of set cannot be 2 when size is 2.");
		} catch(IndexOutOfBoundsException e) {
			assertNull(str);
		}
		//Test the invalid remove functionality.
		try {
			newList.remove(2);
			fail("Index of remove cannot be -1.");
		} catch(IndexOutOfBoundsException e) {
			assertEquals(2, newList.size());
		}
		try {
			newList.remove(2);
			fail("Index of remove cannot be 2 when size is 2.");
		} catch(IndexOutOfBoundsException e) {
			assertEquals(2, newList.size());
		}
	}
	
	/**
	 * Tests that the list can grow to a size larger than it's initial size without losing information.
	 */
	@Test
	public void testUnboundedSize() {
		LinkedAbstractList<Integer> newList = new LinkedAbstractList<Integer>(CAPACITY);
		for (int i = 0; i < 100; i++) {
			newList.add(i, i);
		}
		assertEquals(100, newList.size());
		for (int i = 0; i < 100; i++) {
			assertEquals(i, (int)newList.get(i));
		}
	}

}
