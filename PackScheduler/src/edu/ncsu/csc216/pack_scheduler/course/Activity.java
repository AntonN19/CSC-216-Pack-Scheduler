package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Parent class that gives the program understandings of what an activity is.
 * @author Zhongke Ma
 * @author Anton Nikulsin
 * @author Stephen Welsh
 */
public abstract class Activity implements Conflict {

	/** Course times upper time limit */
	private static final int UPPER_TIME = 2400;
	/** Courses time Upper Hour */
	private static final int UPPER_HOUR = 60;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	/**
	 * Constructs the constructor for the activity super class. This allows for storage of the title, 
	 * days, start time, and end time of any activity the user wishes to add.
	 * @param title the title of the activity
	 * @param meetingDays the days the activity will be on
	 * @param startTime the time the activity will start 
	 * @param endTime the time the activity will end
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
	}
	
	/**
	 * Creates an abstract method for calling a short Array of displaying information for the 
	 * WolfScheduler GUI.
	 * @return a short Array display for the WolfScheduler GUI
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Creates an abstract method for calling a long Array of displaying information for the 
	 * WolfScheduler GUI.
	 * @return a short Array display for the WolfScheduler GUI
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Creates an abstract method for checking if the Activity is a duplicate of another activity
	 * @param activity is the activity passed to be checked if it is a duplicate
	 * @return the name of the activity
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Returns Course's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets Course's title. If the title is null, or has an empty string, throw a new
	 * IllegalArgument Exception. 
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title is null or empty string
	 */
	public void setTitle(String title) {
		if(title == null || title.equals("") ) {
			throw new IllegalArgumentException("Invalid " + this.getClass().getName() + " title.");
		}
		this.title = title;
	}

	/**
	 * Returns the Course's Meeting Days
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the Activitie's Meeting Days.
	 * @param meetingDays the meetingDays to set
	 */
	public void setMeetingDays(String meetingDays) {
		this.meetingDays = meetingDays;
	}

	/**
	 * Returns the Course's Start Time
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's End Time
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the course time with a start and end time.
	 * @param startTime initiates the start time of the course times
	 * @param endTime initiates the end time of the course time
	 * @throws IllegalArgumentException if the start time or end time is not a  valid military time
	 * @throws IllegalArgumentException if end time is less than start time
	 * @throws IllegalArgumentException if the minutes in start time or end time are greater than 59
	 */
	public void setActivityTime(int startTime, int endTime) {
		if(startTime < 0 || startTime >= UPPER_TIME || endTime < 0 || endTime >= UPPER_TIME) {
			throw new IllegalArgumentException("Invalid " + this.getClass().getName() + " times.");
		}
		if(startTime > endTime) {
			throw new IllegalArgumentException("Invalid " + this.getClass().getName() + " times.");
		}
		if(meetingDays.equals("A") && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException("Invalid " + this.getClass().getName() + " times.");
		}
		if(startTime % 100 >= UPPER_HOUR || endTime % 100 >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid " + this.getClass().getName() + " times.");
		}
		
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * This method is used in order to display the meeting days and times as a
	 * string in the format DAYS StartTime-EndTime (ex MW 11:00AM-12:20PM).
	 * 
	 * @return The formatted meeting times and days
	 */
	public String getMeetingString() {
		if (this.meetingDays.equals("A")) {
			return "Arranged";
		}
		String returnStr = meetingDays + " ";
		returnStr += toStandardTime(startTime) + "-" + toStandardTime(endTime);
		return returnStr;
	}
	
	/**
	 * Method used to convert a correctly formatted military time integer to a
	 * standard time string.
	 * 
	 * @param time The military time that needs to be converted
	 * @return Standard time as a string
	 */
	private String toStandardTime(int time) {
		String returnStr = "";
		int mins;
		if (time / 100 <= 12) {
			mins = time % 100;
			if (mins == 0) {
				returnStr += time / 100 + ":" + mins + "0";
			} else {
				returnStr += time / 100 + ":" + mins;
			}
			if (time / 100 == 12) {
				returnStr += "PM";
			} else {
				returnStr += "AM";
			}
		} else {
			mins = time % 100;
			if (mins == 0) {
				returnStr += time / 100 - 12 + ":" + mins + "0";
			} else {
				returnStr += time / 100 - 12 + ":" + mins;
			}
			if (time / 100 == 12) {
				returnStr += "AM";
			} else {
				returnStr += "PM";
			}
		}
		return returnStr;
	}
	
	
	/**
	 * Overrides the Check conflict from conflict. Creates a class that will check if the activity being entered is in conflict with the other activities 
	 * in the activity schedule list.
	 * @param possibleConflictingActivity the activity that is meant to be checked with the schedule
	 * @throws ConflictException if the activity is in conflict with another activity.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		
		String[] validDates = new String[] {"M", "T", "W", "H", "F"};
		
		for (int i = 0; i < validDates.length; i++) {
			if(this.meetingDays.contains(validDates[i]) && possibleConflictingActivity.getMeetingDays().contains(validDates[i])) {
				if(this.endTime >= possibleConflictingActivity.getStartTime() && this.startTime <= possibleConflictingActivity.getEndTime()) {
					throw new ConflictException();
				}
				else if(this.startTime >= possibleConflictingActivity.getEndTime() && this.endTime <= possibleConflictingActivity.getEndTime()) {
					throw new ConflictException();
				}
			}
		}
		
	}

	/**
	 * Generates Hash code for the Activity class
	 * @return result of hashed code to give unique identifier to each class
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Checks to see if two instances of the Activity Class are equal 
	 * @return true if the instances are equal
	 * 		   false if the instances are not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
}