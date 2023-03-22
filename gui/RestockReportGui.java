import java.util.Date;

import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.HashMap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestockReportGui extends JFrame {
    public RestockReportGui(ManagerGui gui) {
		try {
			setTitle("Restock Report");

			setSize(400, 200);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
					gui.setVisible(true);
				}
			});

			JPanel mainPanel = new JPanel();
			mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            Connection conn = DatabaseUtil.makeConnection();
            HashMap<String, Integer> inv = DatabaseUtil.getInventoryData(conn);
            HashMap<String, Integer> restock = DatabaseUtil.getRestock(conn);
            HashMap<String, String> id2name = DatabaseUtil.getIngredientIdToName(conn);
			
			for (String id : inv.keySet()) {
                Integer invCount = inv.get(id);
                Integer restockCount = restock.get(id);

                if (invCount < restockCount) {
				    JLabel label = new JLabel(id2name.get(id) + " needs to be restocked (" + invCount + " / " + restockCount + ")");
				
				    mainPanel.add(label);
                }
			}
					
			DatabaseUtil.closeConnection(conn);
			
			add(mainPanel); 
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQL Connection failed. Please retry action.");
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}
    }
}