/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * Create a catalog for courses that available
 * 
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 */
public class CourseCatalog {

	/** The sorted list of courses to represent the directory */
	public SortedList<Course> courseDirectory;

	/**
	 * Constructs the CourseCatalogue object and creates a new empty list of sorted
	 * courses.
	 */
	public CourseCatalog() {
		courseDirectory = new SortedList<Course>();
	}

	/**
	 * Creates an empty SortedList of Courses for the course catalog object
	 */
	public void newCourseCatalog() {
		courseDirectory = new SortedList<Course>();
	}

	/**
	 * Loads the courses from a specified filename given by the user. Throws an
	 * illegal argument exception if the there is a file not found exception.
	 * 
	 * @param fileName the name of the file the user wishes to load courses from
	 * @throws IllegalArgumentException if a file not found exception is found
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			courseDirectory = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds a specified class to the directory and returns true if the the class can
	 * be added and does not have a conflict with the courses in the directory.
	 * 
	 * @param name          the name of the course
	 * @param title         the title of the course
	 * @param section       the section number of the course
	 * @param credits       the credits of the course specified
	 * @param instructorId  the name or id of the instructor attached to the course
	 * @param enrollmentCap the max number of Students able to enroll in the Course
	 * @param meetingDays   the days the course would be meeting
	 * @param startTime     the start time of the course
	 * @param endTime       the end time of the course
	 * @return true if the course can be added to the directory false if the course
	 *         cannot be added to the directory due to a conflict
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		try {
			Course course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);

			for (int i = 0; i < courseDirectory.size(); i++) {
				Course courseCat = courseDirectory.get(i);
				try {
					courseCat.isDuplicate(course);
				} catch (IllegalArgumentException e) {
					if(courseCat.getSection().equals(course.getSection())) {
						return false;
					}
				}
				if (courseCat.equals(course)) {
					return false;
				}
			}		
			courseDirectory.add(course);
			return true;

		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	/**
	 * Removes a specified course from the course catalog.
	 * 
	 * @param name    the name of the course they wish to remove
	 * @param section the section of the course they wish to remove
	 * @return true if the course was removed false if the course was not removed
	 */
	public boolean removeCourseFromCatalog(String name, String section) {

		for (int i = 0; i < courseDirectory.size(); i++) {
			String newName = courseDirectory.get(i).getName();
			String courseSection = courseDirectory.get(i).getSection();
			if (newName.equals(name) && courseSection.equals(section)) {
				courseDirectory.remove(i);
				return true;
			}
		}

		return false;

	}

	/**
	 * Method pulls a specific course from the catalog if the name and section
	 * match. If the name and section do not match then return null.
	 * 
	 * @param name    of the course the user wishes to pull
	 * @param section the section of the course the user wishes to pull
	 * @return course from the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < courseDirectory.size(); i++) {
			String newName = courseDirectory.get(i).getName();
			String courseSection = courseDirectory.get(i).getSection();
			if (newName.equals(name) && courseSection.equals(section)) {
				Course course = courseDirectory.get(i);
				return course;
			}
		}

		return null;
	}

	/**
	 * Returns a 2d array of strings to represent the courses in the catalog using the 
	 * Course.getShortDisplayArray() method.
	 * 
	 * @return a 2d array of courses displaying their name, section, title, meeting string, and number
	 * of open slots
	 */
	public String[][] getCourseCatalog() {
		String[][] courseCatalogue = new String[courseDirectory.size()][5];

		for (int i = 0; i < courseDirectory.size(); i++) {
			courseCatalogue[i] = courseDirectory.get(i).getShortDisplayArray();
		}

		return courseCatalogue;
	}

	/**
	 * Save Course Catalog saves the to a file by writing the catalog to the
	 * filename.
	 * 
	 * @param fileName the name of the file we wish to write to
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			SortedList<Course> course = new SortedList<Course>();
			for (int i = 0; i < courseDirectory.size(); i++) {
				course.add(courseDirectory.get(i));
			}
			CourseRecordIO.writeCourseRecords(fileName, course);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

}