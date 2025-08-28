package PharmacyOrganizer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import GUI.JButton;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JTextField;

public class CreateMedicine implements Option {

	@Override
	public String getOption() {
		return "Add new Medicine";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		JGridFrame frame = new JGridFrame(parent, 8, 2, getOption(), false);
		
		frame.addToGrid(new JLabel("Name:", 20));
		JTextField name = new JTextField(20);
		frame.addToGrid(name);
		
		frame.addToGrid(new JLabel("Type:", 20));
		JTextField type = new JTextField(20);
		frame.addToGrid(type);
		
		frame.addToGrid(new JLabel("Qty :", 20));
		JTextField qty = new JTextField(20);
		frame.addToGrid(qty);
		
		frame.addToGrid(new JLabel("Manufacture Date (yyyy-dd-MM):", 20));
		JTextField manufactureDate = new JTextField(20);
		frame.addToGrid(manufactureDate);
		
		frame.addToGrid(new JLabel("Expiry Date (yyyy-dd-MM):", 20));
		JTextField expiryDate = new JTextField(20);
		frame.addToGrid(expiryDate);
		
		frame.addToGrid(new JLabel("Company:", 20));
		JTextField company = new JTextField(20);
		frame.addToGrid(company);
		
		frame.addToGrid(new JLabel("Cost :", 20));
		JTextField cost = new JTextField(20);
		frame.addToGrid(cost);
		
		JButton cancel = new JButton("Cancel", 20);
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
		
		JButton confirm = new JButton("Confirm", 20);
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
			public void mouseClicked(MouseEvent e1) {
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
				
				String insert = "INSERT INTO `medicines`(`Name`, `Type`, `Qty`,"
						+ " `ManufactureDate`, `ExpiryDate`, `Company`, `Cost`) VALUES ('"+
						name.getText()+"','"+type.getText()+"','"+qty.getText()+"','"+manufactureDate.getText()+"','"+expiryDate.getText()+
						"','"+company.getText()+"','"+cost.getText()+"');";
				try {
					database.getStatement().execute(insert);
					JOptionPane.showMessageDialog(frame, "Medicine added successfully");
					frame.dispose();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
			}
		});
		frame.addToGrid(confirm);
		
		frame.setVisible(true);
		
	}

}
