package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * A Faculty represents a User in the PackScheduler system and has a similar implementation to the Student class.
 * 
 * @author Ethan Mancini
 */
public class Faculty extends User {
	
	/** The Faculty's maximum amount of courses */
	private int maxCourses;
	/** The minimum number of courses which a Faculty can teach in a semester */
	private static final int MIN_COURSES = 1;
	/** The maximum number of courses which a Faculty can teach in a semester */
	private static final int MAX_COURSES = 3;
	/** The Faculty's course schedule */
	private FacultySchedule schedule;
	
	/**
	 * Constructs a new Faculty by calling the User constructor then initializing maxCourses to the value passed to the constructor and schedule by passing
	 * the id parameter to the FacultySchedule constructor
	 * 
	 * @param firstName Faculty first name
	 * @param lastName Faculty last name
	 * @param id Faculty id
	 * @param email Faculty email
	 * @param password Faculty password
	 * @param maxCourses Faculty max courses
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		this.setMaxCourses(maxCourses);
		this.schedule = new FacultySchedule(id);
	}
	
	/**
	 * A standard setter method which sets the value of maxCourses to the value passed to the parameter
	 * 
	 * @param maxCourses the value to set maxCourses to
	 * @throws IllegalArgumentException if the value passed to the parameter is not within the bounds defined by MIN_COURSES and MAX_COURSES
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses > MAX_COURSES || maxCourses < MIN_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		
		this.maxCourses = maxCourses;
	}
	
	/**
	 * A standard getter method which returns the value stored in maxCourses
	 * 
	 * @return the value of maxCourses
	 */
	public int getMaxCourses() {
		return this.maxCourses;
	}
	
	/**
	 * A standard getter method to access the Faculty's FacultySchedule
	 * 
	 * @return the Faculty's schedule
	 */
	public FacultySchedule getSchedule() {
		return this.schedule;
	}
	
	/**
	 * Returns a boolean value representing whether or not the number of scheduled courses is greater than the Faculty's maxCourses
	 * 
	 * @return true if the Faculty's number of scheduled courses is greater than the value stored in its maxCourses field; false otherwise
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}
	
	/**
	 * Hash code method for the Faculty class which first calls User.hashCode() then multiplies that result by 31 and adds maxCourses
	 * 
	 * @return the hash code of the Faculty
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}
	
	/**
	 * Compares this Faculty with an Object passed to the parameter and returns a boolean which represents this Faculty's equality to it
	 * 
	 * @param o the Object to test for equality with this Faculty
	 * @return true or false if the Object passed to the parameter is or is not equal to this Faculty
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!super.equals(o))
			return false;
		if (!(o instanceof Faculty))
			return false;
		Faculty other = (Faculty) o;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}
	
	/**
	 * Returns a string representation of the Faculty object
	 * 
	 * @return String representations of each of the Faculty's fields, separated by commas
	 */
	@Override
	public String toString() {
		return super.getFirstName() + "," + super.getLastName() + "," + super.getId() + "," + super.getEmail() + "," + super.getPassword() + "," + maxCourses;
	}
	
}
