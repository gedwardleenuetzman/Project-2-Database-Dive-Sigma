import javax.swing.*;
import java.awt.event.*;

public class LoginGui extends JFrame {
    public LoginGui() {
        setTitle("Chick Fi La Login");

        ServerGui serverGui = new ServerGui(this);
        ManagerGui managerGui = new ManagerGui(this);
        
        setSize(275, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField inputBox = new JTextField(10);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = inputBox.getText();

                if (input.equals("server")) {
                    setVisible(false);
                    serverGui.setVisible(true);
                } else if (input.equals("manager")) {
                    setVisible(false);
                    managerGui.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed.");
                }
            }
		});

        JPanel panel = new JPanel();

        panel.add(inputBox);
        panel.add(loginButton);

        add(panel);

        setVisible(true);
    }
}