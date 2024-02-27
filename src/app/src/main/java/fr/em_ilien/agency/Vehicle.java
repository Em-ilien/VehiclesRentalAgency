package fr.em_ilien.agency;

public interface Vehicle {

	public String getBrand();

	public String getModel();

	public int getProductionYear();
	
	public double dailyRentalPrice();
	
	public boolean equals(Object obj);
	
	public String toString();

}
