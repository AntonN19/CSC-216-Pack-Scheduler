/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test the CourseCatalog
 * 
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 *
 */
public class CourseCatalogTest {
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Programming Concepts - Java";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 4;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course max capacity */
	private static final int ENROLLMENT_CAP = 50;
	/** Course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	/** File Name */
	private static final String VALID_FILE = "test-files/actual_course_records.txt";
	/** Invalid File Name */
	private static final String INVALID_FILE = "test-files/actual_course.txt";

	/**
	 * Test method for CourseCatalog.
	 */
	@Test
	public void testCourseCatalog() {
		SortedList<Course> list = new SortedList<Course>();
		assertEquals(0, list.size());

	}

	/**
	 * Test method for newCourseCatalog.
	 */
	@Test
	public void testNewCourseCatalog() {
		SortedList<Course> list = new SortedList<Course>();
		assertEquals(0, list.size());
	}

	/**
	 * Test method for loadCoursesFromFile
	 */
	@Test
	public void testLoadCoursesFromFile() {
		CourseCatalog catalog = new CourseCatalog();
		try {
			catalog.loadCoursesFromFile(INVALID_FILE);
			fail();
		} catch (IllegalArgumentException e) {
			// exception thrown passes test
		}
		try {
			catalog.loadCoursesFromFile(VALID_FILE);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Test method for addCourseToCatalog
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog catalog = new CourseCatalog();
		try {
			catalog.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME,
					END_TIME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(c.getName(), catalog.getCourseCatalog()[0][0]);
		assertEquals(c.getSection(), catalog.getCourseCatalog()[0][1]);
		assertEquals(c.getTitle(), catalog.getCourseCatalog()[0][2]);
		assertEquals(c.getMeetingString(), catalog.getCourseCatalog()[0][3]);
	}

	/**
	 * Test for the addCourseToCatalog() and removeCourseFromCatalog() methods
	 */
	@Test
	public void testEditCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		assertTrue(cc.addCourseToCatalog("CSC116", "Intro to Computer Science- Java", "002", 3, "sheckman", ENROLLMENT_CAP, "MWF", 1100,
				1300));
		assertTrue(
				cc.addCourseToCatalog("CSC216", "Programming Concepts- Java", "005", 4, "sesmith5", ENROLLMENT_CAP, "TH", 1330, 1420));
		assertFalse(cc.addCourseToCatalog("CSC116", "Intro to Computer Science- Java", "002", 3, "sheckman", ENROLLMENT_CAP, "MWF",
				1100, 1300));
		assertFalse(
				cc.addCourseToCatalog("CSC216", "Programming Concepts- Java", "005", 4, "sesmith5", ENROLLMENT_CAP, "TH", 1330, 1420));
		assertTrue(cc.removeCourseFromCatalog("CSC116", "002"));
		assertTrue(cc.removeCourseFromCatalog("CSC216", "005"));
		assertFalse(cc.removeCourseFromCatalog("MA141", "008"));
	}

	/**
	 * Test for the getCourseFromCatalog() method
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.addCourseToCatalog("CSC116", "Intro to Computer Science- Java", "002", 3, "sheckman", ENROLLMENT_CAP, "MWF", 1100, 1300);
		cc.addCourseToCatalog("CSC216", "Programming Concepts- Java", "005", 4, "sesmith5", ENROLLMENT_CAP, "TH", 1330, 1420);
		Course expCourse1 = new Course("CSC116", "Intro to Computer Science- Java", "002", 3, "sheckman", ENROLLMENT_CAP, "MWF", 1100,
				1300);
		Course expCourse2 = new Course("CSC216", "Programming Concepts- Java", "005", 4, "sesmith5", ENROLLMENT_CAP, "TH", 1330, 1420);
		assertEquals(expCourse1, cc.getCourseFromCatalog("CSC116", "002"));
		assertEquals(expCourse2, cc.getCourseFromCatalog("CSC216", "005"));
		assertEquals(null, cc.getCourseFromCatalog("MA242", "003"));
	}

	/**
	 * Test for the getCourseCatalog() method
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.addCourseToCatalog("CSC116", "Intro to Computer Science- Java", "002", 3, "sheckman", ENROLLMENT_CAP, "MWF", 1100, 1300);
		cc.addCourseToCatalog("CSC216", "Programming Concepts- Java", "005", 4, "sesmith5", ENROLLMENT_CAP, "TH", 1330, 1420);
		String[][] expStr = new String[2][4];
		expStr[0][0] = "CSC116";
		expStr[0][1] = "002";
		expStr[0][2] = "Intro to Computer Science- Java";
		expStr[0][3] = "MWF 11:00AM-1:00PM";
		expStr[1][0] = "CSC216";
		expStr[1][1] = "005";
		expStr[1][2] = "Programming Concepts- Java";
		expStr[1][3] = "TH 1:30PM-2:20PM";
		String[][] actualStr = cc.getCourseCatalog();
		for (int i = 0; i < expStr.length; i++) {
			for (int j = 0; j < expStr[i].length; j++) {
				assertEquals(expStr[i][j], actualStr[i][j]);
			}
		}
	}

	/**
	 * Test for the saveCourseCatalog() method
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.addCourseToCatalog("CSC116", "Intro to Computer Science- Java", "002", 3, "sheckman", ENROLLMENT_CAP, "MWF", 1100, 1300);
		cc.addCourseToCatalog("CSC216", "Programming Concepts- Java", "005", 4, "sesmith5", ENROLLMENT_CAP, "TH", 1330, 1420);
		cc.saveCourseCatalog("test-files/actualCatalog.txt");
		try {
			Scanner expScanner = new Scanner(new File("test-files/expectedCatalog.txt"));
			Scanner actScanner = new Scanner(new File("test-files/actualCatalog.txt"));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}
