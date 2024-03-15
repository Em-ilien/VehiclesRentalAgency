package fr.em_ilien.agency;

public class Motorbike extends AbstractVehicle implements Vehicle {

	private static final double PRICE_PER_CM3_OF_CYLINDER_CAPACITY = 0.25;
	private static final int MIN_ALLOWED_CYLINDER_CAPACITY = 50;

	private int cylinderCapacity;

	/**
	 * Create a new Motorbike
	 * 
	 * @param brand            the brand of the motorbike
	 * @param model            the model of the motorbike
	 * @param productionYear   the production year of the motorbike
	 * @param cylinderCapacity the cylinder capacity of the motorbike
	 */
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

	/**
	 * 
	 * @return the cylinder capacity
	 */
	public int getCylinderCapacity() {
		return cylinderCapacity;
	}

	@Override
	public double dailyRentalPrice() {
		return PRICE_PER_CM3_OF_CYLINDER_CAPACITY * cylinderCapacity;
	}

	protected String toStringDetails() {
		return cylinderCapacity + "cm³";
	}

}
