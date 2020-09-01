package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Class that handles user authentication and decides what student directory and
 * course catalog to display depending on the user.
 * 
 * @author Ethan Mancini
 * @author NCSU CSC216 Teaching Staff
 * @version February 24, 2020
 */
public class RegistrationManager {

	/** Instance variable of itself */
	private static RegistrationManager instance;
	/** A the users course catalog */
	private CourseCatalog courseCatalog = new CourseCatalog();
	/** Student directory */
	private StudentDirectory studentDirectory = new StudentDirectory();
	/**Faculty directory */
	private FacultyDirectory facultyDirectory = new FacultyDirectory();
	/** The user in the registrar */
	private User registrar;
	/** The current user of the system */
	private User currentUser = null;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** File where the registrar information is stored */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Constructor which uses the helper method below to create a registrar
	 */
	private RegistrationManager() {
		createRegistrar();
	}

	/**
	 * Helper method used to create a registrar
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Method used to have the RegistrationManager return an instance of itself
	 * 
	 * @return Returns an instance of itself
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Returns a 2d array of strings to represent the courses in the catalog and
	 * displays their name, section, title, and meeting string.
	 * 
	 * @return a 2d array of courses displaying their name, section, title, and
	 *         meeting string
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Returns all students in the directory with a column for first name, last
	 * name, and id.
	 * 
	 * @return String array containing students first name, last name, and id.
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Getter method for the facultyDirectory
	 * 
	 * @return the current faculty directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return this.facultyDirectory;
	}
	
	/**
	 * Method used to log the current user out
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Method used to return the current user
	 * 
	 * @return The current user in the system
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Method used to login a user
	 * 
	 * @param id       The users id
	 * @param password The users password
	 * @return true if the login was successful
	 * @throws IllegalArgumentException If the user does not exist
	 */
	public boolean login(String id, String password) throws IllegalArgumentException {
		if (currentUser != null) {
			return false;
		}
		Student s = studentDirectory.getStudentById(id);
		Faculty f = facultyDirectory.getFacultyById(id);
		if (s != null) {
			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());

				if (s.getPassword().equals(localHashPW)) {
					currentUser = s;
					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		} else if (f != null) {
			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());

				if (f.getPassword().equals(localHashPW)) {
					currentUser = f;
					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}
		if (registrar.getId().equals(id)) {
			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());

				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}

		}
		throw new IllegalArgumentException("User doesn't exist.");
	}

	/**
	 * Clears course catalog and student directory data
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Class made to create a registrar for a certain user
	 * 
	 * @author NCSU CSC 216 Teaching Staff
	 * @author Ethan Mancini
	 */
	private static class Registrar extends User {

		/**
		 * Create a registrar user with the user id and password in the
		 * registrar.properties file.
		 * 
		 * @param firstName The users first name
		 * @param lastName  The users last name
		 * @param id        The users id
		 * @param email     The users email address
		 * @param hashPW    the encoded password of the user
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}

	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	    
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	    
	}
	
	/**
	 * Adds the specified Faculty to the specified Course and updates the 
	 * Faculty's schedule. Any Exceptions thrown by the method calls in this 
	 * method's body are allowed to propagate to the caller of this method
	 * 
	 * @param c the Course to add the Faculty to
	 * @param f the Faculty to add to the Course
	 * @return true if the Faculty is successfully added
	 * @throws IllegalArgumentException if the current user is not the registrar
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (currentUser != null && currentUser.equals(registrar)) {
			return f.getSchedule().addCourseToSchedule(c);
		}
		else {
			throw new IllegalArgumentException("Illegal Action");
		}
		
	}
	
	/**
	 * Removes the specified Faculty from the specified Course and updates the 
	 * Faculty's schedule. Any Exceptions thrown by the method calls in this 
	 * method's body are allowed to propagate to the caller of this method
	 * 
	 * @param c the Course to remove the Faculty from
	 * @param f the Faculty to remove from the Course
	 * @return true if the Faculty is successfully removed
	 * @throws IllegalArgumentException if the current user is not the registrar
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if (currentUser != null && currentUser.equals(registrar)) {
			return f.getSchedule().removeCourseFromSchedule(c);
		}
		else {
			throw new IllegalArgumentException("Illegal Action");
		}
		
	}
	
	/**
	 * Resets the FacultySchedule of the specified Faculty
	 * 
	 * @param f the Faculty for which to reset their FacultySchedule
	 * @throws IllegalArgumentException if the current user is not the registrar
	 */
	public void resetFacultySchedule(Faculty f) {
		if (currentUser != null && currentUser.equals(registrar)) {
			f.getSchedule().resetSchedule();
		}
		else {
			throw new IllegalArgumentException("Illegal Action");
		}
		
	}
	
}
