package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Handles writing and reading from files that contain faculty records.
 * 
 * @author Ethan Mancini
 * @author Anton
 */
public class FacultyRecordIO {
	
	/**
	 * Read through the file and save the data of faculty records, throw
	 * FileNotFoundException when file can not be find. Create Linked list of Faculty
	 * to store the data
	 * 
	 * @param fileName the file to read
	 * @throws FileNotFoundException When file isn't found
	 * @return the recorded faculty data
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> facultyRecords = new LinkedList<Faculty>();
		while (fileReader.hasNextLine()) {
			try {
				String fileLines = fileReader.nextLine();
				Faculty facultyRecord = readFaculty(fileLines);
				facultyRecords.add(facultyRecord);

			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return facultyRecords;
	}

	/**
	 * Store the faculty from the file to Faculty, to check if they are valid data
	 * 
	 * @param faculty the faculty record info
	 * @return the faculty record
	 */
	private static Faculty readFaculty(String faculty) {
		String firstName = null;
		String lastName = null;
		String id = null;
		String email = null;
		String password = null;
		int maxCredit = 0;
		try {
			Scanner read = new Scanner(faculty);
			read.useDelimiter(",");
			int i = 0;
			while (read.hasNext()) {
				if (i == 0) {
					firstName = read.next();
				}
				if (i == 1) {
					lastName = read.next();
				}
				if (i == 2) {
					id = read.next();
				}
				if (i == 3) {
					email = read.next();
				}
				if (i == 4) {
					password = read.next();
				}
				if (i == 5) {
					maxCredit = read.nextInt();
				}
				if (i == 6) {
					break; 
				}
				i++;
			}
			read.close();
		} catch (IllegalArgumentException e) {
			//skip line
		}
		Faculty newFaculty = new Faculty(firstName, lastName, id, email, password, maxCredit);
		return newFaculty;
	}

	/**
	 * Write the faculty records to the file, with new faculty records.
	 *   
	 * @param fileName         the file to write in
	 * @param facultyDirectory the LinkedList contains all student directory
	 * @throws IOException when File can not be written to
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}
		fileWriter.close();
	}
	
}
