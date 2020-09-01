package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Test for StudentRecordIO class
 * 
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 */
public class StudentRecordIOTest {
	
	/** Expected results for valid records */
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";

	/** Array of valid Student records */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9 };

	/** the hash version of the password */
	private String hashPW;

	/** Algorithm used to has password */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Resets actual_student_records.txt and replaces the passwords in valid student
	 * records with hashed version of the passwords
	 */
	@Before
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
		Path source = FileSystems.getDefault().getPath("test-files", "actual_student_records.txt");
		try {
			Files.deleteIfExists(source);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests readStudentRecords()
	 */
	@Test
	public void testReadStudentRecords() {
		try {
			SortedList<Student> testStudentValid = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], testStudentValid.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("File not found");
		}
		try {
			SortedList<Student> testStudentInvalid = StudentRecordIO.readStudentRecords("test-files/invalid_student_records.txt");
			if (testStudentInvalid.size() != 0) {
				fail("Expected siz: 0. Actual size: " + testStudentInvalid.size());
			}
		} catch (FileNotFoundException e) {
			fail("File not Found");
		}
		try {
			StudentRecordIO.readStudentRecords("test-files/ecords.txt");
			fail("No FileNotFoundException thrown.");
		} catch (FileNotFoundException e) {
			// skip line
		}
	}

	/**
	 * Tests writeStudentRecords
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		// Assumption that you are using a hash of "pw" stored in hashPW

		try {
			StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students);
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (IOException e) {
			// Success, this is expected
		}

		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
			checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
			SortedList<Student> fullStudents = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", fullStudents);
			checkFiles("test-files/expected_full_student_records.txt", "test-files/actual_student_records.txt");
		} catch (IOException e) {
			fail("IOException");
		}
	}

	/**
	 * Helper method for testWriteStudentRecords() that is used to check that the
	 * expected file is the same as the file written by writeStudentRecords()
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
