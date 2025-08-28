package GUI;

import java.awt.Color;
import java.awt.Font;

public class JLabel extends javax.swing.JLabel {
	

	private static final long serialVersionUID = 1L;

	public JLabel(String text, int size) {
		super(text);
		setForeground(new Color(15,15,15));
		setFont(new Font("Tahoma", Font.BOLD, size));
		setHorizontalAlignment(JLabel.CENTER);
	}

}
