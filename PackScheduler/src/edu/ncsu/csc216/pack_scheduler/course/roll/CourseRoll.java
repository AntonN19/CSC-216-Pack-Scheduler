/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * A CourseRoll object contains a LinkedAbstractList of type Student which contains all of the Students enrolled in a Course.
 * 
 * @author Anton
 * @author Ethan Mancini
 */
public class CourseRoll {
	/** The roll's waitlist of students */
	private LinkedQueue<Student> waitlist;
	
	/** The Course that the roll is associated with */
	private Course course;
	
	/** The rolls enrollment capacity */
	private int enrollmentCap;

	/** The smallest enrollment capacity allowed */
	private static final int MIN_ENROLLMENT = 10;

	/** The largest enrollment capacity allowed */
	private static final int MAX_ENROLLMENT = 250;

	/** The maximum capacity of the waitlist */
	private static final int WAITLIST_CAPACITY = 10;
	
	/** A linked list of students enrolled */
	private LinkedAbstractList<Student> roll;

	/**
	 * Constructor that creates a new empty course roll
	 * 
	 * @param c the Course that the roll is associated with
	 * @param capacity The max capacity of the new student roll
	 * @throws IllegalArgumentException if c is null
	 */
	public CourseRoll(Course c, int capacity) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		this.course = c;
		this.roll = new LinkedAbstractList<Student>(capacity);
		this.waitlist = new LinkedQueue<Student>(WAITLIST_CAPACITY);
		setEnrollmentCap(capacity);
	}

	/**
	 * Method used to get the class enrollment capacity.
	 * 
	 * @return the enrollment capacity
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Method used to set a new enrollment capacity for the class.
	 * 
	 * @param capacity the new enrollment capacity
	 * @throws IllegalArgumentException if the new enrollment capacity is less than
	 *                                  10, greater than 250, or less than the
	 *                                  current number of Students enrolled
	 */
	public void setEnrollmentCap(int capacity) {
		if (capacity > MAX_ENROLLMENT || capacity < MIN_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		if (roll != null && roll.size() > capacity) {
			throw new IllegalArgumentException();
		}
		this.enrollmentCap = capacity;
		if (roll != null) roll.setCapacity(capacity);
	}

	/**
	 * Method used to get the amount of seats still available in the class
	 * 
	 * @return The number of available seats
	 */
	public int getOpenSeats() {
		int ret = enrollmentCap - roll.size();
		return ret;
	}
	
	/**
	 * Method used to add a new student to the end of the course roll or the roll's waitlist
	 * 
	 * @param s The student to be added
	 * @throws IllegalArgumentException if the student could not be added to the roll or its waitlist
	 */
	public void enroll(Student s) {
		try {
			int rollSize = roll.size();
			
			if (rollSize == enrollmentCap) {
				if (waitlist.size() < WAITLIST_CAPACITY) {
					waitlist.enqueue(s);
				}
				else {
					throw new IllegalArgumentException();
				}
				
			}
			else {
				this.roll.add(rollSize, s);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Method used to drop a student from the roll or waitlist.  If a Student is dropped from the roll, the first student
	 * in the waitlist is added to the course roll, if any exists.
	 * 
	 * @param s The student to be dropped
	 * @throws IllegalArgumentException if the student could not be dropped.
	 */
	public void drop(Student s) {
		try {
			for(int i = 0; i < roll.size(); i++) {
				if(s.equals(roll.get(i))) {
					roll.remove(i);
					if(!waitlist.isEmpty()) {
						Student stu = waitlist.dequeue();
						roll.add(roll.size(), stu);
						stu.getSchedule().addCourseToSchedule(course);
					}
				}
			}
		} catch(Exception E) {
			throw new IllegalArgumentException();
		}
		int size = waitlist.size();
		for (int i = 0; i < size; i++) {
			Student student = waitlist.dequeue();
			if (!s.equals(student)) {
				waitlist.enqueue(student);
			}
		}
	}
	
	/**
	 * Method used to determine if the Student can be added to the roll or the waitlist if the roll is full
	 * 
	 * @param s The student to be tested for adding
	 * @return true if student can be added false otherwise
	 */
	public boolean canEnroll(Student s) {
		if (roll.size() < enrollmentCap && !roll.contains(s)) {
			return true;
		}
		if (roll.size() == enrollmentCap) {
			if (roll.contains(s)) {
				return false;
			}
			boolean inWaitlist = false;
			int size = waitlist.size();
			for (int i = 0; i < size; i++) {
				Student student = waitlist.dequeue();
				if (s.equals(student)) {
					inWaitlist = true;
				}
				waitlist.enqueue(student);
			}
			if (!inWaitlist) return true;
			return false;
		}
		return false;
	}
	
	/**
	 * Returns the number of Students in the roll's waitlist
	 * 
	 * @return the number of Students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
