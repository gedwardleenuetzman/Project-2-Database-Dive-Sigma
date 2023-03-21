import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class PlaceOrderGui extends JFrame {
	private HashMap<String, JTextField> inputMap;

	private ScrollPanel scrollPanel;
	ServerGui serverGui;

    public PlaceOrderGui(ServerGui sg) {
		try {
			serverGui = sg;

			inputMap = new HashMap<String, JTextField>();

			setTitle("Chick-fi-la Server - Place Order");

			setSize(400, 400);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
					serverGui.setVisible(true);
				}
			});
			
			JPanel mainPanel = new JPanel();
			mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

			JPanel topPanel = new JPanel(new BorderLayout());
			topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
			topPanel.setBorder(new EmptyBorder(5, 5, 0, 5));

			JButton placeButton = new JButton("Place");
			placeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					placeOrder();
				}
			});

			topPanel.add(placeButton, BorderLayout.WEST);

			Connection conn = DatabaseUtil.makeConnection();
			Statement statement = conn.createStatement();
        	ResultSet result = statement.executeQuery("SELECT * FROM products_cfa");
			
			scrollPanel = new ScrollPanel();

			while (result.next()) {
				LabeledFieldPanel label = new LabeledFieldPanel(result.getString("name"), "0");
				
				scrollPanel.add(label);
				inputMap.put(result.getString("id"), label.fieldBox);
			}
			
			mainPanel.add(topPanel, BorderLayout.NORTH);
			mainPanel.add(scrollPanel.scrollPane, BorderLayout.CENTER);

			add(mainPanel);

			DatabaseUtil.closeConnection(conn);

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
    }

	public void placeOrder() {
		try {
			// id, total price, day, time /orders
			// order id, prodcut id, quantity /order_products
			// daily_orders
			// daily_order_products

			Connection conn = DatabaseUtil.makeConnection();
			HashMap<String, Integer> inv = DatabaseUtil.getInventoryData(conn);
			HashMap<String, Integer> using = new HashMap<String, Integer>();
			HashMap<String, String> id2name = DatabaseUtil.getIngredientIdToName(conn);

			for (String productId : inputMap.keySet()) {
				JTextField inputBox = inputMap.get(productId);
				Integer quantity = Integer.parseInt(inputBox.getText());

				ResultSet ingredients = conn.createStatement().executeQuery("SELECT * FROM product_ingredients WHERE prod_id = " + productId + ";");
				
				while (ingredients.next()) {
					String ingredientId = ingredients.getString("item_id");

					if (using.get(ingredientId) != null) {
						using.put(ingredientId, using.get(ingredientId) + (quantity * Integer.parseInt(ingredients.getString("quantity"))));
					} else {
						using.put(ingredientId, (quantity * Integer.parseInt(ingredients.getString("quantity"))));
					}
				}				
			}

			for (String ingredientId : using.keySet()) {
				Integer quantity = using.get(ingredientId);

				if (inv.get(ingredientId) < quantity) {
					JOptionPane.showMessageDialog(null, "Unable to make order. Not enough " + id2name.get(ingredientId));
					return;
				}
			}

			// order can be made now so reduce inventory
			for (String ingredientId : using.keySet()) {
				Integer quantity = using.get(ingredientId);
				conn.createStatement().executeUpdate("UPDATE ingredients SET quantity = quantity - " + quantity + " WHERE id = " + ingredientId);
			}

			// update order tables
			//conn.createStatement().executeUpdate("INSERT INTO orders ();");

			DatabaseUtil.closeConnection(conn);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
				e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

		}

		dispose();
		serverGui.setVisible(true);
	}
}
