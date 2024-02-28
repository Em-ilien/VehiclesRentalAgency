package fr.em_ilien.agency;

import fr.em_ilien.util.TimeProvider;

public class Car extends AbstractVehicle implements Vehicle {
	private static final int PRICE_PER_SEAT = 20;
	private static final int MAX_CAR_AGE_STILL_CONSIDERED_NEW = 5;
	private static final int MINIMAL_NUMBER_OF_SEATS = 1;

	private int numberOfSeats;

	public Car(String brand, String model, int productionYear, int numberOfSeats) {
		super(brand, model, productionYear);
		checkFilledNumberOfSeatIsCorrect(numberOfSeats);

		this.numberOfSeats = numberOfSeats;
	}

	private void checkFilledNumberOfSeatIsCorrect(int numberOfSeats) {
		if (numberOfSeats < MINIMAL_NUMBER_OF_SEATS)
			throw new IllegalArgumentException("The number of seats " + numberOfSeats + " isn't greater than or equal to "
					+ MINIMAL_NUMBER_OF_SEATS + ".");
	}

	public boolean isNew() {
		return TimeProvider.currentYearValue() - productionYear < MAX_CAR_AGE_STILL_CONSIDERED_NEW;
	}

	public double dailyRentalPrice() {
		int price = PRICE_PER_SEAT * numberOfSeats;
		if (isNew())
			return price * 2;
		return price;
	}

	protected String toStringDetails() {
		return numberOfSeats + " seats";
	}

}
