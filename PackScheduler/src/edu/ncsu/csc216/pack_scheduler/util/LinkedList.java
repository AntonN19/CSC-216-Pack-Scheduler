package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A custom linked list which contains elements of type E specified upon construction of the list.
 * 
 * @param <E> the type of Object stored in the LinkedList
 * @author Ethan Mancini
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	/** The node at the front of the list */
	private ListNode front;
	/** The node at the back of the list */
	private ListNode back;
	/** The size of the list */
	private int size;
	
	/**
	 * Creates a new, empty LinkedList and initializes its fields
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}
	
	/**
	 * Returns a new ListIterator object which is located between the ListNodes located at index - 1 and index.
	 * 
	 * @param index the index at which the returned ListIterator will be located just before
	 * @return a new ListIterator object which is located between the ListNodes located at index - 1 and index
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}

	/**
	 * Adds a new element to this list at the specified index.
	 * 
	 * @param index the index where the new element should be added to the list
	 * @param element the new element to be added to the list
	 * @throws IllegalArgumentException if the list already contains the specified element
	 */
	@Override
	public void add(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		
		super.add(index, element);
	}

	/**
	 * Updates the element stored at the specified index in the list to the value passed to the method.
	 * 
	 * @param index the index of the element to be updated
	 * @param element the new element to be stored at the specified index
	 * @return the previous element stored at the specified index
	 * @throws IllegalArgumentException if the list already contains the specified element
	 */
	@Override
	public E set(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		
		return super.set(index, element);
	}

	/**
	 * Returns the size of the list.
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * A ListNode contains an element in a LinkedList as well as references to the next and previous nodes in the list
	 * 
	 * @author Ethan Mancini
	 */
	private class ListNode {
		
		/** The data in this node */
		public E data;
		/** The next node in the list */
		public ListNode next;
		/** The previous node in the list */
		public ListNode prev;
		
		/**
		 * Creates a new ListNode with null references to the next and previous nodes in the list
		 * 
		 * @param the value to be stored in the new ListNode
		 */
		public ListNode(E data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
		
		/**
		 * Creates a new ListNode with references to the next and previous nodes in the list
		 * 
		 * @param data the value to be stored in this node
		 * @param prev a reference to the previous node in the list
		 * @param next a reference to the next node in the list
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
		
	}
	
	/**
	 * A custom ListIterator for LinkedList
	 * 
	 * @author Ethan Mancini
	 */
	private class LinkedListIterator implements ListIterator<E> {
		
		/** The previous node in the list */
		private ListNode previous;
		/** The next node in the list */
		private ListNode next;
		/** The index of the previous node in the list */
		private int previousIndex;
		/** The index of the next node in the list */
		private int nextIndex;
		/** The last node retrieved by the next() or previous() methods */
		private ListNode lastRetrieved;
		
		/**
		 * Creates a new LinkedListIterator that is located between the list nodes found at index - 1 and index.
		 * 
		 * @param index the index at which the newly constructed LinkedListIterator will be placed just before
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			
			next = front;
			for (int i = 0; i < index; i++) {
				next = next.next;
			}
			next = next.next;
			previous = next.prev;
			previousIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
		}
		
		@Override
		public boolean hasNext() {
			return next.data != null;
		}

		@Override
		public E next() {
			if (next.data == null) {
				throw new NoSuchElementException();
			}
			
			E data = next.data;
			lastRetrieved = next;
			previous = next;
			next = next.next;
			previousIndex++;
			nextIndex++;
			return data;
		}

		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}

		@Override
		public E previous() {
			if (previous.data == null) {
				throw new NoSuchElementException();
			}
			
			E data = previous.data;
			lastRetrieved = previous;
			next = previous;
			previous = previous.prev;
			previousIndex--;
			nextIndex--;
			return data;
		}

		@Override
		public int nextIndex() {
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * {@inheritDoc}
		 * <br><br>
		 * This implementation also decrements size
		 * 
		 * @throws IllegalStateException if lastRetrieved contains a null reference
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			lastRetrieved.prev.next = lastRetrieved.next;
			lastRetrieved.next.prev = lastRetrieved.prev;
			size--;
		}
		
		/**
		 * {@inheritDoc}
		 * @throws NullPointerException if a null reference is passed to the parameter
		 * @throws IllegalStateException if lastRetrieved contains a null reference
		 */
		@Override
		public void set(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			lastRetrieved.data = e;
		}

		/**
		 * {@inheritDoc}
		 * <br><br>
		 * This implementation also decrements size
		 * 
		 * @throws NullPointerException if a null reference is passed to the parameter
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			if (size == 0) {
				front.next = new ListNode(e, front, back);
				back.prev = front.next;
			}
			else {
				ListNode newNode = new ListNode(e, previous, next);
				previous.next = newNode;
				next.prev = newNode;
			}
			
			size++;
			lastRetrieved = null;
		}
		
	}
	
}
