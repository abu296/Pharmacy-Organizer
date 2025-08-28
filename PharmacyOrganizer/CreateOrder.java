package PharmacyOrganizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GUI.JButton;
import GUI.JComboBox;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;

public class CreateOrder implements Option {

	@Override
	public String getOption() {
		return "Create new Order";
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
				m.setQty(0);
				m.setManufactureDate(rs.getString("ManufactureDate"));
				m.setExpiryDate(rs.getString("ExpiryDate"));
				m.setCompany(rs.getString("Company"));
				m.setCost(rs.getDouble("Cost"));
				medicines.add(m);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		String[] qtyList = new String[101];
		for (int i=0;i<=100;i++) {
			qtyList[i] = String.valueOf(i);
		}
		
		JGridFrame frame = new JGridFrame(parent, medicines.size()+1, 5,
				getOption(), true);
		for (Medicine m : medicines) {
			frame.addToGrid(new JLabel(m.getName(), 15));
			frame.addToGrid(new JLabel(m.getType(), 15));
			frame.addToGrid(new JLabel(m.getCompany(), 15));
			frame.addToGrid(new JLabel(String.valueOf(m.getCost()), 15));
			JComboBox qty = new JComboBox(qtyList, 15);
			qty.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					m.setQty(Integer.parseInt(qty.getSelectedItem().toString()));
				}
			});
			frame.addToGrid(qty);
		}
		
		int pharmacistID = e.getID();
		
		for (int i=0;i<4;i++) {
			frame.addToGrid(new JLabel("", 15));
		}
		
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
				double total = 0;
				StringBuilder sb = new StringBuilder();
				for (int i=0;i<medicines.size();i++) {
					Medicine m = medicines.get(i);
					if (m.getQty()!=0) {
						sb.append(m.getName()+"  "+m.getCost()+"  "+m.getQty()+"\n");
						total += m.getCost()*m.getQty();
					}
				}
				sb.append("Total: "+total);
				
				int confirm = JOptionPane.showConfirmDialog(frame, sb.toString());
				if (confirm==JOptionPane.YES_OPTION) {
					String dateTime = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm")
							.format(LocalDateTime.now());
					String insert1 = "INSERT INTO `orders`"
							+ "(`Cashier`, `Pharmacist`, `DateTime`,"
							+ " `Total`, `Paid`, `Change_`, `PayDateTime`)"
							+ " VALUES ('0','"+
							pharmacistID+"','"+dateTime+"','"+total+
							"','0','0','1000-01-01 01:01');";
					try {
						database.getStatement().execute(insert1);
						ResultSet rs = database.getStatement()
								.executeQuery("SELECT LAST_INSERT_ID();");
						rs.next();
						int orderID = rs.getInt("LAST_INSERT_ID()");
						
						for (Medicine m : medicines) {
							if (m.getQty()!=0) {
								String insert = "INSERT INTO `ordersitems`(`Order`,"
										+ " `Medicine`,"
										+ " `Qty`) VALUES ('"+orderID+"','"+m.getID()+"','"+
										m.getQty()+"');";
								database.getStatement().execute(insert);
							}
						}
						
						JOptionPane.showMessageDialog(frame, 
								"Order created successfully");
						frame.dispose();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, e1.getMessage());
					}
				}
			}
		});
		frame.addToGrid(confirm);
		
	}

}
