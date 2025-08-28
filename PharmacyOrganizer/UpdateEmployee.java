package PharmacyOrganizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GUI.JButton;
import GUI.JComboBox;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JTextField;

public class UpdateEmployee implements Option {

	@Override
	public String getOption() {
		return "Edit Employee";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		JGridFrame frame = new JGridFrame(parent, 6, 4, getOption(), false);
		
		String select = "SELECT `ID`, `FirstName`, `LastName`, `Email`, `PhoneNumber`,"
				+ " `Salary`, `DateOfBirth`, `WorkHours`, `StartTime`, `EndTime`,"
				+ " `Job` FROM `employees`;";
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			while (rs.next()) {
				Employee emp;
				int job = rs.getInt("Job");
				switch(job) {
				case 1:
					emp = new Manager();
					break;
				case 2:
					emp = new Pharmacist();
					break;
				case 3:
					emp = new Cashier();
					break;
					default:
						emp = new Employee() {
							@Override
							String getJob() {
								return null;
							}
						};
						break;
				}
				emp.setID(rs.getInt("ID"));
				emp.setFirstName(rs.getString("FirstName"));
				emp.setLastName(rs.getString("LastName"));
				emp.setEmail(rs.getString("Email"));
				emp.setPhoneNumber(rs.getString("PhoneNumber"));
				emp.setSalary(rs.getDouble("Salary"));
				emp.setDateOfBirth(rs.getString("DateOfBirth"));
				emp.setWorkHours(rs.getInt("WorkHours"));
				emp.setStartTime(rs.getString("StartTime"));
				emp.setEndTime(rs.getString("EndTime"));
				employees.add(emp);
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage());
		}
		
		String[] IDs = new String[employees.size()];
		for (int i=0;i<employees.size();i++) {
			IDs[i] = String.valueOf(employees.get(i).getID());
		}
		
		frame.addToGrid(new JLabel("ID:", 18));
		JComboBox ID = new JComboBox(IDs, 18);
		frame.addToGrid(ID);
		
		frame.addToGrid(new JLabel("First Name:", 18));
		JTextField firstName = new JTextField(18);
		frame.addToGrid(firstName);
		
		frame.addToGrid(new JLabel("Last Name:", 18));
		JTextField lastName = new JTextField(18);
		frame.addToGrid(lastName);
		
		frame.addToGrid(new JLabel("Email:", 18));
		JTextField email = new JTextField(18);
		frame.addToGrid(email);
		
		frame.addToGrid(new JLabel("Phone Number:", 18));
		JTextField phoneNumber = new JTextField(18);
		frame.addToGrid(phoneNumber);
		
		frame.addToGrid(new JLabel("Salary:", 18));
		JTextField salary = new JTextField(18);
		frame.addToGrid(salary);
		
		frame.addToGrid(new JLabel("Date of Birth:", 18));
		JTextField dateOfBirth = new JTextField(18);
		frame.addToGrid(dateOfBirth);
		
		frame.addToGrid(new JLabel("Work Hours:", 18));
		JTextField workHours = new JTextField(18);
		frame.addToGrid(workHours);
		
		frame.addToGrid(new JLabel("Start Time:", 18));
		JTextField startTime = new JTextField(18);
		frame.addToGrid(startTime);
		
		frame.addToGrid(new JLabel("End Time:", 18));
		JTextField endTime = new JTextField(18);
		frame.addToGrid(endTime);
		
		frame.addToGrid(new JLabel("Job:", 18));
		JTextField job = new JTextField(18);
		job.setEditable(false);
		frame.addToGrid(job);
		
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
			public void mouseClicked(MouseEvent e) {
				
				Employee emp = employees.get(ID.getSelectedIndex());
				
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
				try {
					Double.parseDouble(salary.getText());
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(frame, "Salary must be double");
					return;
				}
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				try {
					LocalDate.parse(dateOfBirth.getText(), dateFormatter);
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(frame, "Date of Birth must be (yyyy-dd-MM)");
					return;
				}
				try {
					Integer.parseInt(workHours.getText());
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(frame, "Work Hours must be int");
					return;
				}
				try {
					LocalTime.parse(startTime.getText(), timeFormatter);
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(frame, "Start Time must be (HH:mm)");
					return;
				}
				try {
					LocalTime.parse(endTime.getText(), timeFormatter);
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(frame, "End Time must be (HH:mm)");
					return;
				}
				
				emp.setFirstName(firstName.getText());
				emp.setLastName(lastName.getText());
				emp.setEmail(email.getText());
				emp.setPhoneNumber(phoneNumber.getText());
				emp.setSalary(Double.parseDouble(salary.getText()));
				emp.setDateOfBirth(dateOfBirth.getText());
				emp.setWorkHours(Integer.parseInt(workHours.getText()));
				emp.setStartTime(startTime.getText());
				emp.setEndTime(endTime.getText());
				
				try {
					String update = "UPDATE `employees` SET `FirstName`='"+
							emp.getFirstName()+"',`LastName`='"+emp.getLastName()+"',`Email`='"
									+emp.getEmail()+"',`PhoneNumber`='"+emp.getPhoneNumber()+
									"',`Salary`='"+emp.getSalary()+"',`DateOfBirth`='"+
									emp.getDateOfBirth()+"',`WorkHours`='"+emp.getWorkHours()+
									"',`StartTime`='"+emp.getStartTime()+"',`EndTime`='"+
									emp.getEndTime()+"' WHERE `ID` = '"+emp.getID()+"';";
							database.getStatement().execute(update);
					JOptionPane.showMessageDialog(frame, 
							"Employee updated successfully");
					frame.dispose();
				} catch (SQLException exception) {
					JOptionPane.showMessageDialog(frame, exception.getMessage());
				}
				
			}
		});
		frame.addToGrid(confirm);
		
		frame.setVisible(true);
		
		ID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Employee e = employees.get(ID.getSelectedIndex());
				ID.setSelectedItem(e.getID());
				firstName.setText(e.getFirstName());
				lastName.setText(e.getLastName());
				email.setText(e.getEmail());
				phoneNumber.setText(e.getPhoneNumber());
				salary.setText(String.valueOf(e.getSalary()));
				dateOfBirth.setText(e.getDateOfBirth());
				workHours.setText(String.valueOf(e.getWorkHours()));
				startTime.setText(e.getStartTime());
				endTime.setText(e.getEndTime());
				job.setText(e.getJob());
			}
		});
	}

}

