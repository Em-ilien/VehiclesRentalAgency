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

class AbstractVehicleTest {
	private static final String PEUGEOT_BRAND = "Peugeot";
	private static final String PEUGEOT_208_MODEL = "208";
	private static final int CURRENT_YEAR = 2024;
	private static final int YEAR_2020 = 2020;
	private static final int NUMBER_OF_SEATS = 1;

	Vehicle vehicle;

	@AfterEach
	void tearDown() throws Exception {
		vehicle = null;
	}

	@Test
	void testGetRenaultBrand() {
		final String renault = "Renault";
		final String megane = "Megane";
		vehicle = new Car(renault, megane, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(vehicle.getBrand()).isEqualTo(renault);
	}

	@Test
	void testGetPeugeoltBrand() {
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(vehicle.getBrand()).isEqualTo(PEUGEOT_BRAND);
	}

	@Test
	void testGet207Model() {
		final String model207 = "207";
		vehicle = new Car(PEUGEOT_BRAND, model207, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(vehicle.getModel()).isEqualTo(model207);
	}

	@Test
	void testGet208Model() {
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(vehicle.getModel()).isEqualTo(PEUGEOT_208_MODEL);
	}

	@Test
	void test2020ProductionYear() {
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(vehicle.getProductionYear()).isEqualTo(YEAR_2020);
	}

	@Test
	void test2021ProductionYear() {
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, 2021, NUMBER_OF_SEATS);

		assertThat(vehicle.getProductionYear()).isEqualTo(2021);
	}

	@ParameterizedTest
	@CsvSource({ "0,true", "-1,true", "1899,true", "1900,false", "2010,false", "2024,false", "2025,true" })
	void testProductionYear(int productionYear, boolean exceptionExpected) throws Throwable {
		try (MockedStatic<TimeProvider> utilities = Mockito.mockStatic(TimeProvider.class)) {
			utilities.when(TimeProvider::currentYearValue).thenReturn(CURRENT_YEAR);

			ThrowingCallable throwingCallable = () -> vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL,
					productionYear, NUMBER_OF_SEATS);

			if (exceptionExpected)
				assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable)
						.withMessageStartingWith("The production year " + productionYear + " isn't included in ");
			else
				throwingCallable.call();
		}
	}

	@Test
	void testTwoSameInstanceAreEquals() {
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);
		Vehicle vehicle2 = vehicle;

		assertThat(vehicle).isEqualTo(vehicle2);
	}

	@Test
	void testNotSameClassInstanceAreNotEquals() {
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);
		Motorbike motorbike = new Motorbike(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, 50);

		assertThat(vehicle).isNotEqualTo(motorbike);
	}

	@Test
	void testTwoSimilarCarsAreEquals() {
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);
		Car car2 = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(vehicle).isEqualTo(car2);
	}

	@Test
	void testTwoCarsWithNotSameBrandAreNotEquals() {
		final String renault = "Renault";
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);
		Car car2 = new Car(renault, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(vehicle).isNotEqualTo(car2);
	}

	@Test
	void testTwoCarsWithNotSameModelAreNotEquals() {
		final String peugeot207Model = "207";
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);
		Car car2 = new Car(PEUGEOT_BRAND, peugeot207Model, YEAR_2020, NUMBER_OF_SEATS);

		assertThat(vehicle).isNotEqualTo(car2);
	}

	@Test
	void testTwoCarsWithNotSameProductionYearAreNotEquals() {
		vehicle = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, YEAR_2020, NUMBER_OF_SEATS);
		Car car2 = new Car(PEUGEOT_BRAND, PEUGEOT_208_MODEL, CURRENT_YEAR, NUMBER_OF_SEATS);

		assertThat(vehicle).isNotEqualTo(car2);
	}

	@Test
	void testFillANullBrandShouldNotWork() {
		ThrowingCallable throwingCallable = () -> vehicle = new Car(null, PEUGEOT_208_MODEL, YEAR_2020,
				NUMBER_OF_SEATS);
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable);
	}

	@Test
	void testFillANullModelShouldNotWork() {
		ThrowingCallable throwingCallable = () -> vehicle = new Car(PEUGEOT_BRAND, null, YEAR_2020, NUMBER_OF_SEATS);
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable);
	}

}
