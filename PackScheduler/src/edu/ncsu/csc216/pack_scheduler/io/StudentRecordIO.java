package edu.ncsu.csc216.pack_scheduler.io;

import java.util.*;
import java.io.*;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Read file and store the data as arraylist
 * 
 * @author ZHongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 *
 */
public class StudentRecordIO {

	/**
	 * Read through the file and save the data of student records, throw
	 * FileNotFoundException when file can not be find. Create Sorted Array list of Student
	 * to store the data
	 * 
	 * @param fileName the file to read
	 * @throws FileNotFoundException When file isn't find
	 * @return studentRecords the student records data
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> studentRecords = new SortedList<Student>();
		while (fileReader.hasNextLine()) {
			try {
				String fileLines = fileReader.nextLine();
				Student studentRecord = readStudent(fileLines);
				studentRecords.add(studentRecord);

			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return studentRecords;
	}

	/**
	 * Store the student from the file to Student, to check if they are valid data
	 * 
	 * @param student the student record info
	 * @return the student record
	 */
	private static Student readStudent(String student) {
		String firstName = null;
		String lastName = null;
		String id = null;
		String email = null;
		String password = null;
		int maxCredit = 0;
		try {
			Scanner read = new Scanner(student);
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
		Student newStudent = new Student(firstName, lastName, id, email, password, maxCredit);
		return newStudent;
	}

	/**
	 * Write the student records to the file, with new student records.
	 *   
	 * @param fileName         the file to write in
	 * @param studentDirectory the arrayList contains all student directory
	 * @throws IOException when File can not be write to
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}
		fileWriter.close();
	}

}
