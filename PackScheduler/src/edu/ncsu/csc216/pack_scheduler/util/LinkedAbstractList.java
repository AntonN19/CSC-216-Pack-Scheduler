/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Custom implementation of AbstractList
 * 
 * @param <E> the type of Object to be stored in the list
 * @author Anton
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** List node which refers to the first node in the list */
	private ListNode front;

	/** List node which refers to the last node in the list */
	private ListNode back;

	/** Size of the list */
	private int size;

	/** The capacity of the list */
	private int capacity;

	/**
	 * Constructor for the LinkedAbstractList
	 * 
	 * @param cap The lists capacity
	 * @throws IllegalArgumentException if the capacity is less than zero or if the
	 *                                  size is greater than capacity.
	 */
	public LinkedAbstractList(int cap) {
		this.front = null;
		if (cap < 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = cap;
		this.size = 0;
		if (this.capacity < this.size) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Sets capacity of the list
	 * 
	 * @param capacity new capacity
	 * @throws IllegalArgumentException if the capacity is less than 0 or less than
	 *                                  the current list size
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < this.size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Method used to insert elements into a certain part of the list.
	 * 
	 * @param index   the location at which the element should be inserted
	 * @param element the element being inserted
	 * @throws IllegalArgumentException  if the list is already full or the element
	 *                                   already exists in the list
	 * @throws NullPointerException      if the element trying to be added is null
	 * @throws IndexOutOfBoundsException if the index of where to add the element is
	 *                                   outside of the list size
	 */
	@Override
	public void add(int index, E element) {
		if (this.size == this.capacity) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		ListNode previous = current;

		if (front != null) { // checks that list is not empty			
			// Checks for duplicate elements
			for (int i = 0; i < size; i++) {
				if (element.equals(current.data)) {
					throw new IllegalArgumentException();
				}
				if (i == size - 1) {
					break;
				} else {
					current = current.next;
				}
			}

			current = front;
			// adds to the front of the list
			if (index == 0) {
				front = new ListNode(element, current);
			} else if (index == size) { // adds to the end
				if (size == 1) { // if only one element
					back = new ListNode(element);
					front.next = back;
				} else {
					back.next = new ListNode(element);
					back = back.next;
				}
			} else { // adds to the middle
				for (int i = 0; i < index; i++) {
					if (i != 0) {
						previous = previous.next;
					}
					current = current.next;
				}
				current = new ListNode(element, current);
				previous.next = current;
			}
		} else { // for an empty list
			front = new ListNode(element);
			back = front;
		}
		size++;
	}

	/**
	 * Method used to remove an element from the list
	 * 
	 * @param index the index of the element to be removed
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if the index of the element to be removed
	 *                                   is not inside the list
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		E ret = null;
		ListNode removed = this.front;
		if (index == 0) { // if removing the first element
			front = front.next;
		} else {
			ListNode current = front;
			for (int i = 1; i < index; i++) {
				current = current.next;
			}
			removed = current.next;
			current.next = current.next.next;

			for (int i = index + 1; i < size; i++) {
				current = current.next;
			}
			back = current;
		}
		size--;
		ret = removed.data;
		return ret;
	}

	/**
	 * Method used to change a certain element already in the list.
	 * 
	 * @param index   the index of the element in the list
	 * @param element the new element
	 * @throws IllegalArgumentException  if the element already exists in the list
	 * @throws NullPointerException      if the new element value is null
	 * @throws IndexOutOfBoundsException if the index of where to add the element is
	 *                                   outside of the list
	 */
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;

		// Checks for duplicate elements
		for (int i = 0; i < size; i++) {
			if (element.equals(current.data)) {
				throw new IllegalArgumentException();
			}
			if (i == size - 1) {
				break;
			}
			current = current.next;
		}
		current = front;
		E ret = null;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		ret = current.data;
		current.data = element;
		return ret;
	}

	/**
	 * Method used to get the element in a list at the given location
	 * 
	 * @param index the index of the element
	 * @return the element at the given index
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}

	@Override
	public int size() {
		return this.size;
	}

	/**
	 * List Node class for the LinkedAbstractList.
	 * 
	 * @author Anton
	 */
	private class ListNode {
		/** Data in the node */
		private E data;

		/** The next node in the list */
		private ListNode next;

		/**
		 * Constructor for ListNode
		 * 
		 * @param data Data to be added to the list
		 */
		public ListNode(E data) {
			this.data = data;
			this.next = null;
		}

		/**
		 * Constructor for the ListNode
		 * 
		 * @param data the data for the list node
		 * @param next The next node in the list
		 */
		public ListNode(E data, LinkedAbstractList<E>.ListNode next) {
			this.data = data;
			this.next = next;
		}

	}
}
