import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class EditProductGui extends JFrame {
	private JTextField searchBox;
	private JButton searchButton;
	private JButton saveButton;

	private JPanel topPanel;
	private JPanel contentPanel;
	private JScrollPane contentScroll;

	private HashMap<String, JTextField> inputMap;
	private HashMap<String, Integer> dataMap;

    public EditProductGui(UpdateMenuGui updateMenuGui, String name, Integer id) {
		inputMap = new HashMap<String, JTextField>();
		dataMap = new HashMap<String, Integer>();

		setTitle("Chick-fi-la Manager - Update Menu - Edit Product");

		setSize(300, 300);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
				updateMenuGui.setVisible(true);
            }
        });

		JPanel searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JTextField searchBox = new JTextField(20);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateContent();
			}
		});

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		searchPanel.add(searchBox, BorderLayout.CENTER);
		searchPanel.add(searchButton, BorderLayout.EAST);
		searchPanel.add(saveButton, BorderLayout.WEST);
		searchPanel.add(removeButton, BorderLayout.WEST);

        contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		contentScroll = new JScrollPane(contentPanel);
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(searchPanel, BorderLayout.NORTH);
		add(contentScroll, BorderLayout.CENTER);

		populateContent();
    }

	public void saveUpdates() {

	}

	private void populateContent() {
		try {
			dataMap.clear();
			inputMap.clear();

			contentPanel.removeAll();
			contentPanel.revalidate();
			contentPanel.repaint();

			String input = searchBox.getText();
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
