package PharmacyOrganizer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;

public class ReadAllMedicines implements Option {

	@Override
	public String getOption() {
		return "View All Medicines";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		String select = "SELECT * FROM `medicines`;";
		ArrayList<Medicine> medicines = new ArrayList<>();
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			while (rs.next()) {
				Medicine m = new Medicine();
				m.setID(rs.getInt("ID"));
				m.setName(rs.getString("Name"));
				m.setType(rs.getString("Type"));
				m.setQty(rs.getInt("Qty"));
				m.setManufactureDate(rs.getString("ManufactureDate"));
				m.setExpiryDate(rs.getString("ExpiryDate"));
				m.setCompany(rs.getString("Company"));
				m.setCost(rs.getDouble("Cost"));
				medicines.add(m);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JGridFrame frame = new JGridFrame(parent, medicines.size()+1, 7, 
				getOption(), true);
		
		frame.addToGrid(new JLabel("Name", 15));
		frame.addToGrid(new JLabel("Type", 15));
		frame.addToGrid(new JLabel("Qty", 15));
		frame.addToGrid(new JLabel("Manufacture Date", 15));
		frame.addToGrid(new JLabel("Expiry Date", 15));
		frame.addToGrid(new JLabel("Company", 15));
		frame.addToGrid(new JLabel("Cost", 15));
		
		for (Medicine m : medicines) {
			frame.addToGrid(new JLabel(m.getName(), 15));
			frame.addToGrid(new JLabel(m.getType(), 15));
			frame.addToGrid(new JLabel(String.valueOf(m.getQty()), 15));
			frame.addToGrid(new JLabel(m.getManufactureDate(), 15));
			frame.addToGrid(new JLabel(m.getExpiryDate(), 15));
			frame.addToGrid(new JLabel(m.getCompany(), 15));
			frame.addToGrid(new JLabel("Rs."+m.getCost(), 15));
		}
	}

}
