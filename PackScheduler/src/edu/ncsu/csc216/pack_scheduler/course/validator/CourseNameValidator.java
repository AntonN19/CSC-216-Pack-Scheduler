package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * The CourseNameValidator class implements the Finite State Machine design pattern in order to check whether a course name is valid based on the
 * following conditions:
 * 		<ul>
 * 			<li> A course name must begin with one to four letters. </li>
 * 			<li> A course name must follow the one to four letters with <u>exactly</u> three numbers. </li>
 * 			<li> A course name can, but is not required to, have a single letter at the end, or a suffix. </li>
 * 		</ul>
 * 
 * @author Ethan Mancini
 * @version February 25, 2020
 */
public class CourseNameValidator {
	
	/** A concrete instantiation of the InitialState class, part of the CourseNameValidator FSM pattern */
	private final State stateInitial = new InitialState();
	/** A concrete instantiation of the LetterState class, part of the CourseNameValidator FSM pattern */
	private final State stateLetter = new LetterState();
	/** A concrete instantiation of the NumberState class, part of the CourseNameValidator FSM pattern */
	private final State stateNumber = new NumberState();
	/** A concrete instantiation of the SuffixState class, part of the CourseNameValidator FSM pattern */
	private final State stateSuffix = new SuffixState();
	/** The current state of the CourseNameValidator FSM */
	private State currentState;
	
	/** The number of letters that the FSM has encountered while indexing through the course name to validate */
	private int letterCount;
	/** The number of digits that the FSM has encountered while indexing through the course name to validate */
	private int digitCount;
	
	/**
	 * A default constructor for the CourseNameValidator class which simply instantiates its currentState, letterCount and digitCount fields
	 * to default values
	 */
	public CourseNameValidator() {
		currentState = stateInitial;
		// validEndState = false;
		letterCount = 0;
		digitCount = 0;
	}
	
	/**
	 * This method drives the Finite State Machine code pattern implemented in the CourseNameValidator class.  It indexes through the course name
	 * passed to the name parameter, checking whether the character at the current index is a letter, digit or something else, and calls an 
	 * appropriate method implemented in the currentState field's current concrete State type.  Each of the concrete classes which extend the
	 * abstract State class implement the inherited onLetter() and onDigit() methods according to the transitions that the Finite State Machine
	 * is intended to make.  If the name parameter is an invalid course name, isValid() either returns false or throws an
	 * InvalidTransitionException 
	 * 
	 * @param name the course name to be evaluated for validity
	 * @return boolean, true if the course name is valid or false otherwise
	 * @throws InvalidTransitionException if the state FSM does not support an operation meaning that the course name is invalid
	 */
	public boolean isValid(String name) throws InvalidTransitionException {
		currentState = stateInitial;
		letterCount = 0;
		digitCount = 0;
		
		if (name == null) {
			return false;
		}
		
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			
			if (Character.isLetter(c)) {
				currentState.onLetter();
			}
			else if (Character.isDigit(c)){
				currentState.onDigit();
			}
			else {
				currentState.onOther();
			}
			
		}
		
