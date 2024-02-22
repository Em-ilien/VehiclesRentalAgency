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
	private static final int CYLINDREE = 50;
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
					productionYear, CYLINDREE);

			if (exceptionExpected)
				assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable)
						.withMessageStartingWith("L'année de production " + productionYear + " n'est pas comprise");
			else
				throwingCallable.call();
		}
	}

	@ParameterizedTest
	@CsvSource({ "-1,true", "49,true", "50,false", "52,false" })
	void testCylyndreeConstructor(int cylindree, boolean exceptionExpected) throws Throwable {
		ThrowingCallable throwingCallable = () -> motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020,
				cylindree);

		if (exceptionExpected)
			assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable)
					.withMessageStartingWith("La cylyndrée " + cylindree + " n'est pas supérieure ou égale");
		else
			throwingCallable.call();
	}

	@Test
	void testGet50cm3Cylindree() {
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, CYLINDREE);
		assertThat(motorbike.getCylindree()).isEqualTo(CYLINDREE);
	}

	@Test
	void testGet150cm3Cylindree() {
		final int cylyndre150cm3 = 150;
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, cylyndre150cm3);
		assertThat(motorbike.getCylindree()).isEqualTo(cylyndre150cm3);
	}

	@ParameterizedTest
	@CsvSource({ "50,12.5", "150,37.5" })
	void testMotorbikeDailyRentalPrice(int cylindree, double expectedPrice) {
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, cylindree);
		assertThat(motorbike.dailyRentalPrice()).isEqualTo(expectedPrice);
	}

	@Test
	void testMotorbikeToString() {
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, CYLINDREE);
		final String space = " ";
		assertThat(motorbike.toString()).startsWith(
				"Motorbike" + space + PEUGEOT_BRAND + space + XP400GT_MODEL + space + YEAR_2020 + space + "(");
	}

	@ParameterizedTest
	@CsvSource({ "50,12.5", "150,37.5" })
	void testMotorbikeToStringCylyndreeAndPrice(int cylindree, double dailyRentalPrice) {
		motorbike = new Motorbike(PEUGEOT_BRAND, XP400GT_MODEL, YEAR_2020, cylindree);
		assertThat(motorbike.toString()).contains("(" + cylindree + "cm³) : " + dailyRentalPrice + " €");
	}

}
