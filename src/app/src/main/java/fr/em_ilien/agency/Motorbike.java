package fr.em_ilien.agency;

public class Motorbike extends AbstractVehicle {

	private static final double PRICE_PER_CM3_OF_CYLINDREE = 0.25;
	private static final int MIN_ALLOWED_CYLINDREE = 50;

	private int cylindree;

	public Motorbike(String brand, String model, int productionYear, int cylindree) {
		super(brand, model, productionYear);
		checkFilledCylyndreeIsCorrect(cylindree);

		this.cylindree = cylindree;
	}

	private void checkFilledCylyndreeIsCorrect(int cylindree) {
		if (cylindree < MIN_ALLOWED_CYLINDREE)
			throw new IllegalArgumentException(
					"La cylyndrée " + cylindree + " n'est pas supérieure ou égale à " + MIN_ALLOWED_CYLINDREE);
	}

	public int getCylindree() {
		return cylindree;
	}

	public double dailyRentalPrice() {
		return PRICE_PER_CM3_OF_CYLINDREE * cylindree;
	}

	protected String toStringDetails() {
		return cylindree + "cm³";
	}

}
