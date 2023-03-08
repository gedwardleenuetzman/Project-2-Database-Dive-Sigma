import java.sql.*;
import javax.swing.*;

import java.util.HashMap;

public final class DatabaseUtil {
	private DatabaseUtil() {}

	public static Connection makeConnection() {
		Connection conn = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_sigma", "csce315331_sigma_master", "password");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return conn;
	}

	public static boolean closeConnection(Connection conn) {
		boolean success = false;

		try {
			conn.close();
			success = true;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "SQL Connection failed to close.");
			
			System.exit(0);
		}

		return success;
	}

	public static HashMap<String, String> getIngredientIdToName(Connection conn) {
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			

			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM ingredients");

			while (result.next()) {
				String id = result.getString("id");
				String name = result.getString("name");

				map.put(id, name);
			}

			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return map;

	}

	public static HashMap<String, String> getIngredientNameToId(Connection conn) {
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			

			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM ingredients");

			while (result.next()) {
				String id = result.getString("id");
				String name = result.getString("name");

				map.put(name, id);
			}

			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return map;
	}

	public static HashMap<String, String> getProductIngredients(String prod_id, Connection conn) {
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM product_ingredients WHERE prod_id = '" + prod_id + "';");

			while (result.next()) {
				map.put(result.getString("item_id"), result.getString("quantity"));
			}

			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return map;
	}
}