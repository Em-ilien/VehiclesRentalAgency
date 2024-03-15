package fr.em_ilien.agency.criterion;

import java.util.function.Predicate;

import fr.em_ilien.agency.Vehicle;

public class BrandCriterion implements Predicate<Vehicle> {

	private String brand;

	/**
	 * Create a filter on brand
	 * @param brand
	 */
	public BrandCriterion(String brand) {
		if (brand == null)
			throw new IllegalArgumentException();
		this.brand = brand;
	}

	
	@Override
	public boolean test(Vehicle t) {
		return t.getBrand().equals(brand);
	}
}
