/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface used to show if there is a conflict with entering two courses or events during the same time
 * frame into the schedule.
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 *
 */
public interface Conflict {
	/**  
	 * Creates a class that will check if the activity being entered is in conflict with the other activities 
	 * in the activity schedule list.
	 * @param possibleConflictingActivity the activity that is meant to be checked with the schedule
	 * @throws ConflictException if the activity is in conflict with another activity.
	 */		
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
