import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class ZReportGui extends JFrame {
	private IngredientPanel ingredientPanel;

    public ZReportGui(ManagerGui managerGui) {
		setTitle("Chick-fi-la Manager - Update Inventory");

		setSize(500, 500);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
				managerGui.setVisible(true);
            }
        });

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(5, 5, 0, 5));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JButton saveButton = new JButton("Save");
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveUpdates();
			}
		});

		topPanel.add(saveButton);

		ingredientPanel = new IngredientPanel();

		mainPanel.add(topPanel);
		mainPanel.add(ingredientPanel);
		add(mainPanel);
    }

	public void saveUpdates() {
		try {
			Connection conn = DatabaseUtil.makeConnection();

			for (String key : ingredientPanel.inputMap.keySet()) {
				JTextField inputBox = ingredientPanel.inputMap.get(key);
				Integer quantity = Integer.parseInt(inputBox.getText());

				if (ingredientPanel.dataMap.get(key) != quantity) {
					Statement statement = conn.createStatement();

					String query = "UPDATE ingredients SET quantity = " + quantity + " WHERE name = '" + key + "';";
					statement.executeUpdate(query);
					
					ingredientPanel.dataMap.put(key, quantity);
				}
			}

			DatabaseUtil.closeConnection(conn);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
	}
}
