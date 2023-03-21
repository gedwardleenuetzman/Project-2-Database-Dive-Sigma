import java.util.Date;

import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class SalesReportGui extends JFrame {
    
    private LabeledFieldPanel startDateFieldPanel;
    private LabeledFieldPanel endDateFieldPanel;
    
    public SalesReportGui(ManagerGui managerGui) {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        startDateFieldPanel = new LabeledFieldPanel("Start Date", "0");
        endDateFieldPanel = new LabeledFieldPanel("End Date", "0");

        mainPanel.add(startDateFieldPanel);
        mainPanel.add(endDateFieldPanel);

        add(mainPanel);
    }
}
