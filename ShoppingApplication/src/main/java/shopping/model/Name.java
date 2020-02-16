package shopping.model;

import java.io.Serializable;

public class Name implements Serializable {

	private String firstName;
	private String middleName;
	private String lastName;

	public Name(String firstName, String middleName, String lastName) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return firstName + " " + middleName + " " + lastName;
	}
	
	@Override
	public String toString() {
		return "Name [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + "]";
	}

}
