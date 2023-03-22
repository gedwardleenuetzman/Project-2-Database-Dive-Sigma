import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class XReportGui extends JFrame {

    public XReportGui(ManagerGui managerGui) {
		try {
		setTitle("Chick-fi-la Manager - X Report");

		setSize(500, 300);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
				managerGui.setVisible(true);
            }
        });

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


		Float totalValue = 0.0f;
		Connection conn = DatabaseUtil.makeConnection();
		Statement st = conn.createStatement(); // fill in with col and table name
		ResultSet daily_orders = st.executeQuery("SELECT SUM(totalprice) FROM daily_orders");
		while (daily_orders.next()) {
		Float c = daily_orders.getFloat(1);
		totalValue = totalValue + c;
		}
		

		JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
         
        JLabel label = new JLabel("Total Daily Sales: " + totalValue);
        //JTextField text = new JTextField("Text field", 15);
        panel.setSize(300, 300);
        panel.setLayout(layout);
        panel.add(label);
        //panel.add(text);

		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, panel);
        //layout.putConstraint(SpringLayout.WEST, text, 5, SpringLayout.EAST, label);
        //layout.putConstraint(SpringLayout.NORTH, text, 5, SpringLayout.NORTH, panel);

		daily_orders.close();
		st.close();
		DatabaseUtil.closeConnection(conn);

		mainPanel.add(panel);
		add(mainPanel);

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}
    }



}
