package PharmacyOrganizer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import GUI.JButton;
import GUI.JFrame;
import GUI.JLabel;
import GUI.JPanel;
import GUI.JPasswordField;
import GUI.JTextField;


public class Main {
	

	public static void main(String[] args) {
		
		Database database = new Database();
		
		JFrame frame = new JFrame(625, 350, null);
		
		JPanel panel = new JPanel(new BorderLayout(15, 15), 27, 27, 27, 27);
		panel.add(new GUI.JLabel("Welcome to Pharmacy Organizer", 25),
				BorderLayout.NORTH);
		
		JPanel center = new JPanel(new GridLayout(2, 2, 20, 20), 10, 20, 10, 20);
		
		center.add(new JLabel("Email:", 22));
		JTextField email = new JTextField(20);
		center.add(email);
		
		center.add(new JLabel("Password:", 22));
		JPasswordField password = new JPasswordField(20);
		center.add(password);
		
		JButton login = new JButton("Login", 21);
		panel.add(login, BorderLayout.SOUTH);
		
		panel.add(center, BorderLayout.CENTER);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		
		login.addMouseListener(new MouseListener() {
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
			public void mouseClicked(MouseEvent e1) {
				
				String select = "SELECT * FROM `employees` WHERE `Email` = '"+email.getText()+
						"' AND `Password` = '"+password.getText()+"';";
				try {
					ResultSet rs = database.getStatement().executeQuery(select);
					if (rs.next()) {
						Employee e;
						int job = rs.getInt("Job");
						switch(job) {
						case 1:
							e = new Manager();
							break;
						case 2:
							e = new Pharmacist();
							break;
						case 3:
							e = new Cashier();
							break;
							default:
								e = new Employee() {
									@Override
									String getJob() {
										return null;
									}
								};
								break;
						}
						e.setID(rs.getInt("ID"));
						e.setFirstName(rs.getString("FirstName"));
						e.setLastName(rs.getString("LastName"));
						e.setEmail(rs.getString("Email"));
						e.setPhoneNumber(rs.getString("PhoneNumber"));
						e.setPassword(rs.getString("Password"));
						e.setSalary(rs.getDouble("Salary"));
						e.setDateOfBirth(rs.getString("DateOfBirth"));
						e.setWorkHours(rs.getInt("WorkHours"));
						e.setStartTime(rs.getString("StartTime"));
						e.setEndTime(rs.getString("EndTime"));
						e.showOptions(database, frame);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, 
								"Incorrect email or password!");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}

}
