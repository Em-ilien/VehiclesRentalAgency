package fr.em_ilien.agency;

import fr.em_ilien.util.TimeProvider;

public abstract class AbstractVehicle {
	private static final int MINIMAL_PRODUCTION_YEAR_VEHICLE = 1900;

	protected int productionYear;
	protected String brand;
	protected String model;

	public AbstractVehicle(String brand, String model, int productionYear) {
		super();
		checkFilledProductionYearIsCorrect(productionYear);
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
	}

	protected void checkFilledProductionYearIsCorrect(int productionYear) {
		final int currentYear = TimeProvider.currentYearValue();

		if (productionYear < MINIMAL_PRODUCTION_YEAR_VEHICLE || productionYear > currentYear)
			throw new IllegalArgumentException(
					"L'année de production " + productionYear + " n'est pas comprise entre l'intervalle [["
							+ MINIMAL_PRODUCTION_YEAR_VEHICLE + ";" + currentYear + "]].");
	}

	public int getProductionYear() {
		return productionYear;
	}

	public Object getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public String toString() {
		return separateWithSpaces(this.getClass().getSimpleName(), brand, model, String.valueOf(productionYear),
				"(" + toStringDetails() + ")", ":", String.valueOf(dailyRentalPrice()), "€");
	}

	protected abstract double dailyRentalPrice();

	protected abstract String toStringDetails();

	protected String separateWithSpaces(String... sub) {
		final StringBuilder stb = new StringBuilder();
		for (String string : sub)
			stb.append(" " + string);
		return stb.toString().substring(1);
	}

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