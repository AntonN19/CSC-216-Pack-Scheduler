/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * A LinkedList class which uses recursion instead of loops in all of its
 * methods.
 * 
 * @author Anton
 *
 * @param <E> They type of elements that are contained in this class
 */
public class LinkedListRecursive<E> {

	/** size of the list */
	private int size;

	/** first element in the list */
	private ListNode front;

	/**
	 * LinkedListRecursive Constructor
	 */
	public LinkedListRecursive() {
		this.size = 0;
		this.front = null;
	}

	/**
	 * checks if list is empty
	 * 
	 * @return true only if the list is empty false otherwise
	 */
	public boolean isEmpty() {
		if (this.size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Finds the size of the list.
	 * 
	 * @return the current size of the list
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Adds element to the end of the list.
	 * 
	 * @param element element to be added
	 * @return true if the addition was successful
	 * @throws IllegalArgumentException if the element is already contained in the
	 *                                  list
	 * @throws NullPointerException     if the provided element is null
	 */
	public boolean add(E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		if (size == 0) {
			this.front = new ListNode(element, this.front);
			this.size++;
			return true;
		} else {
			return front.add(element);
		}
	}

	/**
	 * Method used to add element to a specified index in the list.
	 * 
	 * @param idx     the index where the element should be added.
	 * @param element the element to be added.
	 * @throws IllegalArgumentException  if the list already contains the element
	 * @throws IndexOutOfBoundsException if the index is larger than the list size
	 * @throws NullPointerException      if the provided element is null
	 */
	public void add(int idx, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		if (idx > this.size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (idx == 0) {
			this.front = new ListNode(element, this.front);
			this.size++;
		} else {
			front.add(idx, element);
		}
	}

	/**
	 * Public method which gets an element at the provided index
	 * 
	 * @param idx the index provided
	 * @return the element at that index
	 * @throws IndexOutOfBoundsException if the provided index is not inside the
	 *                                   array
	 */
	public E get(int idx) {
		if (idx >= this.size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		return this.front.get(idx);
	}

	/**
	 * Public method which removes the provided element from the list.
	 * 
	 * @param element the element to be removed
	 * @return true if the element has been removed
	 */
	public boolean remove(E element) {
		if (element == null || size == 0) {
			return false;
		}

		if (element.equals(this.front.data)) {
			this.front = this.front.next;
			size--;
			return true;
		}
		return front.remove(element);
	}

	/**
	 * public method used to remove element at a given index
	 * 
	 * @param idx the provided index
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the provided index is not inside the
	 *                                   array
	 */
	public E remove(int idx) {
		if (idx >= this.size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (idx == 0) {
			E ret = front.data;
			front = front.next;
			size--;
			return ret;
		}
		return front.remove(idx);
	}

	/**
	 * Changes the value of an element at the provided index and returns the value
	 * previously held by that element.
	 * 
	 * @param idx     the index of the element to be changed
	 * @param element the new element value
	 * @return the value previously held by this element
	 * @throws IllegalArgumentException  if the list already contains the element
	 * @throws IndexOutOfBoundsException if the index is larger than the list size
	 * @throws NullPointerException      if the provided element is null
	 */
	public E set(int idx, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		if (idx >= this.size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		return front.set(idx, element);
	}

	/**
	 * Public method used to check if the list contains the given element. Transfers
	 * flow of control to the ListNode method if the list is not empty.
	 * 
	 * @param element the element to be checked for
	 * @return true if and only if the list contains the provided element.
	 */
	public boolean contains(E element) {
		if (this.isEmpty()) {
			return false;
		}
		return front.contains(element);
	}

	/**
	 * A ListNode represents one of the elements in a LinkedListRecursive. Each
	 * ListNode stores the data of the element and a reference to the next element
	 * in the list.
	 * 
	 * @author Anton
	 * @author Ethan
	 */
	private class ListNode {

		/** Data contained in the node */
		public E data;

		/** pointer to next node */
		public ListNode next;

		/**
		 * Constructs a ListNode with the specified data and reference to the next
		 * ListNode
		 * 
		 * @param data the data to be stored in the ListNode
		 * @param next a reference to the next ListNode in the list
		 */
		ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * adds element to the end of the list
		 * 
		 * @param element element to be added
		 * @return true if the element was successfully added
		 */
		public boolean add(E element) {
			if (this.next == null) {
				this.next = new ListNode(element, null);
				size++;
				return true;
			}
			return this.next.add(element);
		}

		/**
		 * Private add method which uses recursion to add new element to the list.
		 * 
		 * @param idx     index of where to add element
		 * @param element the element being added
		 */
		public void add(int idx, E element) {
			if (idx == 1) {// next element getting replaced
				if (this.next != null) {
					ListNode nextNext = new ListNode(this.next.data, this.next.next);
					this.next = new ListNode(element, nextNext);
					size++;
				} else {
					this.next = new ListNode(element, null);
					size++;
				}
			} else {
				this.next.add(idx - 1, element);
			}
		}

		/**
		 * Private method which gets data contained at the given index.
		 * 
		 * @param idx the provided index
		 * @return the element at the index
		 */
		public E get(int idx) {
			if (idx == 0) {
				return this.data;
			}
			return this.next.get(idx - 1);
		}

		/**
		 * private method which helps remove element at provided index. Iterates through
		 * list by using recursion.
		 * 
		 * @param idx the provided index
		 * @return element found at that index
		 */
		public E remove(int idx) {
			if (idx == 1) {
				E ret = next.data;
				this.next = this.next.next;
				size--;
				return ret;
			}
			return this.next.remove(idx - 1);
		}

		/**
		 * private method which removes the provided element from the list
		 * 
		 * @param element the provided element
		 * @return true if the element has been removed
		 */
		public boolean remove(E element) {
			if (this.next.data.equals(element)) {
				this.next = this.next.next;
				size--;
				return true;
			}
			return this.next.remove(element);
		}

		/**
		 * Private method which sets the element at the given index to a provided
		 * element and returns the value previously held by the element at this index.
		 * 
		 * @param idx     the where the element is at
		 * @param element new element value
		 * @return true if the value was successfully changed
		 */
		public E set(int idx, E element) {
			if (idx == 0) {
				E ret = this.data;
				this.data = element;
				return ret;
			}
			return this.next.set(idx - 1, element);
		}

		/**
		 * Private method used to check if the list contains the given element.
		 * 
		 * @param element the element to be checked for
		 * @return true if and only if the list contains the provided element.
		 */
		public boolean contains(E element) {
			if (this.data.equals(element)) {
				return true;
			} else if (this.next == null) {
				return false;
			}
			return this.next.contains(element);
		}
	}
}
