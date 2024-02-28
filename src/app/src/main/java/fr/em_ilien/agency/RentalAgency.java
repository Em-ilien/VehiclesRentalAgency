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

	public RentalAgency(Vehicle... vehicles) {
		this.vehicles = new HashSet<Vehicle>(List.of(vehicles));
		this.rentedVehicles = new HashMap<Customer, Vehicle>();
	}

	public List<Vehicle> getVehicles() {
		return new ArrayList<Vehicle>(vehicles);
	}

	public boolean add(Vehicle vehicle) {
		return vehicles.add(vehicle);
	}

	public void remove(Vehicle vehicle) throws UnknownVehicleException {
		if (!vehicles.remove(vehicle))
			throw new UnknownVehicleException(vehicle);
	}

	public boolean contains(Vehicle vehicle) {
		return vehicles.contains(vehicle);
	}

	public List<Vehicle> select(Predicate<Vehicle> criterion) {
		return vehicles.stream().filter(criterion).toList();
	}

	public void printSelectedVehicles(Predicate<Vehicle> criterion) {
		for (Vehicle vehicle : select(criterion))
			System.out.println(vehicle);
	}

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

	public boolean aVehicleIsRentedBy(Customer customer) {
		return rentedVehicles.containsKey(customer);
	}

	public boolean vehicleIsRented(Vehicle vehicle) {
		return rentedVehicles.containsValue(vehicle);
	}

	public void returnVehicle(Customer customer) {
		rentedVehicles.remove(customer);
	}

	public Collection<Vehicle> allRentedVehicles() {
		return rentedVehicles.values();
	}

}
