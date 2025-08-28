package PharmacyOrganizer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order {
	
	private int ID;
	private Employee cashier;
	private Employee pharmacist;
	private LocalDateTime dateTime;
	private double total;
	private ArrayList<Medicine> medicines;
	private double paid;
	private double change;
	private LocalDateTime payDateTime;
	private DateTimeFormatter dateTimeFormatter = 
							DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm");
	
	public Order() {}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public Employee getCashier() {
		return cashier;
	}
	
	public void setCashier(Employee cashier) {
		this.cashier = cashier;
	}
	
	public Employee getPharmacist() {
		return pharmacist;
	}
	
	public void setPharmacist(Employee pharmacist) {
		this.pharmacist = pharmacist;
	}
	
	public String getDateTime() {
		return dateTimeFormatter.format(dateTime);
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public ArrayList<Medicine> getMedicines() {
		return medicines;
	}
	
	public void setMedecines(ArrayList<Medicine> medicines) {
		this.medicines = medicines;
	}
	
	public double getPaid() {
		return paid;
	}
	
	public void setPaid(double paid) {
		this.paid = paid;
	}
	
	public double getChange() {
		return change;
	}
	
	public void setChange(double change) {
		this.change = change;
	}
	
	public String getPayDateTime() {
		return dateTimeFormatter.format(payDateTime);
	}
	
	public void setPayDateTime(String payDateTime) {
		this.payDateTime = LocalDateTime.parse(payDateTime, dateTimeFormatter);
	}
	
	public String getDateTimeFormatter() {
		return "yyyy-dd-MM HH:mm";
	}

}
