/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Creates a checked exception for a conflict exception in the conflict program that will provide 
 * the message "Schedule Conflict" if a conflict is found in activities the user wishes to add to
 * their schedule. 
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor that initializes the conflict exception. Allows the user to 
	 * add their own message to the exception
	 * @param message the message that is to be shown
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Constructors that initializes Conflict and shows the default message of
	 * "Schedule conflict"
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}
}
