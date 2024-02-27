package fr.em_ilien.agency.exceptions;

import fr.em_ilien.agency.Vehicle;

public class UnknownVehicleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Vehicle vehicle;

	public UnknownVehicleException(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public String getMessage() {
		return vehicle + " n'existe pas dans l'agence.";
	}
}
