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

public class ReadOrder implements Option {

	@Override
	public String getOption() {
		return "View Order By ID";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		ArrayList<Order> orders = readOrders(database);
		String[] IDs = new String[orders.size()];
		for (int i=0;i<orders.size();i++) {
			IDs[i] = String.valueOf(orders.get(i).getID());
		}
		
		JGridFrame frame = new JGridFrame(parent, 5, 4, getOption(), false);
		
		frame.addToGrid(new JLabel("ID:", 18));
		JComboBox ID = new JComboBox(IDs, 18);
		frame.addToGrid(ID);
		
		frame.addToGrid(new JLabel("Pharmacist:", 18));
		JTextField pharmacist = new JTextField(18);
		pharmacist.setEditable(false);
		frame.addToGrid(pharmacist);
		
		frame.addToGrid(new JLabel("Date Time:", 18));
		JTextField dateTime = new JTextField(18);
		dateTime.setEditable(false);
		frame.addToGrid(dateTime);
		
		frame.addToGrid(new JLabel("Total:", 18));
		JTextField total = new JTextField(18);
		total.setEditable(false);
		frame.addToGrid(total);
		
		frame.addToGrid(new JLabel("Num of Medics:", 18));
		JTextField numOfMedics = new JTextField(18);
		numOfMedics.setEditable(false);
		frame.addToGrid(numOfMedics);
		
		frame.addToGrid(new JLabel("Cashier:", 18));
		JTextField cashier = new JTextField(18);
		cashier.setEditable(false);
		frame.addToGrid(cashier);
		
		frame.addToGrid(new JLabel("Paid:", 18));
		JTextField paid = new JTextField(18);
		paid.setEditable(false);
		frame.addToGrid(paid);
		
		frame.addToGrid(new JLabel("Change:", 18));
		JTextField change = new JTextField(18);
		change.setEditable(false);
		frame.addToGrid(change);
		
		frame.addToGrid(new JLabel("Paid on:", 18));
		JTextField paidDate = new JTextField(18);
		paidDate.setEditable(false);
		frame.addToGrid(paidDate);
		
		frame.setVisible(true);
		
		ID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Order o = orders.get(ID.getSelectedIndex());
				ID.setSelectedItem(o.getID());
				pharmacist.setText(o.getPharmacist().getFirstName()+" "
							+o.getPharmacist().getLastName());
				dateTime.setText(o.getDateTime());
				total.setText(String.valueOf(o.getTotal()));
				numOfMedics.setText(String.valueOf(o.getMedicines().size()));
				if (o.getPaid()>0) {
					cashier.setText(o.getCashier().getFirstName()+
							" "+o.getCashier().getLastName());
					paid.setText(String.valueOf(o.getPaid()));
					change.setText(String.valueOf(o.getChange()));
					paidDate.setText(o.getPayDateTime());
				} else {
					cashier.setText("Not Paid");
					paid.setText("Not Paid");
					change.setText("Not Paid");
					paidDate.setText("Not Paid");
				}
			}
		});
		
	}
	
	private ArrayList<Order> readOrders(Database database) {
		ArrayList<Order> orders = new ArrayList<>();
		String select = "SELECT * FROM `orders`;";
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			ArrayList<Integer> pharmacistsIDs = new ArrayList<>();
			ArrayList<Integer> cashiersIDs = new ArrayList<>();
			while (rs.next()) {
				Order o = new Order();
				o.setID(rs.getInt("ID"));
				pharmacistsIDs.add(rs.getInt("Pharmacist"));
				cashiersIDs.add(rs.getInt("Cashier"));
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
				try {
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
					} else {
						Employee e = new Pharmacist();
						e.setFirstName("Deleted");
						e.setLastName("Employee");
						orders.get(i).setPharmacist(e);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
				
				if (cashiersIDs.get(i)>0) {
					String select2 = "SELECT `FirstName`, `LastName`, `Email`, `PhoneNumber`,"
							+ " `Salary`, `DateOfBirth`, `WorkHours`, `StartTime`, `EndTime`,"
							+ " `Job` FROM `employees` WHERE `ID` = "+cashiersIDs.get(i)
							+" ;";
					try {
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
							emp.setID(cashiersIDs.get(i));
							emp.setFirstName(rs2.getString("FirstName"));
							emp.setLastName(rs2.getString("LastName"));
							emp.setEmail(rs2.getString("Email"));
							emp.setPhoneNumber(rs2.getString("PhoneNumber"));
							emp.setSalary(rs2.getDouble("Salary"));
							emp.setDateOfBirth(rs2.getString("DateOfBirth"));
							emp.setWorkHours(rs2.getInt("WorkHours"));
							emp.setStartTime(rs2.getString("StartTime"));
							emp.setEndTime(rs2.getString("EndTime"));
							orders.get(i).setCashier(emp);
						} else {
							Employee e = new Cashier();
							e.setFirstName("Deleted");
							e.setLastName("Employee");
							orders.get(i).setCashier(e);
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
				
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
		return orders;
	}

}
