import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

public class UpdateMenuGui extends JFrame {
	private JTextField searchBox;
	private JPanel contentPanel;

	private HashMap<Integer, HashMap<Integer, Integer>> ingredientMap;

	//private EditProductGui currentEditProductGui;

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

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		searchBox = new JTextField(20);
		JButton searchButton = new JButton("Search");

		topPanel.add(searchBox, BorderLayout.CENTER);
		topPanel.add(searchButton, BorderLayout.EAST);

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateContent();
			}
		});

        contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		JScrollPane contentScroll = new JScrollPane(contentPanel);
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(topPanel, BorderLayout.NORTH);
		add(contentScroll, BorderLayout.CENTER);

		populateContent();
    }

	private void populateContent() {
		try {
			ingredientMap.clear();

			contentPanel.removeAll();
			contentPanel.revalidate();
			contentPanel.repaint();

			String input = searchBox.getText();

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
				Integer id = result.getInt("id");
				String name = result.getString("name");

				JLabel nameLabel = new JLabel(name);
				JButton editButton = new JButton("Edit");

				editButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						EditProductGui editProductGui = new EditProductGui(self, name, id);
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
