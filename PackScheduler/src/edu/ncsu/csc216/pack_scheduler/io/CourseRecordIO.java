package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 */
public class CourseRecordIO {

    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));
	    SortedList<Course> courses = new SortedList<Course>();
	    while (fileReader.hasNextLine()) {
	        try {
	            Course course = readCourse(fileReader.nextLine());
	            boolean duplicate = false;
	            for (int i = 0; i < courses.size(); i++) {
	                Course c = courses.get(i);
	                if (course.getName().equals(c.getName()) &&
	                        course.getSection().equals(c.getSection())) {
	                    //it's a duplicate
	                    duplicate = true;
	                }
	            }
	            if (!duplicate) {
	                courses.add(course);
	            }
	        } catch (IllegalArgumentException e) {
	            //skip the line
	        }
	    }
	    fileReader.close();
	    return courses;
	}

	/**
     * Creates a course class from each line of the input file that is read from the 
     * read course records class. 
     * @param nextLine is the input line to be read
     * @return a course object read from the line 
     * @throws IllegalArgumentException if elements are not in the file line
     */
    private static Course readCourse(String nextLine) {
		try {
			Scanner lineScan = new Scanner(nextLine);
	    	
			lineScan.useDelimiter(",");
			
			String name = lineScan.next();
			String title = lineScan.next();
			String section = lineScan.next();
			int credits = lineScan.nextInt();
			String instructorId = lineScan.next();
			int enrollmentCap = lineScan.nextInt();
			String meetingDays = lineScan.next();
			
			int startTime = 0;
			int endTime = 0;
			
			if(lineScan.hasNext()) {
				startTime = lineScan.nextInt();
				endTime = lineScan.nextInt();
			}
			
			Course course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
			
			Faculty faculty = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
			if (faculty != null) {
				faculty.getSchedule().addCourseToSchedule(course);
			}
			
			lineScan.close();
			
	    	return course;
		}
    	catch(NoSuchElementException e) {
    		throw new IllegalArgumentException();
    	}
	}

    /**
	 * Writes the given list of Courses to a PrintStream line that creates a new file 
	 * with the SortedList of courses.
	 * @param fileName is the name of the file the user wishes to write the courses to
	 * @param courses is the list of courses the user wishes to print to the outputFile
	 * @throws IOException throws new IOException if there is an issue with writing the outputFile
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
	
		for (int i = 0; i < courses.size(); i++) {
		    fileWriter.println(courses.get(i).toString());
		}
	
		fileWriter.close();
	    
	}
}