package PharmacyOrganizer;

public class Cashier extends Employee {
	
	public Cashier() {
		super();
		this.options = new Option[] {
				new ReadAllOrders(),
				new ReadOrder(),
				new ReadOrderItems(),
				new PayOrder(),
				new ChangePassword()
		};
	}

	@Override
	String getJob() {
		return "Cashier";
	}

}
