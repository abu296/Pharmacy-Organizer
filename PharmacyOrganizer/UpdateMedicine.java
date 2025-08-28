package PharmacyOrganizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GUI.JButton;
import GUI.JComboBox;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JTextField;

public class UpdateMedicine implements Option {

	@Override
	public String getOption() {
		return "Update Medicine";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		JGridFrame frame = new JGridFrame(parent, 9, 2, getOption(), false);
		
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
		frame.addToGrid(name);
		
		frame.addToGrid(new JLabel("Type:", 18));
		JTextField type = new JTextField(18);
		frame.addToGrid(type);
		
		frame.addToGrid(new JLabel("Qty:", 18));
		JTextField qty = new JTextField(18);
		frame.addToGrid(qty);
		
		frame.addToGrid(new JLabel("Manufacture Date:", 18));
		JTextField manufactureDate = new JTextField(18);
		frame.addToGrid(manufactureDate);
		
		frame.addToGrid(new JLabel("Expiry Date:", 18));
		JTextField expiryDate = new JTextField(18);
		frame.addToGrid(expiryDate);
		
		frame.addToGrid(new JLabel("Company:", 18));
		JTextField company = new JTextField(18);
		frame.addToGrid(company);
		
		frame.addToGrid(new JLabel("Cost:", 18));
		JTextField cost = new JTextField(18);
		frame.addToGrid(cost);
		
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
		
		JButton confirm = new JButton("Confirm", 18);
		confirm.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent event) {
				if (name.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Name cannot be empty");
					return;
				}
				if (type.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Type cannot be empty");
					return;
				}
				if (qty.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Qty cannot be empty");
					return;
				}
				try {
					Integer.parseInt(qty.getText());
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(frame, "Qty must be int");
					return;
				}
				if (manufactureDate.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Manufacture Date cannot be empty");
					return;
				}
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
				try {
					LocalDate.parse(manufactureDate.getText(), dateFormatter);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Manufacture Date must be (yyyy-dd-MM)");
					return;
				}
				if (expiryDate.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Expiry Date cannot be empty");
					return;
				}
				try {
					LocalDate.parse(expiryDate.getText(), dateFormatter);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Expiry Date must be (yyyy-dd-MM)");
					return;
				}
				if (company.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Company cannot be empty");
					return;
				}
				if (cost.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Cost cannot be empty");
					return;
				}
				try {
					Double.parseDouble(cost.getText());
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(frame, "Cost must be double");
					return;
				}
				
				Medicine m = medicines.get(ID.getSelectedIndex());
				m.setName(name.getText());
				m.setType(type.getText());
				m.setManufactureDate(manufactureDate.getText());
				m.setExpiryDate(expiryDate.getText());
				m.setCompany(company.getText());
				m.setCost(Double.parseDouble(cost.getText()));
				
				try {
					String update = "UPDATE `medicines` SET `Name`='"+m.getName()+
							"',`Type`='"+m.getType()+"',`Qty`='"+m.getQty()+
							"',`ManufactureDate`='"+m.getManufactureDate()+"',`ExpiryDate`='"
							+m.getExpiryDate()+"',`Company`='"+m.getCompany()+"',`Cost`='"+
							m.getCost()+"' WHERE `ID` = "+m.getID()+" ;";
					
					database.getStatement().execute(update);
					JOptionPane.showMessageDialog(frame, 
							"Medicine updated successfully");
					frame.dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		frame.addToGrid(confirm);
		
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
