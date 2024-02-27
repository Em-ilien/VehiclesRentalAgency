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

class MotorbikeTest {
	private static final int CYLINDER_CAPACITY = 50;
	private static final int CURRENT_YEAR = 2024;
	private static final int YEAR_2020 = 2020;
	private static final String XP400GT_MODEL = "XP400 GT";
	private static final String PEUGEOT_BRAND = "Peugeot";

	Motorbike motorbike;

	@AfterEach
	void tearDown() throws Exception {
		motorbike = null;
	}

	@ParameterizedTest
	@CsvSource({ "0,true", "-1,true", "1899,true", "1900,false", "2010,false", "2024,false", "2025,true" })
	void testProductionYear(int productionYear, boolean exceptionExpected) throws Throwable {
		try (MockedStatic<TimeProvider> utilities = Mockito.mockStatic(TimeProvider.class)) {
			utilities.when(TimeProvider::currentYearValue).thenReturn(CURRENT_YEAR);

			ThrowingCallable throwingCallable = () -> motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL,
					productionYear, CYLINDER_CAPACITY);

			if (exceptionExpected)
				assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable)
						.withMessageStartingWith("L'année de production " + productionYear + " n'est pas comprise");
			else
				throwingCallable.call();
		}
	}

	@ParameterizedTest
	@CsvSource({ "-1,true", "49,true", "50,false", "52,false" })
	void testCylinderCapacityConstructor(int cylinderCapcity, boolean exceptionExpected) throws Throwable {
		ThrowingCallable throwingCallable = () -> motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020,
				cylinderCapcity);

		if (exceptionExpected)
			assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable)
					.withMessageStartingWith("La cylindrée " + cylinderCapcity + " n'est pas supérieure ou égale");
		else
			throwingCallable.call();
	}

	@Test
	void testGet50cm3CylinderCapcity() {
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, CYLINDER_CAPACITY);
		assertThat(motorbike.getCylinderCapacity()).isEqualTo(CYLINDER_CAPACITY);
	}

	@Test
	void testGet150cm3CylinderCapacity() {
		final int cylinder150cm3 = 150;
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, cylinder150cm3);
		assertThat(motorbike.getCylinderCapacity()).isEqualTo(cylinder150cm3);
	}

	@ParameterizedTest
	@CsvSource({ "50,12.5", "150,37.5" })
	void testMotorbikeDailyRentalPrice(int cylinderCapacity, double expectedPrice) {
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, cylinderCapacity);
		assertThat(motorbike.dailyRentalPrice()).isEqualTo(expectedPrice);
	}

	@Test
	void testMotorbikeToString() {
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, CYLINDER_CAPACITY);
		final String space = " ";
		assertThat(motorbike.toString())
				.startsWith(
						"Motorbike" + space + PEUGEOT_BRAND + space + XP400GT_MODEL + space + YEAR_2020 + space + "(")
				.endsWith(") : " + motorbike.dailyRentalPrice() + " €");
	}

	@ParameterizedTest
	@CsvSource({ "50,12.5", "150,37.5" })
	void testMotorbikeToStringCylinderCapacityAndPrice(int cylinderCapacity, double dailyRentalPrice) {
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, cylinderCapacity);
		assertThat(motorbike.toString()).contains("(" + cylinderCapacity + "cm³) : " + dailyRentalPrice + " €");
	}

}
