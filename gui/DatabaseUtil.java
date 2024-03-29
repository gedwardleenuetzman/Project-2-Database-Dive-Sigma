import java.sql.*;
import javax.swing.*;

import java.util.HashMap;

/**
 * Access the sql database with our login information
 */
public final class DatabaseUtil {

	private DatabaseUtil() {
	}

	/**
	 * @return Connection to the sql database
	 */
	public static Connection makeConnection() {
		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_sigma",
					"csce315331_sigma_master", "password");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return conn;
	}

	/**
	 * @param conn Closes connection to sql database
	 * @return boolean
	 */
	public static boolean closeConnection(Connection conn) {
		boolean success = false;

		try {
			conn.close();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "SQL Connection failed to close.");

			System.exit(0);
		}

		return success;
	}

	/**
	 * @param conn connection to the database to access whatever table is needed by
	 *             queries
	 * @return HashMap<String, String>
	 */
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return map;

	}

	/**
	 * @param conn connection to the database to access whatever table is needed by
	 *             queries
	 * @return HashMap<String, String>
	 */
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

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return map;
	}

	/**
	 * @param prod_id the product id in order to push into hash
	 * @param conn    connection to the database to access whatever table is needed
	 *                by queries
	 * @return HashMap<String, String>
	 */
	public static HashMap<String, String> getProductIngredients(String prod_id, Connection conn) {
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement
					.executeQuery("SELECT * FROM product_ingredients WHERE prod_id = '" + prod_id + "';");

			while (result.next()) {
				map.put(result.getString("item_id"), result.getString("quantity"));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return map;
	}

	/**
	 * @param conn
	 * @return HashMap<String, Integer>
	 */
	public static HashMap<String, Integer> getInventoryData(Connection conn) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM ingredients;");

			while (result.next()) {
				map.put(result.getString("id"), result.getInt("quantity"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return map;
	}

	/**
	 * @param conn
	 * @param tab
	 * @return int
	 */
	public static int generateTableId(Connection conn, String tab) {
		Integer id = -1;

		try {
			ResultSet result = conn.createStatement().executeQuery("SELECT COUNT(*) FROM " + tab + ";");
			result.next();
			id = result.getInt(1) + 1;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();

			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return id;
	}

	/**
	 * @param conn
	 * @return HashMap<String, Integer>
	 */
	public static HashMap<String, Integer> getRestock(Connection conn) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM restock_threshold;");

			while (result.next()) {
				String id = result.getString("ingredient_id");
				Integer quan = result.getInt("quantity");

				map.put(id, quan);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		return map;
	}
}
//////////////////////////////////////////////////////////////////////