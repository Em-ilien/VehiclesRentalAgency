package fr.em_ilien.agency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import fr.em_ilien.util.TimeProvider;

class CarTest {
	private static final String RENAULT_BRAND = "Renault";
	private static final String RENAULT_208_MODEL = "208";
	private static final int CURRENT_YEAR = 2024;
	private static final int YEAR_2020 = 2020;
	private static final int NUMBER_OF_SEATS = 1;

	Car car;

	@AfterEach
	void tearDown() throws Exception {
		car = null;
	}

	@Test
	void testGetPeugeotBrand() {
		final String peugeot = "Peugeot";
		final String megane = "Megane";
		car = new Car(peugeot, megane, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(car.getBrand()).isEqualTo(peugeot);
	}

	@Test
	void testGetRenaultBrand() {
		car = new Car(RENAULT_BRAND, RENAULT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(car.getBrand()).isEqualTo(RENAULT_BRAND);
	}

	@Test
	void testGet207Model() {
		final String model207 = "207";
		car = new Car(RENAULT_BRAND, model207, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(car.getModel()).isEqualTo(model207);
	}

	@Test
	void testGet208Model() {
		car = new Car(RENAULT_BRAND, RENAULT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(car.getModel()).isEqualTo(RENAULT_208_MODEL);
	}

	@Test
	void test2020ProductionYear() {
		car = new Car(RENAULT_BRAND, RENAULT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(car.getProductionYear()).isEqualTo(YEAR_2020);
	}

	@Test
	void test2021ProductionYear() {
		car = new Car(RENAULT_BRAND, RENAULT_208_MODEL, 2021, NUMBER_OF_SEATS);

		assertThat(car.getProductionYear()).isEqualTo(2021);
	}

	@ParameterizedTest
	@CsvSource({ "0,true", "-1,true", "1899,true", "1900,false", "2010,false", "2024,false", "2025,true" })
	void testProductionYear(int productionYear, boolean exceptionExpected) throws Throwable {
		try (MockedStatic<TimeProvider> utilities = Mockito.mockStatic(TimeProvider.class)) {
			utilities.when(TimeProvider::currentYearValue).thenReturn(CURRENT_YEAR);

			ThrowingCallable throwingCallable = () -> car = new Car(RENAULT_BRAND, RENAULT_208_MODEL, productionYear,
					NUMBER_OF_SEATS);

			if (exceptionExpected)
				assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable)
						.withMessageStartingWith("L'année de production " + productionYear + " n'est pas comprise");
			else
				throwingCallable.call();
		}
	}

	@ParameterizedTest
	@CsvSource({ "-1,true", "0,true", "1,false", "3,false" })
	void testNumberOfSeatsConstructor(int numberOfSeats, boolean exceptionExpected) throws Throwable {
		ThrowingCallable throwingCallable = () -> car = new Car(RENAULT_BRAND, RENAULT_208_MODEL, CURRENT_YEAR,
				numberOfSeats);

		if (exceptionExpected)
			assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable)
					.withMessageStartingWith("Le nombre de siège " + numberOfSeats + " n'est pas supérieur ou égal");
		else
			throwingCallable.call();
	}

}
