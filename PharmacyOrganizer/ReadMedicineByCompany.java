package PharmacyOrganizer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.JButton;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JTextField;

public class ReadMedicineByCompany implements Option {

	@Override
	public String getOption() {
		return "Search Medicine By Company";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		JGridFrame frame = new JGridFrame(parent, 2, 2, getOption(), false);
		frame.addToGrid(new JLabel("Company:", 18));
		JTextField company = new JTextField(18);
		frame.addToGrid(company);
		
		JButton cancel = new JButton("Cancel", 18);
		cancel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		frame.addToGrid(cancel);
		
		JButton select = new JButton("Select", 18);
		select.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}	
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				String select = "SELECT * FROM `medicines` WHERE `Company` LIKE '"
						+company.getText()+"';";
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
		});
		frame.addToGrid(select);
		
	}

}
