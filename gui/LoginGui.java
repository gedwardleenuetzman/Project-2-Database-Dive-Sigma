import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGui extends JFrame implements ActionListener {
    private JButton loginButton;
    private JTextField inputBox;
    private Connection conn;
    
    private ServerGui serverGui;
    private ManagerGui managerGui;

    public LoginGui() {
        super("Chick Fi La Login");

        serverGui = new ServerGui(this);
        managerGui = new ManagerGui(this);
        
        setSize(275, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        inputBox = new JTextField(10);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(inputBox);
        panel.add(loginButton);

        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed");

        if (e.getSource() == loginButton) {
            System.out.println("Login button pressed");

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
    }
}