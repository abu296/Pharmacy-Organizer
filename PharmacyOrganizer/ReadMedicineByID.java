package PharmacyOrganizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.JComboBox;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JTextField;

public class ReadMedicineByID implements Option {

	@Override
	public String getOption() {
		return "Search Medicine By ID";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		JGridFrame frame = new JGridFrame(parent, 8, 2, getOption(), false);
		
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
		
		String[] IDs = new String[medicines.size()];
		for (int i=0;i<medicines.size();i++) {
			IDs[i] = String.valueOf(medicines.get(i).getID());
		}
		
		frame.addToGrid(new JLabel("ID:", 18));
		JComboBox ID = new JComboBox(IDs, 18);
		frame.addToGrid(ID);
		
		frame.addToGrid(new JLabel("Name:", 18));
		JTextField name = new JTextField(18);
		name.setEditable(false);
		frame.addToGrid(name);
		
		frame.addToGrid(new JLabel("Type:", 18));
		JTextField type = new JTextField(18);
		type.setEditable(false);
		frame.addToGrid(type);
		
		frame.addToGrid(new JLabel("Qty:", 18));
		JTextField qty = new JTextField(18);
		qty.setEditable(false);
		frame.addToGrid(qty);
		
		frame.addToGrid(new JLabel("Manufacture Date:", 18));
		JTextField manufactureDate = new JTextField(18);
		manufactureDate.setEditable(false);
		frame.addToGrid(manufactureDate);
		
		frame.addToGrid(new JLabel("Expiry Date:", 18));
		JTextField expiryDate = new JTextField(18);
		expiryDate.setEditable(false);
		frame.addToGrid(expiryDate);
		
		frame.addToGrid(new JLabel("Company:", 18));
		JTextField company = new JTextField(18);
		company.setEditable(false);
		frame.addToGrid(company);
		
		frame.addToGrid(new JLabel("Cost:", 18));
		JTextField cost = new JTextField(18);
		cost.setEditable(false);
		frame.addToGrid(cost);
		
		frame.setVisible(true);
		
		ID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Medicine m = medicines.get(ID.getSelectedIndex());
				ID.setSelectedItem(m.getID());
				name.setText(m.getName());
				type.setText(m.getType());
				qty.setText(String.valueOf(m.getQty()));
				manufactureDate.setText(m.getManufactureDate());
				expiryDate.setText(m.getExpiryDate());
				company.setText(m.getCompany());
				cost.setText(String.valueOf(m.getCost()));
			}
		});
		
	}

}
