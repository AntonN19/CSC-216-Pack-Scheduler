/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * ArrayQueue class
 * 
 * @author Anton
 * 
 * @param <E> the type of elements contained in queue
 */
public class ArrayQueue<E> implements Queue<E> {

	/** Queue list */
	private ArrayList<E> list;
	
	/** ArrayQueue capacity */
	private int capacity;
	
	
	/**
	 * Constructor for this class
	 * 
	 * @param capacity the queue capacity to be set
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>(capacity);
		this.capacity = capacity;
	}

	/**
	 * Adds provided element to the back of the queue
	 * 
	 * @param element element to be added to the back of queue
	 * @throws IllegalArgumentException if the queue is already full
	 */
	@Override
	public void enqueue(E element) {
		if(list.size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(list.size(), element);
	}

	/**
	 * Removes the element at the front of queue and returns it
	 * 
	 * @return The element that was removed
	 * @throws NoSuchElementException if the queue is empty
	 */
	@Override
	public E dequeue() {
		if(list.size() == 0) {
			throw new NoSuchElementException();
		}
		return list.remove(0);
	}

	/**
	 * Determines if the queue is empty
	 * 
	 * @return true only if the queue is empty
	 */
	@Override
	public boolean isEmpty() {
		if(list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Sets the queues capacity to the provided capacity.
	 * 
	 * @param capacity the new capacity to be assigned to the queue
	 * @throws IllegalArgumentException if the parameter is negative or less than
	 *                                  the number of elements in the queue.
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0) {
			throw new IllegalArgumentException();
		}
		if(list != null && capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Finds the amount of elements in queue.
	 * 
	 * @return size of queue
	 */
	@Override
	public int size() {
		return list.size();
	}

}
