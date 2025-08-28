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

public class ReadAllOrders implements Option {

	@Override
	public String getOption() {
		return "View All Orders";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
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
						System.out.println("Employee doesn't exist");
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
							System.out.println("Employee doesn't exist");
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
				
				Order o = orders.get(i);
				o.setMedecines(medicines);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JGridFrame frame = new JGridFrame(parent, orders.size()+1, 9, 
				getOption(), true);
		
		frame.addToGrid(new JLabel("Pharmacist", 15));
		frame.addToGrid(new JLabel("Date Time", 15));
		frame.addToGrid(new JLabel("Total", 15));
		frame.addToGrid(new JLabel("Num of Medics", 15));
		frame.addToGrid(new JLabel("Cashier", 15));
		frame.addToGrid(new JLabel("Paid", 15));
		frame.addToGrid(new JLabel("Change", 15));
		frame.addToGrid(new JLabel("Paid on", 15));
		frame.addToGrid(new JLabel("", 15));
		
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
			JButton viewItems = new JButton("View Items", 14);
			viewItems.addMouseListener(new MouseListener() {
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
					new ReadOrderItems().viewOrderItems(database, frame, e, o.getID());

				}
			});
			frame.addToGrid(viewItems);
		}
	}

}
