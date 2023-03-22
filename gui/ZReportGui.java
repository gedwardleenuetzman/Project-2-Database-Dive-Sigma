import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class ZReportGui extends JFrame {
	private IngredientPanel ingredientPanel;

    public ZReportGui(ManagerGui managerGui) {
		try {
		setTitle("Chick-fi-la Manager - Z Report");

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

		// Float totalValue = 0.0f;
		// Connection conn = DatabaseUtil.makeConnection();
		// Statement st = conn.createStatement(); // fill in with col and table name
		// ResultSet res = st.executeQuery("SELECT SUM(totalprice) FROM daily_orders");
		// while (res.next()) {
		// Float c = res.getFloat(1);
		// totalValue = totalValue + c;
		// }

		// String deleteData = "DELETE FROM daily_orders";
		// String deleteData2 = "DELETE FROM daily_order_products";
		// PreparedStatement pstmt = conn.prepareStatement(deleteData);
		// PreparedStatement pstm2 = conn.prepareStatement(deleteData2);
		// //Statement statement = conn.createStatement();
		// ResultSet deleted = pstmt.executeQuery();
		// ResultSet deletedProducts = pstm2.executeQuery();

		JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
         
        JLabel label = new JLabel("Total Daily Sales: ");
        //JTextField text = new JTextField("Text field", 15);
        panel.setSize(300, 300);
        panel.setLayout(layout);
        panel.add(label);
        //panel.add(text);

		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, panel);
        //layout.putConstraint(SpringLayout.WEST, text, 5, SpringLayout.EAST, label);
        //layout.putConstraint(SpringLayout.NORTH, text, 5, SpringLayout.NORTH, panel);

		//DatabaseUtil.closeConnection(conn);

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
