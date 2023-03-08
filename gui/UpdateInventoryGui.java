import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class UpdateInventoryGui extends JFrame {
	private HashMap<String, JTextField> inputMap;
	private HashMap<String, Integer> dataMap;

    public UpdateInventoryGui(ManagerGui managerGui) {
		setTitle("Chick-fi-la Manager - Update Inventory");

		inputMap = new HashMap<String, JTextField>();
		dataMap = new HashMap<String, Integer>();

		setSize(500, 500);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
				managerGui.setVisible(true);
            }
        });

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		JScrollPane contentScroll = new JScrollPane(contentPanel);
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JTextField searchBox = new JTextField(20);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateSearchResults(searchBox.getText(), contentPanel);
			}
		});

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveUpdates();
			}
		});

		topPanel.add(searchBox, BorderLayout.CENTER);
		topPanel.add(searchButton, BorderLayout.EAST);
		topPanel.add(saveButton, BorderLayout.WEST);

		add(topPanel, BorderLayout.NORTH);
		add(contentScroll, BorderLayout.CENTER);
		
		generateSearchResults("", contentPanel);
    }

	public void saveUpdates() {
		// can do atomic transaction here maybe instead, prolly doesnt matter tho
		try {
			Connection conn = DatabaseUtil.makeConnection();

			for (String key : inputMap.keySet()) {
				JTextField inputBox = inputMap.get(key);
				Integer quantity = Integer.parseInt(inputBox.getText());

				if (dataMap.get(key) != quantity) {
					Statement statement = conn.createStatement();

					String query = "UPDATE ingredients SET quantity = " + quantity + " WHERE name = '" + key + "';";
					statement.executeUpdate(query);
					
					dataMap.put(key, quantity);
				}
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
	}

	private void generateSearchResults(String input, JPanel contentPanel) {
		try {
			dataMap.clear();
			inputMap.clear();

			contentPanel.removeAll();
			contentPanel.revalidate();
			contentPanel.repaint();

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

				JLabel nameLabel = new JLabel(name);

				JTextField quantityBox = new JTextField(quantity);
				quantityBox.setPreferredSize(new Dimension(100, quantityBox.getPreferredSize().height));

				JPanel holder = new JPanel(new BorderLayout());
				holder.setBorder(new EmptyBorder(5, 5, 5, 5));
				holder.setLayout(new BoxLayout(holder, BoxLayout.X_AXIS));
				holder.setPreferredSize(new Dimension(holder.getPreferredSize().width, 30));
				holder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

				JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
				buttonPanel.add(quantityBox);
				
				holder.add(nameLabel, BorderLayout.WEST);
				holder.add(buttonPanel, BorderLayout.EAST);

				contentPanel.add(holder);

				inputMap.put(name, quantityBox);
				dataMap.put(name, Integer.parseInt(quantity));
			}

			DatabaseUtil.closeConnection(conn);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
	}
}
