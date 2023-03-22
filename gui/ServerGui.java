import java.awt.event.*;
import javax.swing.*;

public class ServerGui extends JFrame implements ActionListener {
	private JButton placeOrderButton;

    private PlaceOrderGui placeOrderGui;
    
    public ServerGui(LoginGui loginGui) {
		super("Chick-fi-la Server");

        placeOrderGui = new PlaceOrderGui(this);
        
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                loginGui.setVisible(true);
            }
        });

        placeOrderButton = new JButton("Place Order");

        JPanel panel = new JPanel();

        panel.add(placeOrderButton);

        add(panel);

        placeOrderButton.addActionListener(this);
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == placeOrderButton) {
            setVisible(false);
            placeOrderGui.reset();
            placeOrderGui.setVisible(true);
        }
    }
}
