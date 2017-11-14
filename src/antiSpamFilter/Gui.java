package antiSpamFilter;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Gui {

	private int numberOfRules;
	private ArrayList<Rule> rules;
	private ArrayList<Integer> weights;
	private JFrame frame;
	JTable table;
	private JPanel panel;

	public Gui() {
		init();
	}

	public void init() {
		frame = new JFrame("");
		frame.setSize(new Dimension(300, 300));
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rules List"));

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridBagLayout());

		String[] columns = { "Rules", "Weights" };
		String[][] data = { { "regra", "Peso" } };

		table = new JTable(data, columns);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.init();
	}

}
