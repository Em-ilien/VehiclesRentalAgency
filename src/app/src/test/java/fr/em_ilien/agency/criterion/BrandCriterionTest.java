package fr.em_ilien.agency.criterion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

import fr.em_ilien.agency.Car;

class VehicleCriterionTest {
	private static final String BRAND = "Peugeot";
	private static final int PRODUCTION_YEAR = 2020;
	private static final int CAR_NUMBER_OF_SEATS = 5;
	private static final String CAR_MODEL = "208";
	private static final Car CAR = new Car(BRAND, CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS);

	@Test
	void testRespectBrandCriterion() {
		BrandCriterion brandCriterion = new BrandCriterion(BRAND);
		assertThat(brandCriterion.test(CAR)).isTrue();
		assertThat(brandCriterion.test(new Car(BRAND + "1", CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS)))
				.isFalse();
	}

	@Test
	void testBuilderBrandCriterionWithNullAttributeShouldNotWork() {
		ThrowingCallable throwingCallable = () -> new BrandCriterion(null);
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable);
	}

}
