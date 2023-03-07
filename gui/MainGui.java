import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MainGui extends JFrame {
    public static Connection connection = null;

    public MainGui(String[] args) {
        super("Chick Fi La");

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_sigma", "csce315331_sigma_master", "password");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 500);
        setLocationRelativeTo(null);

        if (args.length == 1 && args[0].equals("-m")) {
            JOptionPane.showMessageDialog(null, "Opened manager menu successfully");
            // manager mode
            // ManagerGui gui = new ManagerGui();
            // gui.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Opened server menu successfully");
            // server mode
            // ServerGui gui = new ServerGui();
            // gui.setVisible(true);
        }
    }

	public static void main(String[] args) {
    	MainGui gui = new MainGui(args);
    	gui.setVisible(true);
   	}
}