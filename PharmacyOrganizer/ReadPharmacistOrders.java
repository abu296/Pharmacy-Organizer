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

public class ReadPharmacistOrders implements Option {

	@Override
	public String getOption() {
		return "View Pharmacist Orders";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
JGridFrame frame = new JGridFrame(parent, 3, 2, getOption(), false);
		
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
				if (emp.getJob().equals("Pharmacist")) employees.add(emp);
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
		
		frame.addToGrid(new JLabel("Name:", 18));
		JTextField name = new JTextField(18);
		name.setEditable(false);
		frame.addToGrid(name);
		
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
				
				ArrayList<Order> orders = new ArrayList<>();
				String select = "SELECT * FROM `orders` WHERE `Pharmacist` = "
						+ID.getSelectedItem()+" ;";
				try {
					ResultSet rs = database.getStatement().executeQuery(select);
					if (rs.next()) {
						Order o = new Order();
						o.setID(rs.getInt("ID"));
						int pharmacistID = rs.getInt("Pharmacist");
						int cashierID = rs.getInt("Cashier");
						o.setDateTime(rs.getString("DateTime"));
						o.setTotal(rs.getDouble("Total"));
						o.setPaid(rs.getDouble("Paid"));
						o.setChange(rs.getDouble("Change_"));
						o.setPayDateTime(rs.getString("PayDateTime"));
						
						String select1 = "SELECT `FirstName`, `LastName`, `Email`, `PhoneNumber`,"
								+ " `Salary`, `DateOfBirth`, `WorkHours`, `StartTime`, `EndTime`,"
								+ " `Job` FROM `employees` WHERE `ID` = "+pharmacistID
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
							emp.setID(pharmacistID);
							emp.setFirstName(rs1.getString("FirstName"));
							emp.setLastName(rs1.getString("LastName"));
							emp.setEmail(rs1.getString("Email"));
							emp.setPhoneNumber(rs1.getString("PhoneNumber"));
							emp.setSalary(rs1.getDouble("Salary"));
							emp.setDateOfBirth(rs1.getString("DateOfBirth"));
							emp.setWorkHours(rs1.getInt("WorkHours"));
							emp.setStartTime(rs1.getString("StartTime"));
							emp.setEndTime(rs1.getString("EndTime"));
							o.setPharmacist(emp);
						} else {
							System.out.println("Employee doesn't exist");
						}
						
						if (cashierID>0) {
							String select2 = "SELECT `FirstName`, `LastName`, `Email`, `PhoneNumber`,"
									+ " `Salary`, `DateOfBirth`, `WorkHours`, `StartTime`, `EndTime`,"
									+ " `Job` FROM `employees` WHERE `ID` = "+cashierID
									+" ;";
							ResultSet rs2 = database.getStatement().executeQuery(select2);
							if (rs2.next()) {
								Employee emp;
								int job = rs2.getInt("Job");
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
								emp.setID(cashierID);
								emp.setFirstName(rs2.getString("FirstName"));
								emp.setLastName(rs2.getString("LastName"));
								emp.setEmail(rs2.getString("Email"));
								emp.setPhoneNumber(rs2.getString("PhoneNumber"));
								emp.setSalary(rs2.getDouble("Salary"));
								emp.setDateOfBirth(rs2.getString("DateOfBirth"));
								emp.setWorkHours(rs2.getInt("WorkHours"));
								emp.setStartTime(rs2.getString("StartTime"));
								emp.setEndTime(rs2.getString("EndTime"));
								o.setCashier(emp);
							}  else {
								System.out.println("Employee doesn't exist");
							}
						}
							
						String select3 = "SELECT * FROM `ordersitems` WHERE `Order` = "+
								o.getID()+" ;";
						ResultSet rs3 = database.getStatement().executeQuery(select3);
						ArrayList<Medicine> medicines = new ArrayList<>();
						while (rs3.next()) {
							medicines.add(new Medicine());
						}
						o.setMedecines(medicines);
						orders.add(o);
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
				name.setText(e.getFirstName()+" "+e.getLastName());
			}
		});
	}

}
