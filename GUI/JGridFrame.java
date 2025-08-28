package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

public class JGridFrame extends JFrame {
    
    private JPanel center;
    private static final long serialVersionUID = 1L;
    
    public JGridFrame(JFrame parent, int rows, int columns, String title, boolean scroll) {
        // Set window title and size
        super(title);
        setSize(300 * columns, 150 + 65 * rows);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main panel with padding
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(27, 27, 27, 27));
        
        // Title label at top
        panel.add(new javax.swing.JLabel(title, javax.swing.SwingConstants.CENTER), BorderLayout.NORTH);
        
        // Center grid with padding
        center = new JPanel(new GridLayout(rows, columns, 20, 20));
        center.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Add scroll if needed
        if (scroll) {
            if (300 * columns > 300 * 4)
                setSize(new Dimension(300 * 4, getHeight()));
            if (150 + 65 * rows > 700)
                setSize(new Dimension(getWidth(), 700));	
            
            JScrollPane pane = new JScrollPane(center);
            pane.setBackground(null);
            pane.getViewport().setBackground(null);
            pane.setBorder(null);
            pane.getViewport().setBorder(null);
            panel.add(pane, BorderLayout.CENTER);
        } else {
            panel.add(center, BorderLayout.CENTER);
        }
        
        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    // Method to add components into grid
    public void addToGrid(JComponent c) {
        center.add(c);
        center.revalidate();
        center.repaint();
    }
}
