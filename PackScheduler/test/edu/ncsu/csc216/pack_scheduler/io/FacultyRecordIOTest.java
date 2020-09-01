/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Test For the FacultyRecordIO class
 * 
 * @author Anton
 */
public class FacultyRecordIOTest {

	/** Expected results for valid records */
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";

	/** Array of valid faculty records */
	private String[] validFaculty = { validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
			validFaculty5, validFaculty6, validFaculty7 };

	/** the hash version of the password */
	private String hashPW;

	/** Algorithm used to has password */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Resets actual_faculty_records.txt and replaces the passwords in valid faculty
	 * records with hashed version of the passwords
	 */
	@Before
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validFaculty.length; i++) {
				validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
		Path source = FileSystems.getDefault().getPath("test-files", "actual_faculty_records.txt");
		try {
			Files.deleteIfExists(source);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests readFacultyRecords()
	 */
	@Test
	public void testReadFacultyRecords() {
		try {
			LinkedList<Faculty> testFacultyValid = FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt");
			for (int i = 0; i < validFaculty.length; i++) {
				assertEquals(validFaculty[i], testFacultyValid.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("File not found");
		}
		try {
			LinkedList<Faculty> testFacultyInvalid = FacultyRecordIO
					.readFacultyRecords("test-files/invalid_faculty_records.txt");
			if (testFacultyInvalid.size() != 0) {
				fail("Expected siz: 0. Actual size: " + testFacultyInvalid.size());
			}
		} catch (FileNotFoundException e) {
			fail("File not Found");
		}
		try {
			FacultyRecordIO.readFacultyRecords("test-files/ecords.txt");
			fail("No FileNotFoundException thrown.");
		} catch (FileNotFoundException e) {
			// skip line
		}
	}

	/**
	 * Tests writeFacultytRecords
	 */
	@Test
	public void testWriteFacultyRecords() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));
		faculty.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", hashPW, 3));
		faculty.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", hashPW, 1));
		// Assumption that you are using a hash of "pw" stored in hashPW

		try {
			FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_faculty_records.txt", faculty);
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (IOException e) {
			// Success, this is expected
		}

		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculty);
			checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
			LinkedList<Faculty> fullFaculty = FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt");
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", fullFaculty);
			checkFiles("test-files/expected_full_faculty_records.txt", "test-files/actual_faculty_records.txt");
		} catch (IOException e) {
			fail("IOException");
		}
	}

	/**
	 * Helper method for testWriteFacultyRecords() that is used to check that the
	 * expected file is the same as the file written by writeFacultyRecords()
	 * 
	 * @param expFile The expected version of the file
	 * @param actFile The actual version of the file
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
