/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * LinkedStack class
 * 
 * @param <E> the type of Objects stored in the LinkedStack
 * @author Anton
 */
public class LinkedStack<E> implements Stack<E> {

	/** Stack list */
	private LinkedAbstractList<E> list;
	
	/**
	 * Constructor for the LinkedStack class
	 * 
	 * @param capacity of the stack
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}

	/**
	 * Adds element to the top of the stack
	 * 
	 * @param element element to be added
	 * @throws IllegalArgumentException if there is no more room in the stack for
	 *                                  the given element
	 */
	@Override
	public void push(E element) {
		list.add(0, element);
	}

	/**
	 * Removes and returns the element at the top of the stack.
	 * 
	 * @return the element removed from the stack
	 * @throws EmptyStackException if the stack is already empty
	 */
	@Override
	public E pop() {
		if(list.size() == 0) {
			throw new EmptyStackException();
		}
		return list.remove(0);
	}

	/**
	 * Determines if the stack is empty
	 * 
	 * @return true only if the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		if(list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Determines the stack size
	 * 
	 * @return the number of elements in the stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the stacks capacity
	 * 
	 * @param capacity the capacity of the stack to be set
	 * @throws IllegalArgumentException if the provided capacity is negative or less
	 *                                  than the number of elements already in the
	 *                                  stack.
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}
}
