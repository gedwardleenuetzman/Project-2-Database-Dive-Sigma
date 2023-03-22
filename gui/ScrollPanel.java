import javax.swing.*;
import javax.swing.border.*;

public class ScrollPanel extends JPanel {
	public JScrollPane scrollPane;

	public ScrollPanel() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		scrollPane = new JScrollPane(this);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
}