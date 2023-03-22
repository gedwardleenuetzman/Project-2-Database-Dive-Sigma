import java.awt.event.*;
import javax.swing.*;

public class ManagerGui extends JFrame implements ActionListener {
    // Buttons
	private JButton updateMenuButton;
	private JButton updateInventoryButton;
    private JButton xReportButton;
    private JButton zReportButton;
    private JButton salesReportButton;
    private JButton excessReportButton;

    // Setting Class Variables
    private UpdateInventoryGui updateInventoryGui;
    private UpdateMenuGui updateMenuGui;
    private XReportGui xReportGui;
    private ZReportGui zReportGui;
    
    private SalesReportGui salesReportGui;
    private ExcessReportGui excessReportGui;

    public ManagerGui(LoginGui loginGui) {
		super("Chick-fi-la Manager");

        updateInventoryGui = new UpdateInventoryGui(this);
        updateMenuGui = new UpdateMenuGui(this);
        xReportGui = new XReportGui(this);
        zReportGui = new ZReportGui(this);
        salesReportGui = new SalesReportGui(this);
        excessReportGui = new ExcessReportGui(this);
        
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                loginGui.setVisible(true);
            }
        });

        updateInventoryButton = new JButton("Update Inventory");
		updateMenuButton = new JButton("Update Menu");
        xReportButton = new JButton("View X Report");
        zReportButton = new JButton("View Z Report");
        
		
		salesReportButton = new JButton("Sales Report");
        excessReportButton = new JButton("Excess Report");

        JPanel panel = new JPanel();

        panel.add(updateInventoryButton);
        panel.add(updateMenuButton);
        panel.add(xReportButton);
        panel.add(zReportButton);
        panel.add(salesReportButton);
        panel.add(excessReportButton);

        getContentPane().add(panel);

        updateInventoryButton.addActionListener(this);
        updateMenuButton.addActionListener(this);
        xReportButton.addActionListener(this);
        zReportButton.addActionListener(this);
        salesReportButton.addActionListener(this);
        excessReportButton.addActionListener(this);
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateInventoryButton) {
            setVisible(false);
            updateInventoryGui.setVisible(true);
        } else if (e.getSource() == updateMenuButton) {
            setVisible(false);
            updateMenuGui.setVisible(true);
        } else if (e.getSource() == zReportButton) {
            setVisible(false);
            zReportGui.setVisible(true);
        } else if (e.getSource() == xReportButton) {
            setVisible(false);
            xReportGui.setVisible(true);
        } else if (e.getSource() == salesReportButton) {
            setVisible(false);
            salesReportGui.setVisible(true);
        } else if (e.getSource() == excessReportButton) {
            setVisible(false);
            excessReportGui.setVisible(true);
        }
    }
}
