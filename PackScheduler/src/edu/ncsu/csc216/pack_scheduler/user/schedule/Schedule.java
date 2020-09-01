package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * This class represents a Student's Schedule of Course Objects.  A Schedule contains a custom ArrayList of Course Objects and provides
 * functionality for adding or removing a Course, resetting the Schedule, getting a formatted 2D String Array containing scheduled Course
 * information, as well as getting and setting the value of the Schedule's title
 * 
 * @author Ethan Mancini
 * @version 3/3/2020
 */
public class Schedule {
	
	/** A custom ArrayList of Courses which contains the scheduled courses */
	private ArrayList<Course> schedule;
	/** The title of the Schedule */
	private String title;
	/** The default title to be applied to a Schedule whose title was not specified or was reset */
	private static final String DEFAULT_TITLE = "My Schedule";
	
	/**
	 * The default Schedule constructor which initializes the schedule and title fields to default values
	 */
	public Schedule() {
		this.schedule = new ArrayList<Course>();
		this.title = DEFAULT_TITLE;
	}
	
	/**
	 * Adds a Course to the Schedule if it is not a duplicate of and does not conflict with any Courses already in the Schedule.  If either
	 * of these conditions are not met, an IllegalArgumentException is thrown containing a message specifying the reason it was thrown.  If
	 * the Course is successfully added to the list, a boolean value of true is returned
	 * 
	 * @param courseToAdd the Course to add to the Schedule if it is not a duplicate of and does not conflict with any Courses in the 
	 * 					  Schedule
	 * @return boolean true if the Course is successfully added to the Schedule
	 */
	public boolean addCourseToSchedule(Course courseToAdd) {
		for (int i = 0; i < this.schedule.size(); i++) {
			Course c = this.schedule.get(i);
			
			if (courseToAdd.getName().equals(c.getName())) {
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			}
			
			try {
				courseToAdd.checkConflict(c);
			}
			catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
			
		}
		
		// Double check correct index
		this.schedule.add(schedule.size(), courseToAdd);
		return true;
	}
	
	/**
	 * Removes a Course from the Schedule if one exists that is recognized as a duplicate of the specified Course via the isDuplicate()
	 * method.  If a matching Course is found, it is removed from the Schedule and a boolean value of true is returned.  If a matching
	 * Course is not found, a boolean value of false is returned
	 * 
	 * @param courseToRemove the Course to try to remove from the Schedule
	 * @return boolean true if the Course specified is found in and removed from the Schedule, false otherwise
	 */
	public boolean removeCourseFromSchedule(Course courseToRemove) {
		if (courseToRemove == null) {
			return false;
		}
		
		for (int i = 0; i < this.schedule.size(); i++) {
			Course c = this.schedule.get(i);
			
			if (courseToRemove.equals(c)) {
				this.schedule.remove(i);
				return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * A method which simply resets the schedule and title fields to their initial values.=
	 */
	public void resetSchedule() {
		this.schedule = new ArrayList<Course>();
		this.title = DEFAULT_TITLE;
	}
	
	/**
	 * A method which returns a 2D Array of String Objects which contain the name, section, title and meeting string of each of the Courses
	 * in the Schedule.  The 2D String Array is constructed in the following form:
	 * 																			   {[name1, section1, title1, meetingString1, openSeats1];
	 * 																				[name2, section2, title2, meetingString2, openSeats2];
	 * 																				[name..n, section..n, title..n, meetingString..n, openSeats..n]}
	 * Where n represents the number of Courses in the Schedule.  Each row is constructed with the Course class' getShortDisplayArray()
	 * method
	 * 
	 * @return String[][] a 2D String Array formatted to contain the name, section, title and meeting string of all Courses in the Schedule
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduledCoursesArray = new String[this.schedule.size()][5];
		
		for (int i = 0; i < this.schedule.size(); i++) {
			Course c = this.schedule.get(i);
			scheduledCoursesArray[i] = c.getShortDisplayArray();
		}
		
		return scheduledCoursesArray;
	}
	
	/**
	 * Calculates the total number of credits the schedule has among all courses.
	 * @return the total number of credits.
	 */
	public int getScheduleCredits() {
		int totalCredits = 0;
		for (int i = 0; i < this.schedule.size(); i++) {
			Course c = this.schedule.get(i);
			totalCredits += c.getCredits();
		}
		return totalCredits;
	}

	/**
	 * Checks whether the schedule can add a course.
	 * @param course the course to check.
	 * @return false if the course is null, already in the schedule, or conflicts with a course in the schedule, true otherwise.
	 */
	public boolean canAdd(Course course) {
		if (course == null) return false;
		for (int i = 0; i < this.schedule.size(); i++) {
			Course c = this.schedule.get(i);
			try {
				course.isDuplicate(c);
			} catch (IllegalArgumentException e) {
				return false;
			}
			try {
				course.checkConflict(c);
			} catch (ConflictException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A standard setter method which sets the value of the Schedules's title field to the value passed to the title parameter
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
	 * A standard getter method which returns the value of the Schedule's title field
	 * 
	 * @return String the title of the Schedule
	 */
	public String getTitle() {
		return this.title;
	}
	
}
