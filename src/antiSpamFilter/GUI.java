package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI {

	private JFrame frame;
	private JTable table;
	private JPanel scrollPanel;
	private JPanel buttonPanel;
	private JButton resultButton;
	private JButton saveButton;

	private FileReader r;
	private ArrayList<Rule> rules;

	public GUI() {
		r = new FileReader();
		this.rules = r.getRules();
		createTable(rules);
		init();

	}

	// Cria a Janela de Regras/Pesos

	public void init() {

		frame = new JFrame("Anti-Spam Filter Manual");
		frame.setLayout(new BorderLayout());

		// Acrescentar o scroll à tabela
		scrollPanel = new JPanel();
		scrollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rules List"));

		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.add(scroll);

		// Acrescentar ao painel de botões os botões;
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		resultButton = new JButton("	Result	 ");
		saveButton = new JButton("	Save changes	");
		buttonPanel.add(resultButton);
		buttonPanel.add(saveButton);

		// Acrescentar os paineis à frame;
		frame.add(buttonPanel, BorderLayout.EAST);
		frame.add(scrollPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// Cria a tabela correspondente a Janela

	public void createTable(ArrayList<Rule> rules) {
		String[] columns = { "Rules", "Weights" };
		String[][] data = new String[rules.size()][rules.size()];

		for (int i = 0; i < rules.size(); i++) {

			// Matriz data[Regras][Pesos]

			data[i][0] = rules.get(i).getName();

		}

		table = new JTable(data, columns) {

			// Tabela não editável na coluna das regras;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			};
		};

	}

}
