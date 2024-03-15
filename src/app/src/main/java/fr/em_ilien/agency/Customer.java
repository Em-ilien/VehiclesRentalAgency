package fr.em_ilien.agency;

public class Customer {

	private int birthdayYear;
	private String lastname;
	private String firstname;

	/**
	 * Create a new customer
	 * 
	 * @param firstname    the firstname
	 * @param lastname     the lastname
	 * @param birthdayYear the birthday year
	 */
	public Customer(String firstname, String lastname, int birthdayYear) {
		this.firstname = firstname;
		this.lastname = lastname.toUpperCase();
		this.birthdayYear = birthdayYear;
	}

	/**
	 * 
	 * @return the customer's firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * 
	 * @return the customer's lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * 
	 * @return the customer's birthdayYear
	 */
	public int getBirthdayYear() {
		return birthdayYear;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname.toUpperCase();
	}
}
