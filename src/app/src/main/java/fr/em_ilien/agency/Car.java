package fr.em_ilien.agency;

import fr.em_ilien.util.TimeProvider;

public class Car {
	private static final int MINIMAL_NUMBER_OF_SEATS = 1;

	private static final int MINIMAL_PRODUCTION_YEAR_VEHICLE = 1900;

	private String brand;
	private String model;
	private int productionYear;
	private int numberOfSeats;

	public Car(String brand, String model, int productionYear, int numberOfSeats) {
		final int currentYear = TimeProvider.currentYearValue();

		if (numberOfSeats < MINIMAL_NUMBER_OF_SEATS)
			throw new IllegalArgumentException("Le nombre de siège " + numberOfSeats + " n'est pas supérieur ou égal à "
					+ MINIMAL_NUMBER_OF_SEATS + ".");
		if (productionYear < MINIMAL_PRODUCTION_YEAR_VEHICLE || productionYear > currentYear)
			throw new IllegalArgumentException(
					"L'année de production " + productionYear + " n'est pas comprise entre l'intervalle [["
							+ MINIMAL_PRODUCTION_YEAR_VEHICLE + ";" + currentYear + "]].");
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.numberOfSeats = numberOfSeats;
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

}
