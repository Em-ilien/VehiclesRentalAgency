package fr.em_ilien.agency.criterion;

import java.util.function.Predicate;

import fr.em_ilien.agency.Vehicle;

public class MaxPriceCriterion implements Predicate<Vehicle> {

	private double dailyRentalPrice;

	/**
	 * Create a filter on daily rental price
	 * @param dailyRentalPrice
	 */
	public MaxPriceCriterion(double dailyRentalPrice) {
		this.dailyRentalPrice = dailyRentalPrice;
	}

	@Override
	public boolean test(Vehicle t) {
		return dailyRentalPrice >= t.dailyRentalPrice();
	}

}
