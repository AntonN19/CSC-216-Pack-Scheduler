/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidatorFSM;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Creates a Course object that will store information about courses
 * 
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 */
public class Course extends Activity implements Comparable<Course> {

	/** Max length of the section */
	private static final int SECTION_LENGTH = 3;
	/** Max length of the name of a course */
	private static final int MAX_NAME_LENGTH = 6;
	/** Min length of the name of a course */
	private static final int MIN_NAME_LENGTH = 4;
	/** Max credits of a course */
	private static final int MAX_CREDITS = 5;
	/** Min credits of a course */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's enrollment list */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap max number of students that can be enrolled in the course
	 * @param meetingDays  meeting days for Course as series of chars
	 * @param startTime    start time for Course
	 * @param endTime      end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(this, enrollmentCap);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap max number of students that can be enrolled in the course
	 * @param meetingDays  meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns Course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 4 or
	 * greater than 6, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null or length is less than 4 or
	 *                                  greater than 6
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		CourseNameValidatorFSM validator = new CourseNameValidatorFSM();
		try {
			if (!validator.isValid(name)) {
				throw new IllegalArgumentException("Invalid course name.");
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		this.name = name;
	}

	/**
	 * Returns Course selection
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets Course section. If the section length is not equal to 3 or Null an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if the section is null or not 3 characters
	 *                                  long
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section number.");
		}
		this.section = section;
	}

	/**
	 * Returns Course credits
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets Course credits. If credits are less than 1 or greater than 5, or they
	 * are not an integer, a new IllegalArgumentException is thrown
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if the credits is less than 1 or greater
	 *                                  than 5
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}

	/**
	 * Returns Course's Instructor ID
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets Course's Instructor ID. If instructodId passed is null, or the
	 * instructorId passed is an empty string, throw a new IllegalArgumentException
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorIf is null or an empty string
	 */
	public void setInstructorId(String instructorId) {
//		if (instructorId != null && instructorId.equals("")) {
//			throw new IllegalArgumentException("Invalid instructor unity id.");
//		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Returns Course's enrollment list
	 * 
	 * @return the enrollment list
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

	/**
	 * Generates Hash code for the Course class
	 * 
	 * @return result of hashed code to give unique identifier to each class
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Checks to see if two instances of the Course Class are equal
	 * 
	 * @return true if the instances are equal false if the instances are not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (getMeetingDays().equals("A")) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + ","
					+ getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays()
				+ "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Implements Activities abstract method of getShortDisplayArray, and returns an
	 * Array with five parameters of name, section, title, meeting days, and number of open slots.
	 * 
	 * @return short display array of the name, section, title, meeting days, and number of open slots of
	 *         a specific course
	 */
	@Override
	public String[] getShortDisplayArray() {

		String[] shortDisplay = new String[] { name, section, getTitle(), getMeetingString(), String.valueOf(roll.getOpenSeats()) };

		return shortDisplay;
	}

	/**
	 * Implements Activities abstract method of getLongDisplayArray, and returns an
	 * Array with seven parameters of name, section, title, credits, instructorId,
	 * meeting days, and eventDetails.
	 * 
	 * @return long display array of the name, section, title, credits,
	 *         instructorId, meeting days, and eventDetails.
	 */
	@Override
	public String[] getLongDisplayArray() {

		String[] longDisplay = new String[] { name, section, getTitle(), "" + credits, instructorId, getMeetingString(),
				"" };

		return longDisplay;
	}

	/**
	 * Sets the Course's Meeting Days. If the meeting Days contains characters not
	 * equal to M,T,W,H,F, or A, and if it has A and contains other days, than a new
	 * IllegalArgumentException is thrown.
	 * 
	 * @param meetingDays the meetingDays to set
	 * @throws IllegalArgumentException if meetingDays is null or an empty string
	 * @throws IllegalArgumentException if meeting days doesn't contain a day in the
	 *                                  string
	 * @throws IllegalArgumentException if meeting days contains A and other
	 *                                  characters
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.equals("")) {
			throw new IllegalArgumentException("Invalid meeting days.");
		}

		for (int i = 0; i < meetingDays.length(); i++) {
			if (meetingDays.charAt(i) != 'M' && meetingDays.charAt(i) != 'T' && meetingDays.charAt(i) != 'W'
					&& meetingDays.charAt(i) != 'H' && meetingDays.charAt(i) != 'F' && meetingDays.charAt(i) != 'A') {
				throw new IllegalArgumentException("Invalid meeting days.");
			}
		}

		if (meetingDays.equals("A") || !meetingDays.contains("A")) {
			super.setMeetingDays(meetingDays);
		} else {
			throw new IllegalArgumentException("Invalid meeting days.");
		}
	}

	/**
	 * Checks to see if the Course is a copy of itself by seeing if the name of the
	 * course is equal to the name of the activity passed through the method.
	 * 
	 * @param activity is the activity that is being checked
	 * @return true if the Course is a repeat false if the Course isn't a repeat
	 * @throws IllegalArgumentException if the specified Activity is a duplicate course
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			activity = (Course) activity;
			
			if (((Course) activity).getName().equals(name)) {
				throw new IllegalArgumentException("Already assigned " + name);
			}
			
		}
		return false;
	}

	/**
	 * Implementation of the compareTo method
	 * 
	 * @param o the Course object being compared
	 * @return -1, 0, or 1 based on the result of the compareTo method
	 */
	@Override
	public int compareTo(Course o) {
		if (o == null) {
			throw new NullPointerException();
		}
		String objName = o.getName().toLowerCase();
		String objSection = o.getSection().toLowerCase();
		if (objName.compareTo(this.getName().toLowerCase()) == 0) {
			if (objSection.compareTo(this.getSection().toLowerCase()) == 0) {
				return 0;
			} else {
				return -1 * (objSection.compareTo(this.getSection().toLowerCase()));
			}
		} else {
			return -1 * (objName.compareTo(this.getName().toLowerCase()));
		}
	}

}
