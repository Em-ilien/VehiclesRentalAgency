package fr.em_ilien.agency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.em_ilien.agency.criterion.BrandCriterion;
import fr.em_ilien.agency.criterion.MaxPriceCriterion;
import fr.em_ilien.agency.exceptions.UnknownVehicleException;

class RentalAgencyTest {

	private static final Customer CUSTOMER2 = new Customer("Josie", "Cosson", 1950);
	private static final Customer CUSTOMER = new Customer("Emilien", "Cosson", 2003);
	private static final String BRAND = "Peugeot";
	private static final int PRODUCTION_YEAR = 2020;
	private static final int CAR_NUMBER_OF_SEATS = 5;
	private static final String CAR_MODEL = "208";
	private static final String MOTORBIKE_MODEL = "XP400 GT";
	private static final int MOTORBIKE_CYLINDREE = 50;

	private static final Car CAR = new Car(BRAND, CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS);
	private static final Motorbike MOTORBIKE = new Motorbike(BRAND, MOTORBIKE_MODEL, PRODUCTION_YEAR,
			MOTORBIKE_CYLINDREE);

	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	private RentalAgency rentalAgency;

	@BeforeEach
	void setUp() throws Exception {
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@AfterEach
	void tearDown() throws Exception {
		rentalAgency = null;
		System.setOut(standardOut);
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

	@Test
	void testSelectBrandCriterionWorks() {
		final Car renaultCar = new Car("Renault", CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS);
		rentalAgency = new RentalAgency(CAR, MOTORBIKE, renaultCar);
		BrandCriterion brandCriterion = new BrandCriterion(BRAND);
		List<Vehicle> filteredVehicles = rentalAgency.select(brandCriterion);

		assertThat(filteredVehicles).isNotEmpty().contains(CAR).hasSize(2).contains(MOTORBIKE)
				.doesNotContain(renaultCar);
		rentalAgency.printSelectedVehicles(brandCriterion);
		assertThat(outputStreamCaptor.toString()).contains(CAR.toString()).contains(MOTORBIKE.toString())
				.doesNotContain(renaultCar.toString());
	}

	@Test
	void testSelectMaxPriceCriterionWorks() {
		final Car expensiveCar = new Car(BRAND + "1", CAR_MODEL, PRODUCTION_YEAR, CAR_NUMBER_OF_SEATS + 10);
		rentalAgency = new RentalAgency(CAR, MOTORBIKE, expensiveCar);
		MaxPriceCriterion maxPriceCriterion = new MaxPriceCriterion(CAR.dailyRentalPrice());
		List<Vehicle> filteredVehicles = rentalAgency.select(maxPriceCriterion);

		assertThat(filteredVehicles).isNotEmpty().hasSize(2).contains(CAR).contains(MOTORBIKE)
				.doesNotContain(expensiveCar);
		rentalAgency.printSelectedVehicles(maxPriceCriterion);
		assertThat(outputStreamCaptor.toString()).contains(CAR.toString()).contains(MOTORBIKE.toString())
				.doesNotContain(expensiveCar.toString());
	}

	@Test
	void testRentACar() {
		final double expectedPrice = CAR.dailyRentalPrice();
		rentalAgency = new RentalAgency(CAR);

		assertThat(rentalAgency.aVehicleIsRentedBy(CUSTOMER)).isFalse();
		assertThat(rentalAgency.vehicleIsRented(CAR)).isFalse();

		assertThat(rentalAgency.rentVehicle(CUSTOMER, CAR)).isEqualTo(expectedPrice);

		assertThat(rentalAgency.aVehicleIsRentedBy(CUSTOMER)).isTrue();
		assertThat(rentalAgency.vehicleIsRented(CAR)).isTrue();

		rentalAgency.returnVehicle(CUSTOMER);

		assertThat(rentalAgency.aVehicleIsRentedBy(CUSTOMER)).isFalse();
		assertThat(rentalAgency.vehicleIsRented(CAR)).isFalse();
	}

	@Test
	void testRentUnknowVehicle() {
		rentalAgency = new RentalAgency(MOTORBIKE);

		ThrowingCallable throwingCallable = () -> rentalAgency.rentVehicle(CUSTOMER, CAR);
		assertThatExceptionOfType(UnknownVehicleException.class).isThrownBy(throwingCallable);
	}

	@Test
	void testCustomerRentTwoVehicles() {
		rentalAgency = new RentalAgency(CAR, MOTORBIKE);

		rentalAgency.rentVehicle(CUSTOMER, CAR);
		ThrowingCallable throwingCallable = () -> rentalAgency.rentVehicle(CUSTOMER, MOTORBIKE);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(throwingCallable);
	}

	@Test
	void testToRentTwoTimesTheSameVehicles() {
		rentalAgency = new RentalAgency(CAR);

		rentalAgency.rentVehicle(CUSTOMER, CAR);
		ThrowingCallable throwingCallable = () -> rentalAgency.rentVehicle(CUSTOMER2, CAR);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(throwingCallable);
	}

	@Test
	void testItDoesNothingIfReturnANotRentedVehicle() {
		rentalAgency = new RentalAgency(CAR);
		rentalAgency.returnVehicle(CUSTOMER);
		rentalAgency.returnVehicle(CUSTOMER);
	}

	@Test
	void testAllRentedVehicles() {
		rentalAgency = new RentalAgency(CAR, MOTORBIKE);
		assertThat(rentalAgency.allRentedVehicles()).isEmpty();
		
		rentalAgency.rentVehicle(CUSTOMER, CAR);
		assertThat(rentalAgency.allRentedVehicles()).hasSize(1).contains(CAR).doesNotContain(MOTORBIKE);
		
		rentalAgency.rentVehicle(CUSTOMER2, MOTORBIKE);
		assertThat(rentalAgency.allRentedVehicles()).hasSize(2).contains(CAR).contains(MOTORBIKE);
	}

}