		return digitCount == NumberState.COURSE_NUMBER_LENGTH;
	}
	
	/**
	 * The abstract State class defines two abstract methods for its derived classes; InitialState, LetterState, NumberState and SuffixState.
	 * These two abstract methods onLetter() and onDigit() require that any derived classes must implement logic to handle the current type of
	 * character that the isValid() method is concerned with.  The State class also defines a concrete method onOther() which throws an
	 * InvalidTransitionException when called due to the presence of an invalid character in the course name that is being validated by the
	 * CourseNameValidator Finite State Machine.
	 * 
	 * @author Ethan Mancini
	 * @version February 25, 2020
	 */
	private abstract class State {
		
		/**
		 * An abstract method which requires that derived classes implement functionality to handle a transition in the FSM's State due to the
		 * presence of a letter in the course name being validated
		 */
		public abstract void onLetter()  throws InvalidTransitionException;
		
		/**
		 * An abstract method which requires that derived classes implement functionality to handle a transition in the FSM's State due to the
		 * presence of a digit in the course name being validated
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * A concrete method to be called in the case of a character other than a letter or digit appearing in the course name being validated
		 * by the FSM
		 * 
		 * @throws InvalidTransitionException due to the presence of an invalid character in the course name that is being validated by the FSM
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
		
	}
	
	/**
	 * A child class of the abstract State class which handles the first character in a course name when it is being validated by the FSM
	 * 
	 * @author Ethan Mancini
	 * @version February 25, 2020
	 */
	private class InitialState extends State {
		
		/**
		 * An override of the parent State class' abstract onLetter() method which implements behavior based on the FSM's state being the 
		 * InitialState stateInitial.  Since a course name must begin with a letter, and this method is to be called when the isValid() indexing 
		 * loop encounters a letter at the first index of the course name being validated, the letterCount field is incremented and the FSM's 
		 * State is updated to the LetterState, stateLetter.
		 */
		@Override
		public void onLetter() {
			letterCount++;
			currentState = stateLetter;
		}

		/**
		 * An override of the parent State class' abstract onDigit() method which implements behavior based on the FSM's state being the 
		 * InitialState stateInitial.  Since a course name must begin with a letter, and this method is to be called when the isValid() indexing 
		 * loop encounters a digit at the first index of the course name being validated, an InvalidTransitionException is thrown, meaning the
		 * course name is invalid
		 * 
		 * @throws InvalidTransitionException when the isValid() method encounters a digit at the first index in the course name being validated
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
		
	}
	
	/**
	 * A child class of the abstract State class which handles the characters succeeding the first character of, and preceding the three required 
	 * digits in a course name when it is being validated by the FSM
	 *  
	 * @author Ethan Mancini
	 * @version February 25, 2020
	 */
	private class LetterState extends State {
		
		/** A constant which defines the maximum number of letters that are allowed to be present at the beginning of a valid course name */
		private static final int MAX_PREFIX_LETTERS = 4;
		
		/**
		 * An override of the parent State class' abstract onLetter() method which implements behavior based on the FSM's state being the 
		 * LetterState stateLetter.  Since a course name must begin with no more than the amount of letters defined by the MAX_PREFIX_LETTERS
		 * field, and this method is to be called when the isValid() indexing loop encounters a letter when indexing through the course name being
		 * validated, there are two possible outcomes of calling this implementation of the onLetter() method.  The first is that in a case where
		 * the FSM has already encountered the maximum number of prefix letters, meaning that another letter in the course name invalidates that
		 * name and an InvalidTransitionException is thrown.  The second outcome is that when the isValid() indexing loop encounters a letter and 
		 * the maximum number of prefix letters is not yet being exceeded, the course name being validated is still meeting the requirements for
		 * a valid course name and the letterCount field of the containing class is therefore incremented.
		 * 
		 * @throws InvalidTransitionException if the maximum number of prefix letters has already been reached prior to this method being called
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount == MAX_PREFIX_LETTERS) {
				throw new InvalidTransitionException("Course name cannot start with more than " + MAX_PREFIX_LETTERS + " letters.");
			}
			else {
				letterCount++;
			}
			
		}

		/**
		 * An override of the parent State class' abstract onDigit() method which implements behavior based on the FSM's state being the 
		 * InitialState stateInitial.  Since a course name must begin with a letter, and this method is to be called when the isValid() indexing 
		 * loop encounters a digit at an index other than the first in the course name being validated, the digitCount field of the containing
		 * class is incremented and its State is updated to the NumberState, stateNumber.
		 */
		@Override
		public void onDigit() {
			digitCount++;
			currentState = stateNumber;
		}
		
	}
	
	/**
	 * A child class of the abstract State class which handles the characters succeeding the prefix of, and preceding the optional suffix of a 
	 * valid course name.  A valid course name must have <u>exactly</u> three digits, the count of which is stored in the containing class'
	 * digitCount field
	 * 
	 * @author Ethan Mancini
	 * @version February 25, 2020
	 */
	private class NumberState extends State {

		/** A constant which defines the exact length of a valid course number part of a valid course name */
		private static final int COURSE_NUMBER_LENGTH = 3;
		
		/**
		 * An override of the parent State class' abstract onLetter() method which implements behavior based on the FSM's state being the 
		 * NumberState stateNumber.  Since a course name must contain <u>exactly</u> three numbers, if the FSM encounters a letter when
		 * the course name being validated does not have three digits, an InvalidTransitionException is thrown.  If the FSM has already
		 * encountered the three required digits and then a letter is encountered, the letter fulfills the optional suffix letter requirement
		 * of a valid course name
		 * 
		 * @throws InvalidOperationException if the FSM encounters a letter before it has encountered the three consecutive digits required
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				currentState = stateSuffix;
			}
			else {
				throw new InvalidTransitionException("Course name must have " + COURSE_NUMBER_LENGTH + " digits.");
			}
			
		}

		/**
		 * An override of the parent State class' abstract onDigit() method which implements behavior based on the FSM's state being the 
		 * NumberState stateNumber.  Since a course name must contain exactly three digits succeeding its prefix and preceding its optional
		 * suffix, then there are two possible outcomes when a digit is encountered in the course name being validated.  If the containing class'
		 * digitCount field is already equal to the COURSE_NUMBER_LENGTH constant field, then adding another digit would exceed the required
		 * number of digits and, therefore, an InvalidTransitionException is thrown.  Otherwise, another digit in the course name is allowed and
		 * the digitCount is incremented.
		 * 
		 * @throws InvalidTransitionException if the number of digits in the course name being validated exceeds the COURSE_NUMBER_LENGTH constant
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name can only have " + COURSE_NUMBER_LENGTH + " digits.");
			}
			else {
				digitCount++;
			}
			
		}
		
	}
	
	/**
	 * A child class of the abstract State class which handles the presence of a suffix character in the course name being validated.  A valid 
	 * course name cannot have any characters following the suffix and, therefore, an InvalidTransitionException is thrown if the FSM encounters
	 * <u>any</u> character following a valid suffix
	 * 
	 * @author Ethan Mancini
	 * @version February 25, 2020
	 */
	private class SuffixState extends State {
		
		/**
		 * An override of the parent State class' abstract onLetter() method which throws an InvalidTransitionException due to the presence of a
		 * character after the last allowable character in a valid course name
		 * 
		 * @throws InvalidTransitionException since the FSM encountered a character after the last allowable character in a valid course name
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");			
		}

		/**
		 * An override of the parent State class' abstract onDigit() method which throws an InvalidTransitionException due to the presence of a
		 * character after the last allowable character in a valid course name
		 * 
		 * @throws InvalidTransitionException since the FSM encountered a character after the last allowable character in a valid course name
		 */
		@Override
		public void onDigit() throws InvalidTransitionException{
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");			
		}
		
	}
	
}
