package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;

public class JButton extends javax.swing.JLabel {
	

	private static final long serialVersionUID = 1L;

	public JButton(String text, int size) {
		super(text);
		setForeground(new Color(15, 15, 15));
		setBackground(new Color(166, 206, 222));
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setFont(new Font("Tahoma", Font.BOLD, size));
		setHorizontalAlignment(JButton.CENTER);
	}

}
