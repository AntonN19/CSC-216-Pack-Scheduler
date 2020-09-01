package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Schedule class and its methods for proper implementation
 * 
 * @author Ethan Mancini
 * @author Anton
 * @version 3/3/2020
 */
public class ScheduleTest {

	/** Course 1 */
	private static final Course COURSE_1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith4", 50, "MW",
			1330, 1445);
	/** Course 2 */
	private static final Course COURSE_2 = new Course("CSC116", "Introduction to Programming - Java", "002", 3, "sesmith5", 50, "TH",
			1230, 1325);
	/** Course 3 */
	private static final Course COURSE_3 = new Course("CSC236", "Introduction to Programming - Java", "002", 3, "sesmith5", 50, "TH",
			1230, 1345);

	/**
	 * Test made to test all of the Schedule class methods
	 */
	@Test
	public void testScheduleMethods() {
		Schedule mySched = new Schedule();
		assertEquals("My Schedule", mySched.getTitle());
		mySched.addCourseToSchedule(COURSE_1);
		mySched.addCourseToSchedule(COURSE_2);
		try {
			mySched.addCourseToSchedule(COURSE_3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}
		String[][] courseArr = new String[2][5];
		courseArr[0] = COURSE_1.getShortDisplayArray();
		courseArr[1] = COURSE_2.getShortDisplayArray();
		for(int i = 0; i < courseArr.length; i++) {
			for(int j = 0; j < courseArr.length; j++) {
				assertEquals(courseArr[i][j], mySched.getScheduledCourses()[i][j]);
			}
		}
		try {
			mySched.addCourseToSchedule(COURSE_1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You are already enrolled in CSC216", e.getMessage());
		}
		assertTrue(mySched.removeCourseFromSchedule(COURSE_1));
		assertFalse(mySched.removeCourseFromSchedule(null));
		mySched.setTitle("Fall 2020 Schedule");
		assertEquals("Fall 2020 Schedule", mySched.getTitle());
		try {
			mySched.setTitle(null);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals("Title cannot be null.", e.getMessage());
		}
		mySched.resetSchedule();
		assertEquals("My Schedule", mySched.getTitle());
		
		// Test getScheduleCredits()
		// Add Courses to Schedule
		assertTrue(mySched.addCourseToSchedule(COURSE_1));
		assertTrue(mySched.addCourseToSchedule(COURSE_2));
		// Assert Schedule.getScheduleCredits() is equal to the total of the Courses' credits
		assertEquals(COURSE_1.getCredits() + COURSE_2.getCredits(), mySched.getScheduleCredits());
		// Reset Schedule
		mySched.resetSchedule();
		
		// Test canAdd()
		// Assert that a new Course with no conflicts can be added to the Schedule
		assertTrue(mySched.canAdd(COURSE_1));
		assertTrue(mySched.canAdd(COURSE_2));
		// Add the two courses
		mySched.addCourseToSchedule(COURSE_1);
		mySched.addCourseToSchedule(COURSE_2);
		// Assert that a passing a null Course returns false
		assertFalse(mySched.canAdd(null));
		// Assert that a duplicate Course cannot be added to the schedule
		assertFalse(mySched.canAdd(COURSE_1));
		// Assert that a Course with a time conflict cannot be added to the Schedule
		assertFalse(mySched.canAdd(COURSE_3));
	}
	
}
