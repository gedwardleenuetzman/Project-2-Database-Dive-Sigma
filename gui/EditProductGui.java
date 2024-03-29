import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

/**
 * Edit Product GUI extended
 */
public class EditProductGui extends JFrame {
	/**
	 * EditProductGui will be used to extend the JFrame and
	 * allow the manager to add products to the sql query
	 */
	private HashMap<String, JTextField> inputMap;

	private ScrollPanel scrollPanel;
	private String productId;

	UpdateMenuGui updateMenuGui;
	LabeledFieldPanel nameLabeledFieldPanel;
	LabeledFieldPanel costLabeledFieldPanel;

	/**
	 *
	 * @param umg     update menu gui to go back to when x is pressed
	 * @param name    name of the product
	 * @param prod_id id of the product
	 * @param makeNew bool to show if a new product is being made or not
	 */
	public EditProductGui(UpdateMenuGui umg, String name, String prod_id, Boolean makeNew) {
		try {
			productId = prod_id;
			updateMenuGui = umg;
			inputMap = new HashMap<String, JTextField>();

			if (makeNew) {
				setTitle("Chick-fi-la Manager - Update Menu - Make New Product");
			} else {
				setTitle("Chick-fi-la Manager - Update Menu - Edit " + name);
			}

			setSize(400, 400);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
					updateMenuGui.filter();
					updateMenuGui.setVisible(true);
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

							ResultSet result = conn.createStatement()
									.executeQuery("SELECT COUNT(*) FROM products_cfa;");
							result.next();

							productId = Integer.toString(result.getInt(1) * 2);

							DatabaseUtil.closeConnection(conn);

							saveUpdates();
						} catch (Exception exc) {
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
						int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + name,
								"Confirmation", JOptionPane.YES_NO_OPTION);

						if (result != JOptionPane.YES_OPTION) {
							return;
						}

						try {
							Connection conn = DatabaseUtil.makeConnection();
							Statement statement = conn.createStatement();

							statement.executeUpdate("DELETE FROM products_cfa WHERE id = " + prod_id + ";");
							statement.executeUpdate("DELETE FROM product_ingredients WHERE prod_id = " + prod_id + ";");

							DatabaseUtil.closeConnection(conn);
						} catch (Exception exc) {
							JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
						}

						updateMenuGui.filter();
						dispose();
						updateMenuGui.setVisible(true);
					}
				});

				topPanel.add(removeButton, BorderLayout.EAST);
			}

			Connection conn = DatabaseUtil.makeConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM products_cfa WHERE id = " + prod_id + " LIMIT 1;");

			result.next();

			nameLabeledFieldPanel = new LabeledFieldPanel("Name", name);
			costLabeledFieldPanel = new LabeledFieldPanel("Price", makeNew ? "0" : result.getString("price"));

			scrollPanel = new ScrollPanel();

			HashMap<String, String> id2name = DatabaseUtil.getIngredientIdToName(conn);
			HashMap<String, String> productIngredients = DatabaseUtil.getProductIngredients(prod_id, conn);

			for (String ingredientId : id2name.keySet()) {
				LabeledFieldPanel label;

				if (productIngredients.get(ingredientId) != null) {
					label = new LabeledFieldPanel(id2name.get(ingredientId), productIngredients.get(ingredientId));
				} else {
					label = new LabeledFieldPanel(id2name.get(ingredientId), "0");
				}

				scrollPanel.add(label);
				inputMap.put(ingredientId, label.fieldBox);
			}

			mainPanel.add(topPanel, BorderLayout.NORTH);
			mainPanel.add(nameLabeledFieldPanel);
			mainPanel.add(costLabeledFieldPanel);
			mainPanel.add(scrollPanel.scrollPane, BorderLayout.CENTER);

			add(mainPanel);

			DatabaseUtil.closeConnection(conn);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
	}

	/**
	 * Saves the update for the ingredient that was inserted
	 */
	public void saveUpdates() {
		/*
		 * Saves update by making connection and inserted the inputed ingredient within
		 * the database
		 */
		try {
			Connection conn = DatabaseUtil.makeConnection();

			conn.createStatement().executeUpdate("DELETE FROM products_cfa WHERE id = " + productId + ";");
			conn.createStatement()
					.executeUpdate("INSERT INTO products_cfa (id, name, price) VALUES (" + productId + ", '"
							+ nameLabeledFieldPanel.fieldBox.getText() + "', "
							+ Float.parseFloat(costLabeledFieldPanel.fieldBox.getText()) + ");");

			for (String ingredientId : inputMap.keySet()) {
				JTextField inputBox = inputMap.get(ingredientId);
				Integer quantity = Integer.parseInt(inputBox.getText());

				conn.createStatement().executeUpdate("DELETE FROM product_ingredients WHERE prod_id = " + productId
						+ " AND item_id = " + ingredientId + ";");

				if (quantity > 0) {
					conn.createStatement()
							.executeUpdate("INSERT INTO product_ingredients (prod_id, item_id, quantity) VALUES ("
									+ productId + ", " + ingredientId + ", " + quantity + ");");
				}
			}

			DatabaseUtil.closeConnection(conn);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}

		dispose();
		updateMenuGui.filter();
		updateMenuGui.setVisible(true);
	}
}
