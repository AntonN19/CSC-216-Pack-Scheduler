package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * JUnit test class which tests the functionality of RegistrationManager
 * 
 * @author Ethan Mancini
 * @author NCSU CSC216 Teaching Staff
 * @version February 25, 2020
 */
public class RegistrationManagerTest {
	
	private static final String PROP_FILE = "registrar.properties";
	private static final String HASH_ALGORITHM = "SHA-256";
	private static final String STUDENT_ID = "zking";
	private static final String STUDENT_PASS = "pw";
	private static final String FACULTY_ID = "awitt";
	private static final String FACULTY_PASS = "pw";
	private static final int STUDENT_CREDS = 15;
	private RegistrationManager manager;
	
	/**
	 * Sets up the CourseManager and clears the data.
	 * @throws Exception if error
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.clearData();
		manager.logout();
	}
	
	private String hashPW(String pw) {
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(pw.getBytes());
			return new String(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException();
		}
	}
	
	private boolean loginWithRegistrar(RegistrationManager manager) {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			
			String id = prop.getProperty("id");
			String pw = prop.getProperty("pw");
			
			return manager.login(id, pw);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Tests that the RegistrationManager.getCourseCatalog() does not return null
	 */
	@Test
	public void testGetCourseCatalog() {
		assertNotNull(manager.getCourseCatalog());
	}

	/**
	 * Tests that the RegistrationManager.getStudentDirectory() does not return null
	 */
	@Test
	public void testGetStudentDirectory() {
		assertNotNull(manager.getStudentDirectory());
	}

	/**
	 * Tests that RegistrationManager.login() works as per the lab requirements
	 */
	@Test
	public void testLogin() {
		assertTrue(loginWithRegistrar(manager));
		assertFalse(loginWithRegistrar(manager));
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		assertFalse(manager.login(STUDENT_ID, STUDENT_PASS));
		manager.logout();
		
		assertTrue(manager.login(STUDENT_ID, STUDENT_PASS));
		assertFalse(loginWithRegistrar(manager));
		assertFalse(manager.login(STUDENT_ID, STUDENT_PASS));
		manager.logout();
		
		assertTrue(loginWithRegistrar(manager));
		assertFalse(loginWithRegistrar(manager));
		manager.getFacultyDirectory().loadFacultyFromFile("test-files/faculty_records.txt");
		assertFalse(manager.login(FACULTY_ID, FACULTY_PASS));
		manager.logout();
		
		assertTrue(manager.login(FACULTY_ID, FACULTY_PASS));
		assertFalse(loginWithRegistrar(manager));
		assertFalse(manager.login(FACULTY_ID, FACULTY_PASS));
		manager.logout();
		
		try {
			assertFalse(manager.login("notarealstudent", "notarealpw"));
			fail("Should raise exception");
		} catch (IllegalArgumentException e) {
			//Success, this is expected
		}
	}

	/**
	 * Tests that RegistrationManager.logout() works as per the lab requirements
	 */
	@Test
	public void testLogout() {
		loginWithRegistrar(manager);
		manager.logout();
		assertNull(manager.getCurrentUser());
	}

