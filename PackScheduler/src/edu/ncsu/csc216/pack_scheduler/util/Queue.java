/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * An interface which describes the base functionality of a custom Queue
 * 
 * @author Anton
 * @param <E> Type of element used in Queue
 */
public interface Queue<E> {

	/**
	 * Adds provided element to the back of the queue
	 * 
	 * @param element element to be added to the back of queue
	 * @throws IllegalArgumentException if the queue is already full
	 */
	void enqueue(E element);

	/**
	 * Removes the element at the front of queue and returns it
	 * 
	 * @return The element that was removed
	 * @throws NoSuchElementException if the queue is empty
	 */
	E dequeue();

	/**
	 * Determines if the queue is empty
	 * 
	 * @return true only if the queue is empty
	 */
	boolean isEmpty();
	
	/**
	 * Finds the amount of elements in queue.
	 * 
	 * @return size of queue
	 */
	int size();

	/**
	 * Sets the queues capacity to the provided capacity.
	 * 
	 * @param capacity the new capacity to be assigned to the queue
	 * @throws IllegalArgumentException if the parameter is negative or less than
	 *                                  the number of elements in the queue.
	 */
	void setCapacity(int capacity);
}
