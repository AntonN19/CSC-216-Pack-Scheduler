package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the sortedList
 * 
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 *
 */
public class SortedListTest {

	/**
	 * Testing to make sure the SortedList object is constructed correctly
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));

		// Test that the list grows by adding at least 11 elements
		// Remember the list's initial capacity is 10

		list.add("apple");
		list.add("orange");
		list.add("bannana");
		list.add("pecan");
		list.add("milk");
		list.add("salad");
		list.add("steak");
		list.add("chicken");
		list.add("tomato");
		list.add("onions");
		list.add("juice");
		assertEquals(11, list.size());
	}

	/**
	 * Testing the add method of the SortedList object
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// Test adding to the front, middle and back of the list
		try {
			list.add("apple");
			list.add("pineapple");
			list.add("corn");
			assertEquals(4, list.size());
		} catch (Exception e) {
			fail();
		}
		
		// Test adding a null element
		try {
			list.add(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(4, list.size());
		}
		
		// Test adding a duplicate element
		try {
			list.add("apple");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, list.size());
		}
	}

	/**
	 * Testing the get method
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		// Test getting an element from an empty list
		try {
			list.get(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		// Add some elements to the list
		list.add("apple");
		list.add("bannana");
		list.add("caper");
		list.add("dill");
		
		// Test getting an element at an index < 0
		try {
			list.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}
		
		// Test getting an element at size
		String str = "";
		try {
			str = list.get(4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("", str);
		}

	}

	/**
	 * Test remove method that remove element from sorted list
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		// Test removing from an empty list
		try {
			list.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		// Add some elements to the list - at least 4
		try {
			list.add("pear");
			list.add("peach");
			list.add("grape");
			list.add("pineapple");
			assertEquals(4, list.size());
			assertEquals("grape", list.get(0));
			assertEquals("peach", list.get(1));
			assertEquals("pear", list.get(2));
			assertEquals("pineapple", list.get(3));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		
		// Test removing an element at an index < 0
		try {
			list.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("grape", list.get(0));
			assertEquals("peach", list.get(1));
			assertEquals("pear", list.get(2));
			assertEquals("pineapple", list.get(3));
		}
		
		// Test removing an element at size
		try {
			list.remove(list.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("grape", list.get(0));
			assertEquals("peach", list.get(1));
			assertEquals("pear", list.get(2));
			assertEquals("pineapple", list.get(3));
		}
		
		// Test removing a middle element
		try {
			list.remove(2);
			assertEquals(3, list.size());
			assertEquals("grape", list.get(0));
			assertEquals("peach", list.get(1));
			assertEquals("pineapple", list.get(2));
		} catch (IndexOutOfBoundsException e) {
			fail();

		}
		
		// Test removing the last element
		try {
			list.remove(list.size() - 1);
			assertEquals(2, list.size());
			assertEquals("grape", list.get(0));
			assertEquals("peach", list.get(1));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		
		// Test removing the first element
		try {
			list.remove(0);
			assertEquals(1, list.size());
			assertEquals("peach", list.get(0));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		
		// Test removing the last element
		try {
			list.remove(list.size() - 1);
			assertEquals(0, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
	}

	/**
	 * Test indexOf method to check if there is NullPointerException
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		// Test indexOf on an empty list
		try {
			list.indexOf("apple");
			assertEquals(-1, list.indexOf("apple"));

		} catch (NullPointerException e) {
			fail();
		}
		// Add some elements
		try {
			list.add("apple");
			list.add("banana");
			list.add("grape");
			list.add("pineapple");
			assertEquals(4, list.size());
			assertEquals(0, list.indexOf("apple"));
			assertEquals(1, list.indexOf("banana"));
			assertEquals(2, list.indexOf("grape"));

			assertEquals(3, list.indexOf("pineapple"));

		} catch (NullPointerException e) {
			fail();
		}

		// Test various calls to indexOf for elements in the list
		// and not in the list
		try {
			assertEquals(-1, list.indexOf("watermelon"));
			assertEquals(-1, list.indexOf("plum"));
			assertEquals(-1, list.indexOf("cherry"));
			assertEquals(-1, list.indexOf("strawberry"));
		} catch (NullPointerException e) {
			fail();
		}
		// Test checking the index of null
		try {
			list.indexOf(null);

			fail();
		} catch (NullPointerException e) {
			assertEquals(4, list.size());
			assertEquals(0, list.indexOf("apple"));
			assertEquals(1, list.indexOf("banana"));
			assertEquals(2, list.indexOf("grape"));
			assertEquals(3, list.indexOf("pineapple"));
		}
	}

	/**
	 * Test to clear the list of sorted element
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("apple");
		list.add("peach");
		list.add("grape");
		list.add("pineapple");
		list.add("banana");
		
		// Clear the list
		list.clear();
		
		// Test that the list is empty
		try {
			assertEquals(true, list.isEmpty());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test if the isEmpty function for the sorted list works as implemented
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// Test that the list starts empty, add at least one element, then check that the list is no longer empty
		assertTrue(list.isEmpty());
		list.add("String");
		assertFalse(list.isEmpty());
	}

	/**
	 * Tests if the contains function for the sorted list functions correctly
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// Test the empty list case
		assertFalse(list.contains("string"));
		
		// Add an element
		list.add("String");

		// Test some true and false cases
		assertTrue(list.contains("String"));
		assertFalse(list.contains("string"));
	}

	/**
	 * Tests that the equals method works for SortedList
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("Apples");
		list1.add("Bananas");
		list2 = list1;
		list3.add("Bananas");
		list3.add("pinaple");

		// Test for equality and inequality
		assertTrue(list1.equals(list2));
		assertFalse(list1.equals(list3));
	}

	/**
	 * Tests that hashCode works correctly.
	 */

	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("Apples");
		list1.add("Bananas");
		list2 = list1;
		list3.add("Bananas");
		list3.add("pinaple");

		// Test for the same and different hashCodes
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
	}

}
