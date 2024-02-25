package fr.em_ilien.agency.criterion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import fr.em_ilien.agency.Car;

class MaxPriceCriterionTest {
	private static final String BRAND = "Peugeot";
	private static final int PRODUCTION_YEAR = 2020;
	private static final int CAR_NUMBER_OF_SEATS = 5;
	private static final String CAR_MODEL = "208";
	private static final Car CAR = new Car(BRAND, CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS);

	@Test
	void testRespectMaxPriceCriterion() {
		MaxPriceCriterion maxPriceCriterion = new MaxPriceCriterion(CAR.dailyRentalPrice());
		assertThat(maxPriceCriterion.test(CAR)).isTrue();
		assertThat(maxPriceCriterion.test(new Car(BRAND, CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS + 1)))
				.isFalse();
		assertThat(maxPriceCriterion.test(new Car(BRAND, CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS - 1)))
				.isTrue();
	}

}