	/**
	 * Tests that RegistrationManager.getCurrentUser() works as per the lab requirements
	 */
	@Test
	public void testGetCurrentUser() {
		assertTrue(loginWithRegistrar(manager));
		assertNotNull(manager.getCurrentUser());
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		manager.logout();
		assertNull(manager.getCurrentUser());
		assertTrue(manager.login(STUDENT_ID, STUDENT_PASS));
		User s = new Student("Zahir", "King", STUDENT_ID, "orci.Donec@ametmassaQuisque.com", hashPW(STUDENT_PASS), STUDENT_CREDS);
		assertEquals(manager.getCurrentUser(), s);
		assertFalse(loginWithRegistrar(manager));
		assertEquals(manager.getCurrentUser(), s);
		manager.logout();
		assertNull(manager.getCurrentUser());
	}
	
	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	        manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(3, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(1, scheduleFrostArray.length);
	    assertEquals("CSC226", scheduleFrostArray[0][0]);
	    assertEquals("001", scheduleFrostArray[0][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleFrostArray[0][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleFrostArray[0][3]);
	    assertEquals("9", scheduleFrostArray[0][4]);
	            
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    //Check Student Schedule
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(10, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(3, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC226", scheduleHicksArray[1][0]);
	    assertEquals("001", scheduleHicksArray[1][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
	    assertEquals("8", scheduleHicksArray[1][4]);
	    assertEquals("CSC116", scheduleHicksArray[2][0]);
	    assertEquals("003", scheduleHicksArray[2][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
	    assertEquals("9", scheduleHicksArray[2][4]);
	    
	    manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	        manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    assertFalse("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: False - student not enrolled in the course", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(0, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(0, scheduleFrostArray.length);
	    
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(10, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(3, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC226", scheduleHicksArray[1][0]);
	    assertEquals("001", scheduleHicksArray[1][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
	    assertEquals("9", scheduleHicksArray[1][4]);
	    assertEquals("CSC116", scheduleHicksArray[2][0]);
	    assertEquals("003", scheduleHicksArray[2][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
	    assertEquals("9", scheduleHicksArray[2][4]);
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(7, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(2, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC116", scheduleHicksArray[1][0]);
	    assertEquals("003", scheduleHicksArray[1][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3]);
	    assertEquals("9", scheduleHicksArray[1][4]);
	    
	    assertFalse("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: False - already dropped", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(3, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(1, scheduleHicksArray.length);
	    assertEquals("CSC116", scheduleHicksArray[0][0]);
	    assertEquals("003", scheduleHicksArray[0][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC116-003\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(0, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(0, scheduleHicksArray.length);
	    
	    manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //Test if not logged in
	    try {
	        manager.resetSchedule();
	        fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.resetSchedule();
	        fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    manager.resetSchedule();
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(0, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(0, scheduleFrostArray.length);
	    
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    manager.resetSchedule();
	    //Check Student schedule
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(0, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(0, scheduleHicksArray.length);
	    
	    manager.logout();
	}
	
	/**
	 * Test RegistrationManager.addFacultyToCourse()
	 */
	@Test
	public void testAddFacultyToCourse() {
	    FacultyDirectory facultyDirectory = manager.getFacultyDirectory();
	    facultyDirectory.loadFacultyFromFile("test-files/faculty_records_extended.txt");
	    Faculty f = facultyDirectory.getFacultyById("awitt");
	    
	    StudentDirectory studentDirectory = manager.getStudentDirectory();
	    studentDirectory.loadStudentsFromFile("test-files/student_records.txt");
   		
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    Course c = catalog.getCourseFromCatalog("CSC116", "001");
	    c.setInstructorId(null);
	    
	    // Assert that an IllegalArgumentException is thrown if no user is logged in
	    manager.logout();
	    try {
	    	manager.addFacultyToCourse(c, f);
	    	fail();
	    }
	    catch (IllegalArgumentException e) {
	    	// Intended result
	    }
	    
	    // Assert that an IllegalArgumentException is thrown if the current user is not the registrar
	    manager.logout();
	    manager.login("efrost", "pw");
	    try {
	    	manager.addFacultyToCourse(c, f);
	    	fail();
	    }
	    catch (IllegalArgumentException e) {
	    	// Intended result
	    }
	    
	    // Assert that the registrar is able to successfully add a Faculty to a Course
	    manager.logout();
	    manager.login("registrar", "Regi5tr@r");
	    assertTrue(manager.addFacultyToCourse(c, f));
	    assertEquals(f.getId(), c.getInstructorId());
	    assertEquals(1, f.getSchedule().getNumScheduledCourses());
	}
	
	/**
	 * Test RegistrationManager.removeFacultyFromCourse()
	 */
	@Test
	public void testRemoveFacultyFromCourse() {
	    FacultyDirectory facultyDirectory = manager.getFacultyDirectory();
	    facultyDirectory.loadFacultyFromFile("test-files/faculty_records_extended.txt");
	    Faculty f = facultyDirectory.getFacultyById("awitt");
	    
	    StudentDirectory studentDirectory = manager.getStudentDirectory();
	    studentDirectory.loadStudentsFromFile("test-files/student_records.txt");
   		
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    Course c = catalog.getCourseFromCatalog("CSC116", "001");
	    c.setInstructorId(null);
	    
	    // Assert that an IllegalArgumentException is thrown if no user is logged in
	    manager.logout();
	    try {
	    	manager.removeFacultyFromCourse(c, f);
	    	fail();
	    }
	    catch (IllegalArgumentException e) {
	    	// Intended result
	    }
	    
	    // Assert that an IllegalArgumentException is thrown if the current user is not the registrar
	    manager.logout();
	    manager.login("efrost", "pw");
	    try {
	    	manager.removeFacultyFromCourse(c, f);
	    	fail();
	    }
	    catch (IllegalArgumentException e) {
	    	// Intended result
	    }
	    
	    // Assert that the registrar is able to successfully remove a Faculty from a Course
	    manager.logout();
	    manager.login("registrar", "Regi5tr@r");
	    assertTrue(manager.addFacultyToCourse(c, f));
	    assertTrue(manager.removeFacultyFromCourse(c, f));
	    assertEquals(null, c.getInstructorId());
	    assertEquals(0, f.getSchedule().getNumScheduledCourses());
	}
	
	/**
	 * Test RegistrationManager.resetFacultySchedule()
	 */
	@Test
	public void testResetFacultySchedule() {
	    FacultyDirectory facultyDirectory = manager.getFacultyDirectory();
	    facultyDirectory.loadFacultyFromFile("test-files/faculty_records_extended.txt");
	    Faculty f = facultyDirectory.getFacultyById("awitt");
	    
	    StudentDirectory studentDirectory = manager.getStudentDirectory();
	    studentDirectory.loadStudentsFromFile("test-files/student_records.txt");
   		
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    Course c = catalog.getCourseFromCatalog("CSC116", "001");
	    c.setInstructorId(null);
	    
	    // Assert that the registrar is able to successfully reset a Faculty's Schedule
	    manager.logout();
	    manager.login("registrar", "Regi5tr@r");
	    assertTrue(manager.addFacultyToCourse(c, f));
	    manager.resetFacultySchedule(f);
	    assertNull(c.getInstructorId());
	    assertEquals(0, f.getSchedule().getNumScheduledCourses());
	    manager.addFacultyToCourse(c, f);
	    
	    // Assert that an IllegalArgumentException is thrown if no user is logged in
	    manager.logout();
	    try {
	    	manager.resetFacultySchedule(f);
	    	fail();
	    }
	    catch (IllegalArgumentException e) {
	    	// Intended result
	    }
	    
	    // Assert that an IllegalArgumentException is thrown if the current user is not the registrar
	    manager.logout();
	    manager.login("efrost", "pw");
	    try {
	    	manager.resetFacultySchedule(f);
	    	fail();
	    }
	    catch (IllegalArgumentException e) {
	    	// Intended result
	    }
	    
	}
	
}
