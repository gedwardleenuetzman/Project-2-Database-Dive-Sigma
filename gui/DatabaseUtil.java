import java.sql.*;
import javax.swing.*;

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
}