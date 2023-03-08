import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class UpdateInventoryGui extends JFrame implements ActionListener {
	private JTextField searchBox;
	private JButton searchButton;
	private JButton saveButton;

	private JPanel topPanel;
	private JPanel contentPanel;
	private JScrollPane contentScroll;

	private HashMap<String, JTextField> inputMap;
	private HashMap<String, Integer> dataMap;

    public UpdateInventoryGui(ManagerGui managerGui) {
		inputMap = new HashMap<String, JTextField>();
		dataMap = new HashMap<String, Integer>();

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

		topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		searchBox = new JTextField(20);
		searchBox.addActionListener(this);
		topPanel.add(searchBox, BorderLayout.CENTER);

		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		topPanel.add(searchButton, BorderLayout.EAST);

		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		topPanel.add(saveButton, BorderLayout.WEST);

        contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		contentScroll = new JScrollPane(contentPanel);
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(topPanel, BorderLayout.NORTH);
		add(contentScroll, BorderLayout.CENTER);

		populateContent();
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
					System.out.println(key + " = " + quantity);
				}
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
	}

    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == searchButton) {
            populateContent();
        } else if (e.getSource() == saveButton) {
			saveUpdates();
		}
    }

	private void populateContent() {
		try {
			dataMap.clear();
			inputMap.clear();

			contentPanel.removeAll();
			contentPanel.revalidate();
			contentPanel.repaint();

			String input = searchBox.getText();

			System.out.println("Querying ingredients with: " + input);

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

				System.out.println("Rendering " + name + ": " + quantity);
				
				JLabel nameLabel = new JLabel(name);
				nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				nameLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameLabel.getPreferredSize().height));

				JTextField quantityBox = new JTextField(quantity);
				quantityBox.setAlignmentX(Component.LEFT_ALIGNMENT);
				quantityBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, quantityBox.getPreferredSize().height));

				inputMap.put(name, quantityBox);
				dataMap.put(name, Integer.parseInt(quantity));

				contentPanel.add(nameLabel);
				contentPanel.add(quantityBox);
			}

			DatabaseUtil.closeConnection(conn);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error accessing Database: " + e.toString());
		}
	}
}
