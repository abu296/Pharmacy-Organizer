package PharmacyOrganizer;

public class Pharmacist extends Employee {
	
	public Pharmacist() {
		super();
		this.options = new Option[] {
				new CreateMedicine(),
				new ReadAllMedicines(),
				new ReadMedicineByID(),
				new ReadMedicineByName(),
				new ReadMedicineByType(),
				new ReadMedicineByCompany(),
				new UpdateMedicine(),
				new DeleteMedicine(),
				new CreateOrder(),
				new ReadAllOrders(),
				new ReadOrder(),
				new ReadOrderItems(),
				new DeleteOrder(),
				new ChangePassword()
		};
	}

	@Override
	String getJob() {
		return "Pharmacist";
	}

}
