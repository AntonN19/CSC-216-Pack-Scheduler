/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Test for FacultyDirectory class
 * 
 * @author Sarah Heckman
 * @author Anton
 */
public class FacultyDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/nosuchfile.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "password";
	/** Test max credits */
	private static final int MAX_CREDITS = 3;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset faculty_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests facultyDirectory().
	 */
	@Test
	public void testfacultyDirectory() {
		// Test that the facultyDirectory is initialized to an empty list
		FacultyDirectory sd = new FacultyDirectory();
		assertFalse(sd.removeFaculty("sesmith5"));
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests invalid file
	 */
	@Test
	public void testLoadfacultysFromInvalidFile() {
		FacultyDirectory sd = new FacultyDirectory();
		try {
			sd.loadFacultyFromFile(invalidTestFile);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to read file test-files/nosuchfile.txt", e.getMessage());
		}
	}

	/**
	 * Tests facultyDirectory.testNewfacultyDirectory().
	 */
	@Test
	public void testNewfacultyDirectory() {
		// Test that if there are Faculties in the directory, they
		// are removed after calling newfacultyDirectory().
		FacultyDirectory sd = new FacultyDirectory();

		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);

		sd.newFacultyDirectory();
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests facultyDirectory.loadfacultysFromFile().
	 */
	@Test
	public void testLoadfacultysFromFile() {
		FacultyDirectory sd = new FacultyDirectory();

		// Test valid file
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests facultyDirectory.addfaculty().
	 */
	@Test
	public void testAddfaculty() {
		FacultyDirectory sd = new FacultyDirectory();

		// Test valid faculty
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);

		FacultyDirectory s = new FacultyDirectory();
		try {
			s.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid password", e.getMessage());
		}
		try {
			s.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid password", e.getMessage());
		}
		try {
			s.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid password", e.getMessage());
		}
		try {
			s.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid password", e.getMessage());
		}
	}

	/**
	 * Tests facultyDirectory.getfacultyById().
	 */
	@Test
	public void testGetfacultyById() {
		FacultyDirectory sd = new FacultyDirectory();

		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		Faculty s = sd.getFacultyById("kpatel");
		assertEquals(s.getId(), "kpatel");
		s = sd.getFacultyById("error");
		assertEquals(s, null);

	}

	/**
	 * Tests facultyDirectory.removefaculty().
	 */
	@Test
	public void testRemovefaculty() {
		FacultyDirectory sd = new FacultyDirectory();

		// Add faculty and remove
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		assertTrue(sd.removeFaculty("awitt"));
		String[][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
		assertEquals("Fiona", facultyDirectory[0][0]);
		assertEquals("Brent", facultyDirectory[1][0]);
		assertEquals("haguirr", facultyDirectory[2][2]);
		assertEquals("Kevyn", facultyDirectory[3][0]);
		assertEquals("Elton", facultyDirectory[4][0]);
		assertEquals("Norman", facultyDirectory[5][0]);
		assertEquals("Brady", facultyDirectory[5][1]);
		assertEquals("nbrady", facultyDirectory[5][2]);
		assertTrue(sd.removeFaculty("lwalls"));
		facultyDirectory = sd.getFacultyDirectory();
		assertEquals(6, facultyDirectory.length);
	}

	/**
	 * Tests facultyDirectory.savefacultyDirectory().
	 */
	@Test
	public void testSavefacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();

		// Add a faculty
		fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		fd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		fd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
		assertEquals(3, fd.getFacultyDirectory().length);
		fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

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
