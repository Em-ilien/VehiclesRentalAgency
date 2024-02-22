package fr.em_ilien.agency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import fr.em_ilien.util.TimeProvider;

class CarTest {
	private static final String PEUGEOT_BRAND = "Peugeot";
	private static final String PEUGEOT_208_MODEL = "208";
	private static final int CURRENT_YEAR = 2024;
	private static final int YEAR_2020 = 2020;
	private static final int NUMBER_OF_SEATS = 1;

	Car car;

	@AfterEach
	void tearDown() throws Exception {
		car = null;
	}

	@ParameterizedTest
	@CsvSource({ "-1,true", "0,true", "1,false", "3,false" })
	void testNumberOfSeatsConstructor(int numberOfSeats, boolean exceptionExpected) throws Throwable {
		ThrowingCallable throwingCallable = () -> car = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, CURRENT_YEAR,
				numberOfSeats);

		if (exceptionExpected)
			assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable)
					.withMessageStartingWith("Le nombre de siège " + numberOfSeats + " n'est pas supérieur ou égal");
		else
			throwingCallable.call();
	}

	@ParameterizedTest
	@CsvSource({ "2021,2021,true", "2021,2019,true", "2021,2017,true", "2021,2016,false", "2021,2010,false",
			"2020,2016,true", "2020,2015,false", "2020,2010,false" })
	void testIsNewCar(int currentYear, int productionYear, boolean expectedIsNew) {
		try (MockedStatic<TimeProvider> utilities = Mockito.mockStatic(TimeProvider.class)) {
			utilities.when(TimeProvider::currentYearValue).thenReturn(currentYear);

			car = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, productionYear, NUMBER_OF_SEATS);
			assertThat(car.isNew()).isEqualTo(expectedIsNew);
		}
	}

	@ParameterizedTest
	@CsvSource({ "2010,1,20", "2024,1,40", "2010,2,40", "2024,2,80", "2011,3,60", "2023,3,120" })
	void testDailyRentalPrice(int productionYear, int numberOfSeats, int priceExpected) {
		try (MockedStatic<TimeProvider> utilities = Mockito.mockStatic(TimeProvider.class)) {
			utilities.when(TimeProvider::currentYearValue).thenReturn(CURRENT_YEAR);

			car = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, productionYear, numberOfSeats);
			assertThat(car.dailyRentalPrice()).isEqualTo(priceExpected);
		}
	}

	@Test
	void testCarToString() {
		car = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);
		final String space = " ";
		assertThat(car.toString()).startsWith(
				"Car" + space + PEUGEOT_BRAND + space + PEUGEOT_208_MODEL + space + YEAR_2020 + space + "(");
	}

	@ParameterizedTest
	@CsvSource({ "1,40", "2,80", "3,120" })
	void testCarToStringSeatsAndPrice(int numberOfSeats, double dailyRentalPrice) {
		car = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, numberOfSeats);
		assertThat(car.toString()).contains("(" + numberOfSeats + " seats) : " + dailyRentalPrice + " €");
	}

}
