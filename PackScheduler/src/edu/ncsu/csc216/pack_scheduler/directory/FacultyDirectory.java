package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * The FacultyDirectory class contains a LinkedList of Faculty and provides various methods
 * for accessing and modifying the list of Faculty as well as file IO.
 * 
 * @author Ethan Mancini
 * @author Anton
 */
public class FacultyDirectory {

	/** List of faculties in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Creates an empty faculty directory.
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/**
	 * Creates an empty faculty directory. All faculties in the previous list are
	 * list unless saved by the user.
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}

	/**
	 * Constructs the faculty directory by reading in faculty information from the
	 * given file. Throws an IllegalArgumentException if the file cannot be found.
	 * 
	 * @param fileName file containing list of faculties
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Adds a faculty to the directory. Returns true if the faculty is added and
	 * false if the faculty is unable to be added because their id matches another
	 * faculty's id.
	 * 
	 * This method also hashes the faculty's password for internal storage.
	 * 
	 * @param firstName      faculty's first name
	 * @param lastName       faculty's last name
	 * @param id             faculty's id
	 * @param email          faculty's email
	 * @param password       faculty's password
	 * @param repeatPassword faculty's repeated password
	 * @param maxCredits     faculty's max credits.
	 * @return true if added
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
			String repeatPassword, int maxCredits) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || password.equals("") || repeatPassword.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());

			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatPassword.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		// If an IllegalArgumentException is thrown, it's passed up from faculty
		// to the GUI
		Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCredits);

		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty s = facultyDirectory.get(i);
			if (s.getId().equals(faculty.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(faculty);
	}
	
	/**
	 * Gets a faculty by their id
	 * 
	 * @param id the id to look for
	 * @return the faculty with matching id, or null if none exist.
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty s = facultyDirectory.get(i);
			if (s.getId().equals(id)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Removes the faculty with the given id from the list of facultys with the
	 * given id. Returns true if the faculty is removed and false if the faculty is
	 * not in the list.
	 * 
	 * @param facultyId faculty's id
	 * @return true if removed
	 */
	public boolean removeFaculty(String facultyId) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty s = facultyDirectory.get(i);
			if (s.getId().equals(facultyId)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns all faculties in the directory with a column for first name, last
	 * name, and id.
	 * 
	 * @return String array containing faculty's first name, last name, and id.
	 */
	public String[][] getFacultyDirectory() {
		String[][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty s = facultyDirectory.get(i);
			directory[i][0] = s.getFirstName();
			directory[i][1] = s.getLastName();
			directory[i][2] = s.getId();
		}
		return directory;
	}

	/**
	 * Saves all faculties in the directory to a file.
	 * 
	 * @param fileName name of file to save faculties to.
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
}
