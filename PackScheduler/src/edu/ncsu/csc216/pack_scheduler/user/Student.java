package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Student code will set data for student, set their firstName, lastName, id,
 * email, password, and credits
 * 
 * @author ZHongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 * @author Ethan Mancini
 */
public class Student extends User implements Comparable<Student> {

	/** Max amount of credits allowed for student */
	private int maxCredits;
	/** Constant of max amount of credits */
	public static final int MAX_CREDITS = 18;
	/** Student's Schedule which contains scheduled Course */
	private Schedule schedule;

	/**
	 * Constructor used to declare a student Object
	 * 
	 * @param firstName  Students first name
	 * @param lastName   Students last name
	 * @param id         students id
	 * @param email      Students email
	 * @param password   Students password
	 * @param maxCredits max amount of credits allowed for student
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		this(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
	}

	/**
	 * Constructor used to declare a student Object
	 * 
	 * @param firstName Students first name
	 * @param lastName  Students last name
	 * @param id        students id
	 * @param email     Students email
	 * @param password  Students password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(MAX_CREDITS);
		schedule = new Schedule();
	}

	/**
	 * Returns the Max Credits the student can take.
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the max credits this student can take. Throws an
	 * IllegalArgumentException if the credits are bellow 3 or greater than 18.
	 * 
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if the maxCredits is greater than 18 or less
	 *                                  than 3
	 */
	public void setMaxCredits(int maxCredits) {
 
		if (maxCredits > MAX_CREDITS || maxCredits < 3) {
			throw new IllegalArgumentException("Invalid max credits");
		}

		this.maxCredits = maxCredits;
	}

	
	/**
	 * Returns a string representation of the student object.
	 * 
	 * @return string representation of the objects variables
	 */
	@Override
	public String toString() {
		return super.getFirstName() + "," + super.getLastName() + "," + super.getId() + "," + super.getEmail() + "," + super.getPassword() + "," + maxCredits;
	}

	/**
	 * Implementation of the compareTo method
	 * @param o the Student object being compared
	 * @return -1, 0, or 1 based on the result of the compareTo method
	 */
	@Override
	public int compareTo(Student o) {
		if(o == null) {
			throw new NullPointerException();
		}
		String objLast = o.getLastName();
		String objFirst = o.getFirstName();
		String objId = o.getId();
		if (objLast.toLowerCase().compareTo(this.getLastName().toLowerCase()) == 0) {
			if(objFirst.toLowerCase().compareTo(this.getFirstName().toLowerCase()) == 0) {
				if(objId.toLowerCase().compareTo(this.getId().toLowerCase()) == 0) {
					return 0;
				} else {
					return -1 * (objId.toLowerCase().compareTo(this.getId().toLowerCase()));
				}
			} else {
				return -1 * (objFirst.toLowerCase().compareTo(this.getFirstName().toLowerCase()));
			}
		} else {
			return -1 * (objLast.toLowerCase().compareTo(this.getLastName().toLowerCase()));
		}
	}

	/**
	 * Hash Code method for the Student Class
	 * 
	 * @return the hashCode value
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Method used to compare this object to a different one
	 * 
	 * @param obj The object being compared to
	 * @return true/false based on whether the objects are identical
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Student))
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}

	/**
	 * A standard getter method which returns the Student's Schedule of Courses
	 * 
	 * @return Schedule the Student's Schedule of Courses
	 */
	public Schedule getSchedule() {
		return this.schedule;
	}
	
	/**
	 * Checks whether the student can add a course to their schedule.
	 * @param c the course to check.
	 * @return false if the course is null, already in the schedule, conflicts with a course in the schedule,
	 * or the student has too many credits to add the course, true otherwise.
	 */
	public boolean canAdd(Course c) {
		if (!schedule.canAdd(c)) {
			return false;
		}
		return schedule.getScheduleCredits() + c.getCredits() <= maxCredits;
	}
	
}
