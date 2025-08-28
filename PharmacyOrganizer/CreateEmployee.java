package PharmacyOrganizer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import GUI.JButton;
import GUI.JComboBox;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JPasswordField;
import GUI.JTextField;

public class CreateEmployee implements Option {

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		JGridFrame frame = new JGridFrame(parent, 6, 4, getOption(), false);
		
		frame.addToGrid(new JLabel("First Name:", 20));
		JTextField firstName = new JTextField(20);
		frame.addToGrid(firstName);
		
		frame.addToGrid(new JLabel("Last Name:", 20));
		JTextField lastName = new JTextField(20);
		frame.addToGrid(lastName);
		
		frame.addToGrid(new JLabel("Email:", 20));
		JTextField email = new JTextField(20);
		frame.addToGrid(email);
		
		frame.addToGrid(new JLabel("Phone Number:", 20));
		JTextField phoneNumber = new JTextField(20);
		frame.addToGrid(phoneNumber);
		
		frame.addToGrid(new JLabel("Password:", 20));
		JPasswordField password = new JPasswordField(20);
		frame.addToGrid(password);
		
		frame.addToGrid(new JLabel("Salary :", 20));
		JTextField salary = new JTextField(20);
		frame.addToGrid(salary);
		
		frame.addToGrid(new JLabel("Date of Birth (yyyy-dd-MM):", 20));
		JTextField dateOfBirth = new JTextField(20);
		frame.addToGrid(dateOfBirth);
		
		frame.addToGrid(new JLabel("Work Hours :", 20));
		JTextField workHours = new JTextField(20);
		frame.addToGrid(workHours);
		
		frame.addToGrid(new JLabel("Start Time (HH:mm):", 20));
		JTextField startTime = new JTextField(20);
		frame.addToGrid(startTime);
		
		frame.addToGrid(new JLabel("End Time (HH:mm):", 20));
		JTextField endTime = new JTextField(20);
		frame.addToGrid(endTime);
		
		frame.addToGrid(new JLabel("Job:", 20));
		JComboBox job = new JComboBox(
				new String[] {"", "Manager", "Pharmacist", "Cashier"}, 20);
		frame.addToGrid(job);
		
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
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent event) {
				
				if (firstName.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "First Name cannot be empty");
					return;
				}
				if (lastName.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Last Name cannot be empty");
					return;
				}
				if (email.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Email cannot be empty");
					return;
				}
				if (phoneNumber.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Phone Number cannot be empty");
					return;
				}
				if (password.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Password cannot be empty");
					return;
				}
				if (salary.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Salary cannot be empty");
					return;
				}
				if (dateOfBirth.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Date of Birth cannot be empty");
					return;
				}
				if (workHours.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Work Hours cannot be empty");
					return;
				}
				if (startTime.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Start Time cannot be empty");
					return;
				}
				if (endTime.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "End Time cannot be empty");
					return;
				}
				if (job.getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(frame, "Job cannot be empty");
					return;
				}
				try {
					Double.parseDouble(salary.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Salary must be double");
					return;
				}
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				try {
					LocalDate.parse(dateOfBirth.getText(), dateFormatter);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Date of Birth must be (yyyy-dd-MM)");
					return;
				}
				try {
					Integer.parseInt(workHours.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Work Hours must be int");
					return;
				}
				try {
					LocalTime.parse(startTime.getText(), timeFormatter);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Start Time must be (HH:mm)");
					return;
				}
				try {
					LocalTime.parse(endTime.getText(), timeFormatter);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "End Time must be (HH:mm)");
					return;
				}
				
				String insert = "INSERT INTO `employees`(`FirstName`, `LastName`, `Email`,"
				+ " `PhoneNumber`, `Password`, `Salary`, `DateOfBirth`, `WorkHours`,"
				+ " `StartTime`, `EndTime`, `Job`) VALUES ('"+firstName.getText()+"','"
				+lastName.getText()+"','"+email.getText()+"','"+phoneNumber.getText()
				+"','"+password.getText()+"','"+salary.getText()+"','"+
				dateOfBirth.getText()+"','"+workHours.getText()+"','"+
				startTime.getText()+"','"+endTime.getText()+"','"+
				job.getSelectedIndex()+"');";
				try {
					database.getStatement().execute(insert);
					JOptionPane.showMessageDialog(frame, "Employee added successfully");
					frame.dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.addToGrid(confirm);
		
		frame.setVisible(true);
		
	}
	
	@Override
	public String getOption() {
		return "Add new Employee";
	}

}
