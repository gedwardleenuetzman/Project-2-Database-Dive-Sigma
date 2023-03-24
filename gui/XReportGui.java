import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Shows the X report GUI
 */
public class XReportGui extends JFrame {
	/**
	 * Calculates the daily total for the CFA Sales
	 * Will produce a result set of the daily_orders table and
	 * and traverse the totalprice col in order to get the total
	 * 
	 * @param managerGui the manager gui that the xreport control comes from
	 * 
	 * @return the total daily amount calculated through the sql tables
	 * @throws e exception if the database in unable to get connected or if there is
	 *           an invalid connection statement
	 */

	public XReportGui(ManagerGui managerGui) {
		try {
			setTitle("Chick-fi-la Manager - X Report");

			setSize(500, 300);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			// goes back to Manager Gui
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
					managerGui.setVisible(true);
				}
			});

			// GUI formatting
			JPanel mainPanel = new JPanel();
			mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

			// Functionality when pressing x report button
			Float totalDaily = 0.0f;
			Connection conn = DatabaseUtil.makeConnection();
			String strQuery = "SELECT SUM(totalprice) FROM daily_orders";
			PreparedStatement pst = conn.prepareStatement(strQuery);
			ResultSet result = pst.executeQuery();

			// Traverses the rows of totalprice col to get the total
			while (result.next()) {
				Float td = result.getFloat(1);
				totalDaily = totalDaily + td;
			}

			JPanel panel = new JPanel();
			SpringLayout layout = new SpringLayout();

			JLabel label = new JLabel("Total Daily Sales: " + totalDaily);
			panel.setSize(300, 300);
			panel.setLayout(layout);
			panel.add(label);

			layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, panel);

			DatabaseUtil.closeConnection(conn);

			mainPanel.add(panel);
			add(mainPanel);

			// Catch for invalid query prompts to SQL
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}
	}
}
