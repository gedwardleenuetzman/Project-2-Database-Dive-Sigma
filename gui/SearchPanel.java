import java.awt.*;
import javax.swing.*;

public class SearchPanel extends JPanel {
	public JButton searchButton;
	public JTextField searchBox;

	public SearchPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		searchBox = new JTextField();
		searchButton = new JButton("Search");

		add(searchBox, BorderLayout.CENTER);
		add(searchButton, BorderLayout.EAST);

		setPreferredSize(new Dimension(getPreferredSize().width, 30));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
	}
}