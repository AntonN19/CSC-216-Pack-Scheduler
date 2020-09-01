package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A custom implementation of ArrayList that prevents null and duplicate entries.
 * 
 * @author Art Schell
 *
 * @param <E> The generic type of the array.
 */
public class ArrayList<E> extends AbstractList<E> {
	/** The internal list object. */
	private E[] list;
	/** The size of the internal list object. */
	private int internalSize;
	/** The size of the stored array, and how much of the internal list object is used. */
	private int size;
	/** The default starting internal size. */
	private static final int STARTING_INTERNAL_SIZE = 10;

	/**
	 * Creates a new array list with a customized internal size.
	 * 
	 * @param internalSize the internal size of the list.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList(int internalSize) {
		this.internalSize = internalSize;
		this.list = (E[])(new Object[internalSize]);
		this.size = 0;
	}
	
	/**
	 * Creates a new array list with the default internal size.
	 */
	public ArrayList() {
		this(STARTING_INTERNAL_SIZE);
	}

	/**
	 * Grows the array to have twice the internal size it currently has.
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		internalSize *= 2;
		E[] newlist = (E[])(new Object[internalSize]);
		for (int i = 0; i < size; i++) {
			newlist[i] = list[i];
			list[i] = null;
		}
		list = newlist;
	}
	
	/**
	 * Adds an element at a given index.
	 * 
	 * @param idx the index to add the element at. index is between 0 and size
	 * @param elem the element to add. Cannot be null or identical to an element already in the list.
	 */
	@Override
	public void add(int idx, E elem) {
		if (elem == null) {
			throw new NullPointerException("Cannot add null to list");
		}
		for (int i = 0; i < size; i++) {
			E e = list[i];
			if (e.equals(elem)) {
				throw new IllegalArgumentException("This element already exists in the list");
			}
		}
		if (idx >= 0 && idx <= size) {
			if (size + 1 > internalSize) {
				growArray();
			}
			for (int i = size; i > idx; i--) {
				list[i] = list[i - 1];
			}
			list[idx] = elem;
			size++;
		} else {
			throw new IndexOutOfBoundsException("Cannot add element at index " + idx);
		}
	}
	
	/**
	 * Removes an element at a given index and returns it.
	 * 
	 * @param idx the index to of the element to remove. index between 0 and size
	 * @return the removed element.
	 */
	@Override
	public E remove(int idx) {
		if (idx >= 0 && idx < size) {
			size--;
			E ret = list[idx];
			for (int i = idx; i < size; i++) {
				list[i] = list[i + 1];
			}
			list[size] = null;
			return ret;
		} else {
			throw new IndexOutOfBoundsException("Cannot remove element at index " + idx);
		}
	}
	
	/**
	 * Sets an element at a given index.
	 * 
	 * @param idx the index to set to the element. index has to be between 0 and size.
	 * @param elem the element to set. Cannot be null or identical to an element already in the list.
	 * @return the previous value of the element at that index.
	 */
	@Override
	public E set(int idx, E elem) {
		if (elem == null) {
			throw new NullPointerException("Cannot add null to list");
		}
		for (int i = 0; i < size; i++) {
			E e = list[i];
			if (i != idx && e.equals(elem)) {
				throw new IllegalArgumentException("This element already exists in the list");
			}
		}
		if (idx >= 0 && idx < size) {
			E ret = list[idx];
			list[idx] = elem;
			return ret;
		} else {
			throw new IndexOutOfBoundsException("Cannot set element at index " + idx);
		}
	}
	
	/**
	 * Gets an element at a given index.
	 * 
	 * @param idx the index to set to the element. Index has to be between 0 and size.
	 * @return the value of the element at that index.
	 */
	@Override
	public E get(int idx) {
		if (idx >= 0 && idx < size) {
			return list[idx];
		} else {
			throw new IndexOutOfBoundsException("Cannot get element at index " + idx);
		}
	}
	
	/**
	 * Gets the size of the array.
	 * @return the size.
	 */
	@Override
	public int size() {
		return size;
	}
}
