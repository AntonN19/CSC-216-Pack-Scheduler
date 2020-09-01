package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * New Exception made to be thrown when an invalid transition occurs.
 *  
 * @author Anton
 */
public class InvalidTransitionException extends Exception {
	
	/** Id used for object serialization */
	private static final long serialVersionUID = 1L;

	/** Default message for exception */
	private static final String DEFAULT_MESSAGE = "Invalid FSM Transition.";

	/**
	 * Paramaterized constructor used to attach the error message to the
	 * InvalidTransitionException
	 * 
	 * @param message The message t be attached to this exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Parameterless InvalidTransitionException constructor, uses the default message for the
	 * exception
	 */
	public InvalidTransitionException() {
		this(DEFAULT_MESSAGE);
	}
}
