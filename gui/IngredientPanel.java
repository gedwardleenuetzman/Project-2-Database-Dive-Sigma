import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

/**
 * Provides a panel to be used by the manager for each ingredient
 */
public class IngredientPanel extends JPanel {

	public HashMap<String, JTextField> inputMap;
	public HashMap<String, Integer> dataMap;

	private ScrollPanel scrollPanel;

	public IngredientPanel() {
		inputMap = new HashMap<String, JTextField>();
		dataMap = new HashMap<String, Integer>();

		scrollPanel = new ScrollPanel();

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		SearchPanel searchPanel = new SearchPanel();

		searchPanel.searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter(searchPanel.searchBox.getText());
			}
		});

		add(searchPanel, BorderLayout.NORTH);
		add(scrollPanel.scrollPane, BorderLayout.CENTER);

		filter("");
	}

	/**
	 * @param input depending on the string , it will filter the word
	 */
	public void filter(String input) {
		try {
			dataMap.clear();
			inputMap.clear();

			scrollPanel.removeAll();
			scrollPanel.revalidate();
			scrollPanel.repaint();

			Connection conn = DatabaseUtil.makeConnection();
			Statement statement = conn.createStatement();

			String query;

			if (input.equals("")) {
				query = "SELECT * FROM ingredients;";
			} else {
				query = "SELECT * FROM ingredients WHERE name LIKE '%" + input + "%';";
			}

			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
				String quantity = result.getString("quantity");
				String name = result.getString("name");

				LabeledFieldPanel panel = new LabeledFieldPanel(name, quantity);

				scrollPanel.add(panel);

				inputMap.put(name, panel.fieldBox);
				dataMap.put(name, Integer.parseInt(quantity));
			}

			DatabaseUtil.closeConnection(conn);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
	}
}