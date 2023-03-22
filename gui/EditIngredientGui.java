import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class EditIngredientGui extends JFrame {
	private String productId;

	UpdateInventoryGui updateInventoryGui;
	LabeledFieldPanel nameLabeledFieldPanel;
	LabeledFieldPanel quantityLabeledFieldPanel;
	
    public EditIngredientGui(UpdateInventoryGui uig, String name, String prod_id, Boolean makeNew) {
		try {
			productId = prod_id;
			updateInventoryGui = uig;

			if (makeNew) {
				setTitle("Chick-fi-la Manager - Update Inventory - Make New Ingredient");
			} else {
				setTitle("Chick-fi-la Manager - Update Inventory - Edit " + name);
			}

			setSize(400, 200);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
					updateInventoryGui.filter();
					updateInventoryGui.setVisible(true);
				}
			});
			
			JPanel mainPanel = new JPanel();
			mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

			JPanel topPanel = new JPanel(new BorderLayout());
			topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
			topPanel.setBorder(new EmptyBorder(5, 5, 0, 5));

			JButton saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!makeNew) {
						saveUpdates();
					} else {
						try {	
							Connection conn = DatabaseUtil.makeConnection();

							productId = Integer.toString(DatabaseUtil.generateTableId(conn, "ingredients"));

							DatabaseUtil.closeConnection(conn);

							saveUpdates();
						} catch(Exception exc) {
							JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
						}
					}
					
				}
			});
			topPanel.add(saveButton, BorderLayout.WEST);

			if (!makeNew) {
				JButton removeButton = new JButton("Remove");

				removeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + name, "Confirmation", JOptionPane.YES_NO_OPTION);

						if (result != JOptionPane.YES_OPTION) {
							return;
						}		

						try {	
							Connection conn = DatabaseUtil.makeConnection();
							Statement statement = conn.createStatement();

							statement.executeUpdate("DELETE FROM ingredients WHERE id = " + productId + ";");

							DatabaseUtil.closeConnection(conn);
						} catch(Exception exc) {
							JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
						}

						updateInventoryGui.filter();
						dispose();
						updateInventoryGui.setVisible(true);
					}
				});

				topPanel.add(removeButton, BorderLayout.EAST);
			}

			Connection conn = DatabaseUtil.makeConnection();
			Statement statement = conn.createStatement();
        	ResultSet result = statement.executeQuery("SELECT * FROM ingredients WHERE id = " + productId + " LIMIT 1;");
			
			result.next();

			nameLabeledFieldPanel = new LabeledFieldPanel("Name", name);
			quantityLabeledFieldPanel = new LabeledFieldPanel("Quantity", makeNew ? "0" : result.getString("quantity"));

			mainPanel.add(topPanel, BorderLayout.NORTH);
			mainPanel.add(nameLabeledFieldPanel);
			mainPanel.add(quantityLabeledFieldPanel);

			add(mainPanel);

			DatabaseUtil.closeConnection(conn);

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
    }

	public void saveUpdates() {
		try {
			Connection conn = DatabaseUtil.makeConnection();

			conn.createStatement().executeUpdate("DELETE FROM ingredients WHERE id = " + productId + ";");
			conn.createStatement().executeUpdate("INSERT INTO ingredients (id, name, quantity) VALUES (" + productId + ", '" + nameLabeledFieldPanel.fieldBox.getText() + "', " + Integer.parseInt(quantityLabeledFieldPanel.fieldBox.getText()) + ");");

			DatabaseUtil.closeConnection(conn);

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}

		dispose();
		updateInventoryGui.filter();
		updateInventoryGui.setVisible(true);	
	}
}
