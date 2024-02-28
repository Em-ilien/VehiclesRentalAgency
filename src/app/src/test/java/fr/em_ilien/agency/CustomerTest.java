package fr.em_ilien.agency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {

	private static final String CUSTOMER1_LASTNAME = "Cosson";
	private static final String CUSTOMER1_FIRSTNAME = "Emilien";
	private static final int CUSTOMER1_BIRTHDAY = 2003;
	private static final Customer CUSTOMER1 = new Customer(CUSTOMER1_FIRSTNAME, CUSTOMER1_LASTNAME, CUSTOMER1_BIRTHDAY);

	private static final String CUSTOMER2_LASTNAME = "PELARD";
	private static final String CUSTOMER2_FIRSTNAME = "Josie";
	private static final int CUSTOMER2_BIRTHDAY = 1997;
	private static final Customer CUSTOMER2 = new Customer(CUSTOMER2_FIRSTNAME, CUSTOMER2_LASTNAME, CUSTOMER2_BIRTHDAY);

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFirstname() {
		assertThat(CUSTOMER1.getFirstname()).isEqualTo(CUSTOMER1_FIRSTNAME);
		assertThat(CUSTOMER2.getFirstname()).isEqualTo(CUSTOMER2_FIRSTNAME);
	}

	@Test
	void testLastname() {
		assertThat(CUSTOMER1.getLastname()).isEqualTo(CUSTOMER1_LASTNAME.toUpperCase());
		assertThat(CUSTOMER2.getLastname()).isEqualTo(CUSTOMER2_LASTNAME.toUpperCase());
	}

	@Test
	void testBirthday() {
		assertThat(CUSTOMER1.getBirthday()).isEqualTo(CUSTOMER1_BIRTHDAY);
		assertThat(CUSTOMER2.getBirthday()).isEqualTo(CUSTOMER2_BIRTHDAY);
	}

	@Test
	void testToString() {
		assertThat(CUSTOMER1.toString()).isEqualTo(CUSTOMER1_FIRSTNAME + " " + CUSTOMER1_LASTNAME.toUpperCase());
	}

}
