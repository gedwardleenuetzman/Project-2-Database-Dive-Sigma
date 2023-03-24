import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

/**
 * Shows the Z report GUI
 */
public class ZReportGui extends JFrame {
	/**
	 * Calculates the daily total for the CFA Sales
	 * Will produce a result set of the daily_orders table and
	 * and traverse the totalprice col in order to get the total.
	 * Also clears the daily_orders and daily_order_products tables in sql that
	 * that both x and z report rely on.
	 * 
	 * @param managerGui the manager gui that the z report control comes from
	 * 
	 * @ @return the total daily amount calculated through the sql tables (clears
	 *   daily tables)
	 * @throws e exception if the database in unable to get connected or if there is
	 *           an invalid connection statement
	 */

	public ZReportGui(ManagerGui managerGui) {
		try {
			setTitle("Chick-fi-la Manager - Z Report");

			setSize(500, 300);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			// Goes back to manager gui if closed
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
					managerGui.setVisible(true);
				}
			});

			JPanel mainPanel = new JPanel();
			mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

			// Calculates total sales for the day
			Float totalDaily = 0.0f;
			Connection conn = DatabaseUtil.makeConnection();
			String strQuery = "SELECT SUM(totalprice) FROM daily_orders;";
			PreparedStatement pst = conn.prepareStatement(strQuery);
			ResultSet result = pst.executeQuery();

			while (result.next()) {
				Float td = result.getFloat(1);
				totalDaily = totalDaily + td;
			}

			// query statments to clear both daily_orders and daily_order_products
			String deleteData1 = "DELETE FROM daily_orders;";
			String deleteData2 = "DELETE FROM daily_order_products;";
			PreparedStatement stm1 = conn.prepareStatement(deleteData1);
			PreparedStatement stm2 = conn.prepareStatement(deleteData2);
			stm1.executeUpdate();
			stm2.executeUpdate();

			// GUI formatting
			JPanel panel = new JPanel();
			SpringLayout layout = new SpringLayout();

			JLabel label = new JLabel("Total Daily Sales: " + totalDaily);
			panel.setSize(300, 300);
			panel.setLayout(layout);
			panel.add(label);
			;

			layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, panel);

			DatabaseUtil.closeConnection(conn);

			mainPanel.add(panel);
			add(mainPanel);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}
	}
}
