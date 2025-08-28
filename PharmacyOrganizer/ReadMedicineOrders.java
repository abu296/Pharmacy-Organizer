package PharmacyOrganizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.JButton;
import GUI.JComboBox;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JTextField;

public class ReadMedicineOrders implements Option {

	@Override
	public String getOption() {
		return "View Medicine Orders";
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
		
		String[] IDs = new String[medicines.size()];
		for (int i=0;i<medicines.size();i++) {
			IDs[i] = String.valueOf(medicines.get(i).getID());
		}
		
		JGridFrame frame = new JGridFrame(parent, 5, 2, getOption(), false);
		frame.addToGrid(new JLabel("Medecine ID:", 18));
		JComboBox medID = new JComboBox(IDs, 18);
		frame.addToGrid(medID);
		frame.addToGrid(new JLabel("Medecine Name:", 18));
		JTextField medName = new JTextField(18);
		medName.setEditable(false);
		frame.addToGrid(medName);
		frame.addToGrid(new JLabel("Medecine Type:", 18));
		JTextField medType = new JTextField(18);
		medType.setEditable(false);
		frame.addToGrid(medType);
		frame.addToGrid(new JLabel("Medecine Company:", 18));
		JTextField medCompany = new JTextField(18);
		medCompany.setEditable(false);
		frame.addToGrid(medCompany);
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
				
				Medicine m = medicines.get(medID.getSelectedIndex());
				ArrayList<Order> orders = new ArrayList<>();
				String select = "SELECT * FROM `ordersitems` WHERE `Medicine` = "
						+m.getID()+" ;";
				ArrayList<Integer> ordersIDs = new ArrayList<>();
				try {
					ResultSet rs = database.getStatement().executeQuery(select);
					while (rs.next()) {
						int order = rs.getInt("Order");
						if (!ordersIDs.contains(order)) ordersIDs.add(order);
					}
					
					for (int orderID : ordersIDs) {
						String select0 = "SELECT * FROM `orders` WHERE `ID` = "+orderID+" ;";
						
						try {
							ResultSet rs0 = database.getStatement().executeQuery(select0);
							if (rs0.next()) {
								Order o = new Order();
								o.setID(rs0.getInt("ID"));
								int pharmacistID = rs0.getInt("Pharmacist");
								int cashierID = rs0.getInt("Cashier");
								o.setDateTime(rs0.getString("DateTime"));
								o.setTotal(rs0.getDouble("Total"));
								o.setPaid(rs0.getDouble("Paid"));
								o.setChange(rs0.getDouble("Change_"));
								o.setPayDateTime(rs0.getString("PayDateTime"));
								
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
								ArrayList<Medicine> meds = new ArrayList<>();
								while (rs3.next()) {
									meds.add(new Medicine());
								}
								o.setMedecines(medicines);
								
								orders.add(o);	
								
							}
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
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
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		frame.addToGrid(selectBtn);
		frame.setVisible(true);
		
		medID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Medicine m = medicines.get(medID.getSelectedIndex());
				medID.setSelectedItem(m.getID());
				medName.setText(m.getName());
				medType.setText(m.getType());
				medCompany.setText(m.getCompany());
			}
		});
	}

}
