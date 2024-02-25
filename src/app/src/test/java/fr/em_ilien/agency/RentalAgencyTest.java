package fr.em_ilien.agency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.em_ilien.agency.exceptions.UnknownVehicleException;

class RentalAgencyTest {

	private static final String BRAND = "Peugeot";
	private static final int PRODUCTION_YEAR = 2020;
	private static final int CAR_NUMBER_OF_SEATS = 5;
	private static final String CAR_MODEL = "208";
	private static final String MOTORBIKE_MODEL = "XP400 GT";
	private static final int MOTORBIKE_CYLINDREE = 50;

	private static final Car CAR = new Car(BRAND, CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS);
	private static final Motorbike MOTORBIKE = new Motorbike(BRAND, MOTORBIKE_MODEL, PRODUCTION_YEAR,
			MOTORBIKE_CYLINDREE);

	private RentalAgency rentalAgency;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		rentalAgency = null;
	}

	@Test
	void testCreateAgencyWithEmptyVehiclesList() {
		rentalAgency = new RentalAgency();
		assertThat(rentalAgency.getVehicles()).isEmpty();
	}

	@Test
	void testCreateAgencyWithOneDefaultVehicle() {
		rentalAgency = new RentalAgency(CAR);
		assertThat(rentalAgency.getVehicles()).isNotEmpty().hasSize(1).contains(CAR);
	}

	@Test
	void testCreateAgencyWithTwoDefaultVehicle() {
		rentalAgency = new RentalAgency(CAR, MOTORBIKE);
		assertThat(rentalAgency.getVehicles()).isNotEmpty().hasSize(2).contains(CAR).contains(MOTORBIKE);
	}

	@Test
	void testUseGetVehicleToAddAVehicleShouldNotWork() {
		rentalAgency = new RentalAgency(CAR);
		rentalAgency.getVehicles().add(MOTORBIKE);
		assertThat(rentalAgency.getVehicles()).hasSize(1).contains(CAR).doesNotContain(MOTORBIKE);
	}

	@Test
	void testAddVehicleToAgencyShouldWork() {
		rentalAgency = new RentalAgency();
		rentalAgency.add(CAR);
		assertThat(rentalAgency.getVehicles()).isNotEmpty().hasSize(1).contains(CAR);
	}

	@Test
	void testAddTwoVehiclesToAgencyShouldWork() {
		rentalAgency = new RentalAgency();
		final boolean res1 = rentalAgency.add(CAR);
		final boolean res2 = rentalAgency.add(MOTORBIKE);
		assertThat(res1).isTrue();
		assertThat(res2).isTrue();
		assertThat(rentalAgency.getVehicles()).isNotEmpty().hasSize(2).contains(CAR).contains(MOTORBIKE);
	}

	@Test
	void testAddTheSameVehicleTwoTimesShouldNotWork() {
		rentalAgency = new RentalAgency();
		final boolean res1 = rentalAgency.add(CAR);
		final boolean res2 = rentalAgency.add(CAR);
		assertThat(res1).isTrue();
		assertThat(res2).isFalse();
		assertThat(rentalAgency.getVehicles()).isNotEmpty().hasSize(1).contains(CAR);
	}

	@Test
	void testRemoveVehicleShouldWork() {
		rentalAgency = new RentalAgency(CAR);
		rentalAgency.remove(CAR);
		assertThat(rentalAgency.getVehicles()).doesNotContain(CAR).isEmpty();
	}

	@Test
	void testRemoveNotContainedVehicleShouldNotWork() {
		rentalAgency = new RentalAgency();

		ThrowingCallable throwingCallable = () -> rentalAgency.remove(CAR);
		assertThatExceptionOfType(UnknownVehicleException.class).isThrownBy(throwingCallable);
	}

	@Test
	void testRemoveTwoTimeSameVegicleShouldNotWork() {
		rentalAgency = new RentalAgency(CAR);

		ThrowingCallable throwingCallable = () -> rentalAgency.remove(CAR);
		rentalAgency.remove(CAR);
		assertThat(rentalAgency.getVehicles()).doesNotContain(CAR).isEmpty();
		assertThatExceptionOfType(UnknownVehicleException.class).isThrownBy(throwingCallable);
	}

	@Test
	void testContainsMethod() {
		rentalAgency = new RentalAgency(CAR);
		assertThat(rentalAgency.contains(CAR)).isTrue();
		assertThat(rentalAgency.contains(MOTORBIKE)).isFalse();
	}

}