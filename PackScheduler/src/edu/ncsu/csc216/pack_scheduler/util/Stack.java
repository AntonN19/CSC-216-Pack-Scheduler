/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * An interface which describes the base functionality of a custom Stack
 * 
 * @author Anton
 * @param <E> type of element used in the Stack
 *
 */
public interface Stack<E> {

	/**
	 * Adds element to the top of the stack
	 * 
	 * @param element element to be added
	 * @throws IllegalArgumentException if there is no more room in the stack for
	 *                                  the given element
	 */
	void push(E element);

	/**
	 * Removes and returns the element at the top of the stack.
	 * 
	 * @return the element removed from the stack
	 * @throws EmptyStackException if the stack is already empty
	 */
	E pop();

	/**
	 * Determines if the stack is empty
	 * 
	 * @return true only if the stack is empty
	 */
	boolean isEmpty();

	/**
	 * Determines the stack size
	 * 
	 * @return the number of elements in the stack
	 */
	int size();

	/**
	 * Sets the stacks capacity
	 * 
	 * @param capacity the capacity of the stack to be set
	 * @throws IllegalArgumentException if the provided capacity is negative or less
	 *                                  than the number of elements already in the
	 *                                  stack.
	 */
	void setCapacity(int capacity);
}
