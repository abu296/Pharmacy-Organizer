package GUI;

import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("rawtypes")
public class JComboBox extends javax.swing.JComboBox {
	
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public JComboBox(Object[] list, int size) {
		super(list);
		setForeground(new Color(0, 50, 140));
		setFont(new Font("Tahoma", Font.BOLD, size));
//		setHorizontalAlignment(JLabel.CENTER);
	}

}
