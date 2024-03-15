package fr.em_ilien.agency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import fr.em_ilien.agency.exceptions.UnknownVehicleException;

public class RentalAgency {

	private Set<Vehicle> vehicles;
	private Map<Customer, Vehicle> rentedVehicles;

	/**
	 * Create the rental agency with the passed vehicles if they are
	 * 
	 * @param vehicles a list of vehicles
	 */
	public RentalAgency(Vehicle... vehicles) {
		this.vehicles = new HashSet<Vehicle>(List.of(vehicles));
		this.rentedVehicles = new HashMap<Customer, Vehicle>();
	}

	/**
	 * 
	 * @return a copy if the vehicles
	 */
	public Set<Vehicle> getVehicles() {
		return new HashSet<Vehicle>(vehicles);
	}

	/**
	 * Add the vehicle to the rental agency
	 * 
	 * @param vehicle the vehicle added
	 * @return true if the operation succeed.
	 */
	public boolean add(Vehicle vehicle) {
		return vehicles.add(vehicle);
	}

	/**
	 * Remove the vehicle from the rental agency
	 * 
	 * @param vehicle the removed vehicle
	 * @throws UnknownVehicleException if the vehicle doesn't exist on the rental
	 *                                 agency
	 */
	public void remove(Vehicle vehicle) throws UnknownVehicleException {
		if (!vehicles.remove(vehicle))
			throw new UnknownVehicleException(vehicle);
	}

	/**
	 * 
	 * @param vehicle
	 * @return true only if the vehicle exists on the rentalAgency
	 */
	public boolean contains(Vehicle vehicle) {
		return vehicles.contains(vehicle);
	}

	/**
	 * Filter the rental agency's vehicles
	 * 
	 * @param criterion the filter criterion
	 * @return the matching set of vehicles
	 */
	public List<Vehicle> select(Predicate<Vehicle> criterion) {
		return vehicles.stream().filter(criterion).toList();
	}

	/**
	 * Show the rental agency's vehicle matching the filter criterion
	 */
	public void printSelectedVehicles(Predicate<Vehicle> criterion) {
		for (Vehicle vehicle : select(criterion))
			System.out.println(vehicle);
	}

	/**
	 * Rent a vehicle existing on the rental agency to a customer
	 * 
	 * @param customer the customer renter
	 * @param vehicle  the vehicle rented
	 * @return the daily rental price
	 * @throws UnknownVehicleException if the vehicle is not existing on the rental
	 *                                 agency
	 * @throws IllegalStateException   if the vehicle is already rented or if the
	 *                                 customer is already renting an another
	 *                                 vehicle
	 */
	public double rentVehicle(Customer customer, Vehicle vehicle)
			throws UnknownVehicleException, IllegalStateException {
		if (!vehicles.contains(vehicle))
			throw new UnknownVehicleException(vehicle);
		if (aVehicleIsRentedBy(customer))
			throw new IllegalStateException(customer + " is already renting " + rentedVehicles.get(customer) + ".");
		if (vehicleIsRented(vehicle))
			throw new IllegalStateException(vehicle + " is already rented by " + getRenterOf(vehicle) + ".");

		rentedVehicles.put(customer, vehicle);
		return vehicle.dailyRentalPrice();
	}

	private Customer getRenterOf(Vehicle vehicle) {
		return rentedVehicles.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), vehicle))
				.map(Map.Entry::getKey).findFirst().orElse(null);
	}

	/**
	 * 
	 * @param customer
	 * @return true only if the customer is currently renting a vehicle
	 */
	public boolean aVehicleIsRentedBy(Customer customer) {
		return rentedVehicles.containsKey(customer);
	}

	/**
	 * 
	 * @param vehicle
	 * @return true only if the vehicle is currently rented
	 */
	public boolean vehicleIsRented(Vehicle vehicle) {
		return rentedVehicles.containsValue(vehicle);
	}

	public void returnVehicle(Customer customer) {
		rentedVehicles.remove(customer);
	}

	/**
	 * 
	 * @return the collection of the rental agency's rented vehicles
	 */
	public Collection<Vehicle> allRentedVehicles() {
		return rentedVehicles.values();
	}

}
