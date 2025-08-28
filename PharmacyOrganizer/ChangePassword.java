package PharmacyOrganizer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import GUI.JButton;
import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;
import GUI.JPasswordField;

public class ChangePassword implements Option {

	@Override
	public String getOption() {
		return "Change Password";
	}

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		
		JGridFrame frame = new JGridFrame(parent, 4, 2, "Change Password", false);
		
		frame.addToGrid(new JLabel("Old Password:", 20));
		JPasswordField oldPassword = new JPasswordField(20);
		frame.addToGrid(oldPassword);
		
		frame.addToGrid(new JLabel("New Password:", 20));
		JPasswordField newPassword = new JPasswordField(20);
		frame.addToGrid(newPassword);
		
		frame.addToGrid(new JLabel("Confirm Password:", 20));
		JPasswordField confirmPassword = new JPasswordField(20);
		frame.addToGrid(confirmPassword);
		
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
			public void mouseClicked(MouseEvent e1) {
				if (!e.getPassword().equals(oldPassword.getText())) {
					JOptionPane.showMessageDialog(frame, "Incorrect password!");
					return;
				}
				if (newPassword.getText().equals("")||newPassword.getText().equals(" ")) {
					JOptionPane.showMessageDialog(frame, "Empty Password!");
					return;
				}
				
				if (!newPassword.getText().equals(confirmPassword.getText())) {
					JOptionPane.showMessageDialog(frame, "Password doesn't match!");
					return;
				}
				String update = "UPDATE `employees` SET `Password`='"+newPassword.getText()+
				"' WHERE `ID` = "+e.getID()+" ;";
				try {
					database.getStatement().execute(update);
					JOptionPane.showMessageDialog(frame, "Password changed successfully");
					frame.dispose();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(frame, e2.getMessage());
				}
			}
		});
		frame.addToGrid(confirm);
		
		frame.setVisible(true);
	}

}
