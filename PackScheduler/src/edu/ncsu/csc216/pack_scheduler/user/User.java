package edu.ncsu.csc216.pack_scheduler.user;

/**
 * The abstract User Class defines state that any User using the RegistrationManager program must have.  It also provides concrete methods which 
 * allow for a User's state to be retrieved using getter methods and modified using setter methods which also check for invalid values for a
 * User's fields.  Finally, User overrides the generic Object class' hashcode() and equals methods()
 * 
 * @author Anton Schell
 */
public abstract class User {

	/** Students first name */
	private String firstName;
	/** Students last name */
	private String lastName;
	/** Students id */
	private String id;
	/** Students email */
	private String email;
	/** Students password */
	private String password;

	/**
	 * Constructor for the User class
	 * 
	 * @param firstName Users first name
	 * @param lastName Users last name
	 * @param id Users id
	 * @param email Users email
	 * @param password Users password
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setId(id);
		this.setEmail(email);
		this.setPassword(password);
	}

	/**
	 * Returns the STudents First Name
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the Students First Name and throws an IllegalArgumentException if the
	 * firstName is null or a blank string
	 * 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException If firstName is null or an empty string
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Returns the students last name
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the students last name. Throws an IllegalArgumentException if the
	 * lastName is null or a blank string
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException If lastName is null or an empty string
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Returns the id of the student
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the students id. Throws an IllegalArgumentException if the id is null or
	 * a blank string
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException If id is null or an empty string
	 */
	protected void setId(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Returns the email of the student
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the student email. Throws an IllegalAregumentException if the parameter
	 * is null or an empty string. Throws another IllegalArgumentException if the
	 * email doesn't contain an @ character or . character or if the @ character
	 * index is behind the . characters index.
	 * 
	 * @param email the email to set
	 * @throws IllegalArgumentException If email is null or an empty string
	 * @throws IllegalArgumentException if email doesn't contain an '@' or '.'
	 * @throws IllegalArgumentException if email's index of '@' is greater than the
	 *                                  index for '.'
	 */
	public void setEmail(String email) {
		if (email == null || email.length() == 0) {
			throw new IllegalArgumentException("Invalid email");
		} else if (!email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		} else if (email.indexOf('@') > email.lastIndexOf('.')) {
			throw new IllegalArgumentException("Invalid email");
		}
	
		this.email = email;
	}

	/**
	 * Returns the password of the student
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the students password. Throws an IllegalArgumentException if the
	 * password is null or a blank string
	 * 
	 * @param password the password to set
	 * @throws IllegalArgumentException If id is null or an empty string
	 */
	public void setPassword(String password) {
	
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		}
	
		this.password = password;
	}

	/**
	 * Hash Code method for the User superclass
	 * 
	 * @return The hashCode for the class
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Equals method for the User superclass
	 * 
	 * @param obj that the user is being compared to
	 * @return true/false based on the result
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	

}