package fr.em_ilien.agency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

}
