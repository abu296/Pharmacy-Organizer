package GUI;

import java.awt.BorderLayout;
import java.awt.Color;

public class JFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	public JFrame(int w, int l, JFrame parent) {
		super("Pharmacy Management System");
		setSize(w, l);
		getContentPane().setBackground(new Color(43, 101, 236));
		getContentPane().setLayout(new BorderLayout(20, 20));
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
