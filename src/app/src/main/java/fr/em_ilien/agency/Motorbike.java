package fr.em_ilien.agency;

public class Motorbike extends AbstractVehicle implements Vehicle {

	private static final double PRICE_PER_CM3_OF_CYLINDER_CAPACITY = 0.25;
	private static final int MIN_ALLOWED_CYLINDER_CAPACITY = 50;

	private int cylinderCapacity;

	public Motorbike(String brand, String model, int productionYear, int cylinderCapacity) {
		super(brand, model, productionYear);
		checkFilledCylinderCapacityIsCorrect(cylinderCapacity);

		this.cylinderCapacity = cylinderCapacity;
	}

	private void checkFilledCylinderCapacityIsCorrect(int cylinderCapacity) {
		if (cylinderCapacity < MIN_ALLOWED_CYLINDER_CAPACITY)
			throw new IllegalArgumentException("The cylinder capacity " + cylinderCapacity
					+ " isn't greater than or equal to " + MIN_ALLOWED_CYLINDER_CAPACITY);
	}

	public int getCylinderCapacity() {
		return cylinderCapacity;
	}

	public double dailyRentalPrice() {
		return PRICE_PER_CM3_OF_CYLINDER_CAPACITY * cylinderCapacity;
	}

	protected String toStringDetails() {
		return cylinderCapacity + "cm³";
	}

}
