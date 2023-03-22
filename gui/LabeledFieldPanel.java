import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class LabeledFieldPanel extends JPanel {
	public JTextField fieldBox;
	public JLabel fieldLabel;
	
	public LabeledFieldPanel(String name, String value) {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(this.getPreferredSize().width, 30));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		fieldLabel = new JLabel(name);

		fieldBox = new JTextField(value);
		fieldBox.setPreferredSize(new Dimension(150, fieldBox.getPreferredSize().height));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		buttonPanel.add(fieldBox);
				
		add(fieldLabel, BorderLayout.WEST);
		add(buttonPanel, BorderLayout.EAST);
	}
}