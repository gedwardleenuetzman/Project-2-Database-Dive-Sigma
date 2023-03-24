import java.util.Date;

import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * SalesReportGui that provides the report for the all sales
 */
public class SalesReportGui extends JFrame {

    private LabeledFieldPanel startDateFieldPanel;
    private LabeledFieldPanel endDateFieldPanel;

    public SalesReportGui(ManagerGui managerGui) {
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                managerGui.setVisible(true);
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(new EmptyBorder(5, 5, 0, 5));

        startDateFieldPanel = new LabeledFieldPanel("Start Date", "2000-01-01 00:00:00.00");
        endDateFieldPanel = new LabeledFieldPanel("End Date", "2030-01-01 00:00:00.00");

        mainPanel.add(startDateFieldPanel);
        mainPanel.add(endDateFieldPanel);

        JButton generateButton = new JButton("Generate");
        SalesReportGui self = this;

        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateFieldPanel.fieldBox.getText();
                String endDate = endDateFieldPanel.fieldBox.getText();

                RangedSalesGui gui = new RangedSalesGui(self, startDate, endDate);

                gui.setVisible(true);
                setVisible(false);
            }
        });

        topPanel.add(generateButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(startDateFieldPanel);
        mainPanel.add(endDateFieldPanel);

        add(mainPanel);
    }
}
