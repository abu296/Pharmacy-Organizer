package PharmacyOrganizer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;

public class ReadOrderItems implements Option {
	
	public void viewOrderItems(Database database, JGridFrame frame2, Employee e, int ID) {
		
		String select = "SELECT * FROM `ordersitems` WHERE `Order` = "+ID+" ;";
		ArrayList<Integer> medicineIDs = new ArrayList<>();
		ArrayList<Integer> qtys = new ArrayList<>();
		ArrayList<Medicine> medicines = new ArrayList<>();
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			while (rs.next()) {
				medicineIDs.add(rs.getInt("Medicine"));
				qtys.add(rs.getInt("Qty"));
			}
			
			for (int i=0;i<medicineIDs.size();i++) {
				String select1 = "SELECT * FROM `medicines` WHERE `ID` = '"+
							medicineIDs.get(i)+"';";
				
				ResultSet rs1 = database.getStatement().executeQuery(select1);
				if (rs1.next()) {
					Medicine m = new Medicine();
					m.setID(rs1.getInt("ID"));
					m.setName(rs1.getString("Name"));
					m.setType(rs1.getString("Type"));
					m.setQty(qtys.get(i));
					m.setManufactureDate(rs1.getString("ManufactureDate"));
					m.setExpiryDate(rs1.getString("ExpiryDate"));
					m.setCompany(rs1.getString("Company"));
					m.setCost(rs1.getDouble("Cost"));
					medicines.add(m);
				} else {
					JOptionPane.showMessageDialog(frame2, "Invalid Medicine ID: "+medicineIDs.get(i));
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JGridFrame frame = new JGridFrame(frame2, medicines.size()+1, 5, getOption(), true);
		
		frame.addToGrid(new JLabel("Name", 18));
		frame.addToGrid(new JLabel("Type", 18));
		frame.addToGrid(new JLabel("Qty", 18));
		frame.addToGrid(new JLabel("Company", 18));
		frame.addToGrid(new JLabel("Cost", 18));
		
		for (Medicine m : medicines) {
			frame.addToGrid(new JLabel(m.getName(), 18));
			frame.addToGrid(new JLabel(m.getType(), 18));
			frame.addToGrid(new JLabel(String.valueOf(m.getQty()), 18));
			frame.addToGrid(new JLabel(m.getCompany(), 18));
			frame.addToGrid(new JLabel("Rs."+m.getCost(), 18));
		}
		
		frame.setVisible(true);
		
	}

	@Override
	public String getOption() {
		return "View Order Items";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		JOptionPane.showMessageDialog(parent, "Select the order from View all Orders to read order items");
		
//		System.out.println("Enter Order ID (int): (-1 to show all orders)");
//		int ID = s.nextInt();
//		while (ID<0) {
//			new ReadAllOrders().oper(database, s, e);
//			System.out.println("Enter Order ID (int): (-1 to show all orders)");
//			ID = s.nextInt();
//		}
//		
//		String select = "SELECT * FROM `ordersitems` WHERE `Order` = "+ID+" ;";
//		ArrayList<Integer> medicineIDs = new ArrayList<>();
//		ArrayList<Integer> qtys = new ArrayList<>();
//		try {
//			ResultSet rs = database.getStatement().executeQuery(select);
//			while (rs.next()) {
//				medicineIDs.add(rs.getInt("Medicine"));
//				qtys.add(rs.getInt("Qty"));
//			}
//			
//			ArrayList<Medicine> medicines = new ArrayList<>();
//			for (int i=0;i<medicineIDs.size();i++) {
//				String select1 = "SELECT * FROM `medicines` WHERE `ID` = '"+
//							medicineIDs.get(i)+"';";
//				
//				ResultSet rs1 = database.getStatement().executeQuery(select1);
//				if (rs1.next()) {
//					Medicine m = new Medicine();
//					m.setID(rs1.getInt("ID"));
//					m.setName(rs1.getString("Name"));
//					m.setType(rs1.getString("Type"));
//					m.setQty(qtys.get(i));
//					m.setManufactureDate(rs1.getString("ManufactureDate"));
//					m.setExpiryDate(rs1.getString("ExpiryDate"));
//					m.setCompany(rs1.getString("Company"));
//					m.setCost(rs1.getDouble("Cost"));
//					medicines.add(m);
//
//					System.out.println("Name:\t\t"+m.getName());
//					System.out.println("Type:\t\t"+m.getType());
//					System.out.println("Qty:\t\t"+m.getQty());
//					System.out.println("Company:\t"+m.getCompany());
//					System.out.println("Cost:\t\t"+m.getCost());
//					System.out.println("---------------------------");
//				} else {
//					System.out.println("Invalid Medicine ID: "+medicineIDs.get(i));
//				}
//			}
//			
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
	}

}
