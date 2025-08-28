package PharmacyOrganizer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Medicine {
	
	private int ID;
	private String name;
	private String type;
	private int qty;
	private LocalDate manufactureDate;
	private LocalDate expiryDate;
	private String company;
	private double cost;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
	
	public Medicine() {}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public String getManufactureDate() {
		return dateFormatter.format(manufactureDate);
	}
	
	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = LocalDate.parse(manufactureDate, dateFormatter);
	}
	
	public String getExpiryDate() {
		return dateFormatter.format(expiryDate);
	}
	
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = LocalDate.parse(expiryDate, dateFormatter);
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public String getDateFormat() {
		return "yyyy-dd-MM";
	}
	
	public void print() {
		System.out.println("ID:\t\t\t"+getID());
		System.out.println("Name:\t\t\t"+getName());
		System.out.println("Type:\t\t\t"+getType());
		System.out.println("Qty:\t\t\t"+getQty());
		System.out.println("Manufacture Date:\t"+getManufactureDate());
		System.out.println("Expiry Date:\t\t"+getExpiryDate());
		System.out.println("Company:\t\t"+getCompany());
		System.out.println("Cost:\t\t\t"+getCost());
		System.out.println("-------------------------------------");
	}

}
