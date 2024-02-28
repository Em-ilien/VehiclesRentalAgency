package fr.em_ilien.agency;

public class Customer {

	private int birthdayYear;
	private String lastname;
	private String firstname;

	public Customer(String firstname, String lastname, int birthdayYear) {
		this.firstname = firstname;
		this.lastname = lastname.toUpperCase();
		this.birthdayYear = birthdayYear;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public int getBirthday() {
		return birthdayYear;
	}

	
	@Override
	public String toString() {
		return firstname + " " + lastname.toUpperCase();
	}
}
