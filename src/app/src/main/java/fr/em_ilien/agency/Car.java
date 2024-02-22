package fr.em_ilien.agency;

import fr.em_ilien.util.TimeProvider;

public class Car {
	private static final int PRICE_PER_SEAT = 20;
	private static final int MAX_CAR_AGE_STILL_CONSIDERED_NEW = 5;
	private static final int MINIMAL_NUMBER_OF_SEATS = 1;
	private static final int MINIMAL_PRODUCTION_YEAR_VEHICLE = 1900;

	private String brand;
	private String model;
	private int productionYear;
	private int numberOfSeats;

	public Car(String brand, String model, int productionYear, int numberOfSeats) {
		checkFilledNumberOfSeatIsCorrect(numberOfSeats);
		checkFilledProductionYearIsCorrect(productionYear);

		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.numberOfSeats = numberOfSeats;
	}

	private void checkFilledProductionYearIsCorrect(int productionYear) {
		final int currentYear = TimeProvider.currentYearValue();

		if (productionYear < MINIMAL_PRODUCTION_YEAR_VEHICLE || productionYear > currentYear)
			throw new IllegalArgumentException(
					"L'année de production " + productionYear + " n'est pas comprise entre l'intervalle [["
							+ MINIMAL_PRODUCTION_YEAR_VEHICLE + ";" + currentYear + "]].");
	}

	private void checkFilledNumberOfSeatIsCorrect(int numberOfSeats) {
		if (numberOfSeats < MINIMAL_NUMBER_OF_SEATS)
			throw new IllegalArgumentException("Le nombre de siège " + numberOfSeats + " n'est pas supérieur ou égal à "
					+ MINIMAL_NUMBER_OF_SEATS + ".");
	}

	public Object getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public int getProductionYear() {
		return productionYear;
	}

	@Override
	public String toString() {
		return separateWithSpaces("Car", brand, model, String.valueOf(productionYear), "(" + numberOfSeats + " seats)",
				":", String.valueOf(dailyRentalPrice()), "€");
	}

	private String separateWithSpaces(String... sub) {
		final StringBuilder stb = new StringBuilder();
		for (String string : sub)
			stb.append(" " + string);
		return stb.toString().substring(1);
	}

	public boolean isNew() {
		return TimeProvider.currentYearValue() - productionYear < MAX_CAR_AGE_STILL_CONSIDERED_NEW;
	}

	public int dailyRentalPrice() {
		int price = PRICE_PER_SEAT * numberOfSeats;
		if (isNew())
			return price * 2;
		return price;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;
		if (this == obj)
			return true;

		Car car2 = (Car) obj;

		if (!brand.equals(car2.getBrand()))
			return false;
		if (!model.equals(car2.getModel()))
			return false;
		if (productionYear != car2.getProductionYear())
			return false;
		return true;
	}

}
