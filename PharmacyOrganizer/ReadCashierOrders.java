package PharmacyOrganizer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GUI.JButton;
import GUI.JComboBox;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JTextField;

public class ReadCashierOrders implements Option {

	@Override
	public String getOption() {
		return "View Cashier Orders";
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
		firstName.setEditable(false);
		frame.addToGrid(firstName);
		
		frame.addToGrid(new JLabel("Last Name:", 18));
		JTextField lastName = new JTextField(18);
		lastName.setEditable(false);
		frame.addToGrid(lastName);
		
		frame.addToGrid(new JLabel("Email:", 18));
		JTextField email = new JTextField(18);
		email.setEditable(false);
		frame.addToGrid(email);
		
		frame.addToGrid(new JLabel("Phone Number:", 18));
		JTextField phoneNumber = new JTextField(18);
		phoneNumber.setEditable(false);
		frame.addToGrid(phoneNumber);
		
		frame.addToGrid(new JLabel("Salary:", 18));
		JTextField salary = new JTextField(18);
		salary.setEditable(false);
		frame.addToGrid(salary);
		
		frame.addToGrid(new JLabel("Date of Birth:", 18));
		JTextField dateOfBirth = new JTextField(18);
		dateOfBirth.setEditable(false);
		frame.addToGrid(dateOfBirth);
		
		frame.addToGrid(new JLabel("Work Hours:", 18));
		JTextField workHours = new JTextField(18);
		workHours.setEditable(false);
		frame.addToGrid(workHours);
		
		frame.addToGrid(new JLabel("Start Time:", 18));
		JTextField startTime = new JTextField(18);
		startTime.setEditable(false);
		frame.addToGrid(startTime);
		
		frame.addToGrid(new JLabel("End Time:", 18));
		JTextField endTime = new JTextField(18);
		endTime.setEditable(false);
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
		
		JButton selectBtn = new JButton("Select", 18);
		selectBtn.addMouseListener(new MouseListener() {
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
				int cashierID = ID.getSelectedIndex();
				Employee cashier = employees.get(cashierID);
				
				ArrayList<Order> orders = new ArrayList<>();
				ArrayList<Integer> pharmacistsIDs = new ArrayList<>();
				
				String select0 = "SELECT * FROM `orders` WHERE `Cashier` = "+cashierID+" ;";
				try {
					ResultSet rs = database.getStatement().executeQuery(select0);
					while (rs.next()) {
						Order o = new Order();
						o.setID(rs.getInt("ID"));
						pharmacistsIDs.add(rs.getInt("Pharmacist"));
						o.setDateTime(rs.getString("DateTime"));
						o.setTotal(rs.getDouble("Total"));
						o.setPaid(rs.getDouble("Paid"));
						o.setChange(rs.getDouble("Change_"));
						o.setPayDateTime(rs.getString("PayDateTime"));
						orders.add(o);
					}
					
					for (int i=0;i<orders.size();i++) {
						String select1 = "SELECT `FirstName`, `LastName`, `Email`, `PhoneNumber`,"
								+ " `Salary`, `DateOfBirth`, `WorkHours`, `StartTime`, `EndTime`,"
								+ " `Job` FROM `employees` WHERE `ID` = "+pharmacistsIDs.get(i)
								+" ;";
						ResultSet rs1 = database.getStatement().executeQuery(select1);
						if (rs1.next()) {
							Employee emp;
							int job = rs1.getInt("Job");
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
							emp.setID(pharmacistsIDs.get(i));
							emp.setFirstName(rs1.getString("FirstName"));
							emp.setLastName(rs1.getString("LastName"));
							emp.setEmail(rs1.getString("Email"));
							emp.setPhoneNumber(rs1.getString("PhoneNumber"));
							emp.setSalary(rs1.getDouble("Salary"));
							emp.setDateOfBirth(rs1.getString("DateOfBirth"));
							emp.setWorkHours(rs1.getInt("WorkHours"));
							emp.setStartTime(rs1.getString("StartTime"));
							emp.setEndTime(rs1.getString("EndTime"));
							orders.get(i).setPharmacist(emp);
							
							orders.get(i).setCashier(cashier);
						}
					}
					
					for (int i=0;i<orders.size();i++) {
						String select3 = "SELECT * FROM `ordersitems` WHERE `Order` = "+
								orders.get(i).getID()+" ;";
						ResultSet rs3 = database.getStatement().executeQuery(select3);
						ArrayList<Medicine> medicines = new ArrayList<>();
						while (rs3.next()) {
							medicines.add(new Medicine());
						}
						orders.get(i).setMedecines(medicines);
					}
						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				JGridFrame frame = new JGridFrame(parent, orders.size()+1, 8, 
						getOption(), true);
				
				frame.addToGrid(new JLabel("Pharmacist", 15));
				frame.addToGrid(new JLabel("Date Time", 15));
				frame.addToGrid(new JLabel("Total", 15));
				frame.addToGrid(new JLabel("Num of Medics", 15));
				frame.addToGrid(new JLabel("Cashier", 15));
				frame.addToGrid(new JLabel("Paid", 15));
				frame.addToGrid(new JLabel("Change", 15));
				frame.addToGrid(new JLabel("Paid on", 15));
				
				for (Order o : orders) {
					frame.addToGrid(new JLabel(o.getPharmacist().getName(), 15));
					frame.addToGrid(new JLabel(o.getDateTime(), 15));
					frame.addToGrid(new JLabel("Rs."+o.getTotal(), 15));
					frame.addToGrid(new JLabel(String.valueOf(o.getMedicines().size()), 15));
					if (o.getPaid()>0) {
						frame.addToGrid(new JLabel(o.getCashier().getName(), 15));
						frame.addToGrid(new JLabel("Rs."+o.getPaid(), 15));
						frame.addToGrid(new JLabel("Rs."+o.getChange(), 15));
						frame.addToGrid(new JLabel(o.getPayDateTime(), 15));
					} else {
						frame.addToGrid(new JLabel("Not Paid", 15));
						frame.addToGrid(new JLabel("Not Paid", 15));
						frame.addToGrid(new JLabel("Not Paid", 15));
						frame.addToGrid(new JLabel("Not Paid", 15));
					}
				}
			}
		});
		frame.addToGrid(selectBtn);
		
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
