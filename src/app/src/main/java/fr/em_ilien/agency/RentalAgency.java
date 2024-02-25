package fr.em_ilien.agency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import fr.em_ilien.agency.exceptions.UnknownVehicleException;

public class RentalAgency {

	private Set<Vehicle> vehicles;

	public RentalAgency(Vehicle... vehicles) {
		this.vehicles = new HashSet<Vehicle>(List.of(vehicles));
	}

	public List<Vehicle> getVehicles() {
		return new ArrayList<Vehicle>(vehicles);
	}

	public boolean add(Vehicle vehicle) {
		return vehicles.add(vehicle);
	}

	public void remove(Vehicle vehicle) {
		if (!vehicles.remove(vehicle))
			throw new UnknownVehicleException();
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

}
