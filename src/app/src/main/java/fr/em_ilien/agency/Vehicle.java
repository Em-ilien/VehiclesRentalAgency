package fr.em_ilien.agency;

public interface Vehicle {

	/**
	 * 
	 * @return the brand of vehicle
	 */
	public String getBrand();

	/**
	 * 
	 * @return the model of vehicle
	 */
	public String getModel();

	/**
	 * 
	 * @return the production year of vehicle
	 */
	public int getProductionYear();

	/**
	 * 
	 * @return the daily rental price
	 */
	public double dailyRentalPrice();

	/**
	 * 
	 * @return true if the specified object is a vehicle having the same brand, model and production year ; false otherwise
	 */
	public boolean equals(Object obj);

	/**
	 * 
	 * @return the specification of the vehicle according to his type
	 */
	public String toString();

}
