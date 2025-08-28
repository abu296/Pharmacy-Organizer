package GUI;

import java.awt.Color;
import java.awt.Font;

public class JTextField extends javax.swing.JTextField {
	
	
	private static final long serialVersionUID = 1L;

	public JTextField(int size) {
		super();
		setForeground(new Color(15,15,15));
		setFont(new Font("Tahoma", Font.BOLD, size));
		setHorizontalAlignment(JLabel.CENTER);
	}

}
