import java.util.Date;

import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcessInventoryGui extends JFrame {
    public ExcessInventoryGui(ExcessReportGui gui, String startDate, String endDate) {
		try {
			setTitle(startDate + " to " + endDate + " Sales Report");

			setSize(400, 200);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
					gui.setVisible(true);
				}
			});

			JPanel mainPanel = new JPanel();
			mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			
			Connection conn = DatabaseUtil.makeConnection();
			String range_sql = "SELECT * FROM orders WHERE time BETWEEN ? AND ?;";
			PreparedStatement range_pstmt = conn.prepareStatement(range_sql);
			Statement statement = conn.createStatement();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date startDate2 = dateFormat.parse(startDate);
			Date endDate2 = dateFormat.parse(endDate);
			
			range_pstmt.setTimestamp(1, new Timestamp(startDate2.getTime()));
			range_pstmt.setTimestamp(2, new Timestamp(endDate2.getTime()));

			ResultSet orderResult = range_pstmt.executeQuery();

			HashMap<String, Integer> product_quantity = new HashMap<String, Integer>();
            HashMap<String, Integer> ingredient_sold = new HashMap<String, Integer>();
            HashMap<String, Integer> ingredient_inventory = new HashMap<String, Integer>();
			HashMap<String, Float> totals = new HashMap<String, Float>();

			while (orderResult.next()) {
				ResultSet order_products = statement.executeQuery("SELECT * FROM order_products WHERE order_id = " + orderResult.getInt("id") + ";");
				Statement statement2 = conn.createStatement();

				while (order_products.next()) {
					ResultSet product = statement2.executeQuery("SELECT * FROM products_cfa WHERE id = " + order_products.getInt("prod_id") + " LIMIT 1;");
                    product.next();

					if (product.getString("name") != null) {
						String name = product.getString("name");

						if (product_quantity.get(name) != null) {
							product_quantity.put(name, product_quantity.get(name) + order_products.getInt("quantity"));
							totals.put(name, totals.get(name) + order_products.getInt("quantity") * product.getFloat("price"));
						} else {
							product_quantity.put(name, order_products.getInt("quantity"));
							totals.put(name,  order_products.getInt("quantity") * product.getFloat("price"));
						}
					}

                    Statement statement3 = conn.createStatement();
                    Statement statement4 = conn.createStatement();

                    ResultSet ingredient = statement3.executeQuery("SELECT * FROM product_ingredients WHERE prod_id = " + product.getInt("id") + ";");

                    while(ingredient.next()){
                        ResultSet ingredient_name = statement4.executeQuery("SELECT * FROM ingredients WHERE id = " + ingredient.getInt("prod_id") + " LIMIT 1;");
                        ingredient_name.next();
                        String ingredient_String = ingredient_name.getString("name");

                        if(ingredient_sold.get(ingredient_String) != null){
                            ingredient_sold.put(ingredient_String, ingredient_sold.get(ingredient_String) + ingredient.getInt("quantity"));

                        }
                        else{
                            ingredient_sold.put(ingredient_String, ingredient.getInt("quantity"));
                            ingredient_inventory.put(ingredient_String, ingredient_name.getInt("quantity"));
                        }
                    }
				}
			}

			JLabel title_label = new JLabel("Items that sold less than 10% of their stock");
			mainPanel.add(title_label);
			for (String name : ingredient_sold.keySet()) {

				float amountSold = ingredient_sold.get(name);
                float amountInventory = ingredient_sold.get(name) + ingredient_inventory.get(name);
                float percentage = amountSold / amountInventory;

                System.out.println(name + " inventory " + amountInventory + " sold " + amountSold);

                if(percentage < .1){
                    JLabel label = new JLabel(name + " sold " + percentage + "% percent of its inventory : ");
				
                    mainPanel.add(label);
                }
			}
					
			DatabaseUtil.closeConnection(conn);
			
			add(mainPanel); 
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}
    }
}
