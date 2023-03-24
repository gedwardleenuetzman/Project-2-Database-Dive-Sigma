import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

/**
 * Allows the menu to be updated with new products.
 */
public class UpdateMenuGui extends JFrame {
	private SearchPanel searchPanel;
	private ScrollPanel contentPanel;

	private HashMap<Integer, HashMap<Integer, Integer>> ingredientMap;

	// private EditProductGui currentEditProductGui;

	public UpdateMenuGui(ManagerGui managerGui) {
		setTitle("Chick-fi-la Manager - Update Menu");

		ingredientMap = new HashMap<Integer, HashMap<Integer, Integer>>();

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

		searchPanel = new SearchPanel();
		searchPanel.searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter();
			}
		});

		contentPanel = new ScrollPanel();

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(searchPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel.scrollPane, BorderLayout.CENTER);

		JButton createButton = new JButton("Create");
		UpdateMenuGui self = this;

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				EditProductGui editProductGui = new EditProductGui(self, "New menu item", "-1", true);

				editProductGui.setVisible(true);
			}
		});

		topPanel.add(createButton);
		add(mainPanel);

		filter();
	}

	public void filter() {
		try {
			String input = searchPanel.searchBox.getText();

			ingredientMap.clear();

			contentPanel.removeAll();
			contentPanel.revalidate();
			contentPanel.repaint();

			Connection conn = DatabaseUtil.makeConnection();
			Statement statement = conn.createStatement();

			String query;

			if (input.equals("")) {
				query = "SELECT * FROM products_cfa;";
			} else {
				query = "SELECT * FROM products_cfa WHERE name LIKE '%" + input + "%';";
			}

			ResultSet result = statement.executeQuery(query);
			UpdateMenuGui self = this;

			while (result.next()) {
				String id = result.getString("id");
				String name = result.getString("name");

				JLabel nameLabel = new JLabel(name);
				JButton editButton = new JButton("Edit");

				editButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						EditProductGui editProductGui = new EditProductGui(self, name, id, false);

						editProductGui.setVisible(true);
					}
				});

				JPanel holder = new JPanel(new BorderLayout());
				holder.setBorder(new EmptyBorder(5, 5, 5, 5));
				holder.setLayout(new BoxLayout(holder, BoxLayout.X_AXIS));
				holder.setPreferredSize(new Dimension(holder.getPreferredSize().width, 40));
				holder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

				JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
				buttonPanel.add(editButton);

				holder.add(nameLabel, BorderLayout.WEST);
				holder.add(buttonPanel, BorderLayout.EAST);

				contentPanel.add(holder);
			}

			DatabaseUtil.closeConnection(conn);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
	}
}
