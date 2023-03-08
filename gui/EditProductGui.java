import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class EditProductGui extends JFrame {
	private HashMap<String, JTextField> inputMap;
	private HashMap<String, String> dataMap;

	private ScrollPanel scrollPanel;
	private String productId;
	private String productName;

	UpdateMenuGui updateMenuGui;
	LabeledFieldPanel nameLabeledFieldPanel;
	LabeledFieldPanel costLabeledFieldPanel;

    public EditProductGui(UpdateMenuGui umg, String name, String prod_id, Boolean makeNew) {
		try {
			productId = prod_id;
			productName = name;

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
					saveUpdates();
				}
			});

			JButton newButton = new JButton("Create");
			newButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {	
						Connection conn = DatabaseUtil.makeConnection();

						ResultSet result = conn.createStatement().executeQuery("SELECT COUNT(*) FROM products_cfa;");
						result.next();

						productId = Integer.toString(result.getInt(1) * 2);

						DatabaseUtil.closeConnection(conn);

						saveUpdates();
					} catch(Exception exc) {
						JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
					}
				}
			});

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

						statement.executeUpdate("DELETE FROM products_cfa WHERE id = " + prod_id + ";");
						statement.executeUpdate("DELETE FROM product_ingredients WHERE prod_id = " + prod_id + ";");

						DatabaseUtil.closeConnection(conn);
					} catch(Exception exc) {
						JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
					}

					updateMenuGui.filter();
					dispose();
					updateMenuGui.setVisible(true);
				}
			});

			topPanel.add(saveButton, BorderLayout.WEST);
			topPanel.add(newButton, BorderLayout.WEST);

			if (!makeNew) {
				topPanel.add(removeButton, BorderLayout.EAST);
			}

			Connection conn = DatabaseUtil.makeConnection();
			Statement statement = conn.createStatement();
        	ResultSet result = statement.executeQuery("SELECT * FROM products_cfa WHERE id = " + prod_id + " LIMIT 1;");
			
			result.next();

			nameLabeledFieldPanel = new LabeledFieldPanel("Name", name);
			costLabeledFieldPanel = new LabeledFieldPanel("Price", result.getString("price"));

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

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
    }

	public void saveUpdates() {
		try {
			Connection conn = DatabaseUtil.makeConnection();

			conn.createStatement().executeUpdate("DELETE FROM products_cfa WHERE id = " + productId + ";");
			conn.createStatement().executeUpdate("INSERT INTO products_cfa (id, name, price) VALUES (" + productId + ", '" + nameLabeledFieldPanel.fieldBox.getText() + "', " + Float.parseFloat(costLabeledFieldPanel.fieldBox.getText()) + ");");

			for (String ingredientId : inputMap.keySet()) {
				JTextField inputBox = inputMap.get(ingredientId);
				Integer quantity = Integer.parseInt(inputBox.getText());
				
				conn.createStatement().executeUpdate("DELETE FROM product_ingredients WHERE prod_id = " + productId + " AND item_id = " + ingredientId + ";");

				if (quantity > 0) {
					conn.createStatement().executeUpdate("INSERT INTO product_ingredients (prod_id, item_id, quantity) VALUES (" + productId + ", " + ingredientId + ", " + quantity + ");");
				}
			}

			DatabaseUtil.closeConnection(conn);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}

		dispose();
		updateMenuGui.filter();
		updateMenuGui.setVisible(true);	
	}
}
