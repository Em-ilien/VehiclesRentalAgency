package fr.em_ilien.agency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.em_ilien.agency.exceptions.UnknownVehicleException;

public class RentalAgency {

	private Set<AbstractVehicle> vehicles;

	public RentalAgency(AbstractVehicle... vehicles) {
		this.vehicles = new HashSet<AbstractVehicle>(List.of(vehicles));
	}

	public List<AbstractVehicle> getVehicles() {
		return new ArrayList<AbstractVehicle>(vehicles);
	}

	public boolean add(AbstractVehicle vehicle) {
		return vehicles.add(vehicle);
	}

	public void remove(AbstractVehicle vehicle) {
		if (!vehicles.remove(vehicle))
			throw new UnknownVehicleException();
	}

	public boolean contains(AbstractVehicle vehicle) {
		return vehicles.contains(vehicle);
	}

}
