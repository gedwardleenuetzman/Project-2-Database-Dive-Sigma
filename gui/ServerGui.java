import java.sql.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerGui extends JFrame implements ActionListener {
	private JButton makeOrderButton;

    public ServerGui(LoginGui loginGui) {
		setTitle("Chick-fil-a Server");
        
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        makeOrderButton = new JButton("Make Order");
		
        JPanel panel = new JPanel();

        panel.add(makeOrderButton);

        getContentPane().add(panel);

        makeOrderButton.addActionListener(this);
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Close")) {
            
        }
    }
}
