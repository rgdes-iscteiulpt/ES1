package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI {

	private JFrame frame;
	private JTable table;
	private JTable table1;
	private JPanel scrollPanel;
	private JPanel scrollPanel2;
	private JPanel b1;
	private JPanel b2;
	private JPanel buttonPanel1;
	private JPanel buttonPanel2;
	private JPanel fpfnPanel;
	private JPanel fpfnPanel2;
	private JLabel falsePositives;
	private JLabel falsePositives2;
	private JTextField numberOfFalsePositives;
	private JTextField numberOfFalsePositives2;
	private JTextField textrules;
	private JTextField textspam;
	private JTextField textham;
	private String p = null;
	private JLabel falseNegatives;
	private JLabel falseNegatives2;
	private JTextField numberOfFalseNegatives;
	private JTextField numberOfFalseNegatives2;
	private String n = null;
	private JButton resultButton;
	private JButton resultButton2;
	private JButton saveButton;
	private JButton saveButton2;


	private FileManager r;
	// private ArrayList<Rule> rules;
	private String[][] data;

	
	public GUI() {
		init();
	}

	// Cria a Janela de Regras/Pesos
	public void init() {
		frame = new JFrame("Anti-Spam Configuration For Professional Mail-Box");
		frame.setLayout(new BorderLayout());

		JPanel path = new JPanel();
		path.setLayout(new BorderLayout());

		JPanel esquerda = new JPanel();
		esquerda.setLayout(new BorderLayout());

		JLabel l1 = new JLabel("         Escolher Path:      ");
		esquerda.add(l1);
		
		JPanel direita = new JPanel();
		direita.setLayout(new GridLayout(6, 1));

		JLabel l2 = new JLabel("		rules.cf		"); //rules.cf
		JLabel l3 = new JLabel("		spam.log		"); //spam.log.txt
		JLabel l4 = new JLabel("		ham.log			"); //ham.log.txt
		
		textrules = new JTextField();
		textspam = new JTextField();
		textham = new JTextField();

		direita.add(l2);
		direita.add(textrules);
		direita.add(l3);
		direita.add(textspam);
		direita.add(l4);
		direita.add(textham);
		
		JButton update = new JButton("		Get Rules		");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r = new FileManager(textrules.getText());
				// this.rules = r.getRules();
				createTable(r.getRules());
				updateGUI();
			}
		});

		path.add(update, BorderLayout.SOUTH); //só depois de darmos path para rules é que podes ler as rules e fazer o resto da gui
		path.add(esquerda, BorderLayout.WEST);
		path.add(direita, BorderLayout.EAST);
		frame.add(path, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void updateGUI() {
		//1 painel para cada tipo de configuração
		JPanel manual = new JPanel();
		JPanel auto = new JPanel();
				
		manual.setLayout(new BorderLayout());
		auto.setLayout(new BorderLayout());
				
		//títulos das configurações
		JLabel m = new JLabel("Configuração Manual");
		JLabel a = new JLabel("Configuração Automática");
					
		manual.add(m, BorderLayout.NORTH);
		auto.add(a, BorderLayout.NORTH);

		// Acrescentar o scroll à tabela
		scrollPanel = new JPanel();
		scrollPanel2 = new JPanel();
		
		scrollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rules List"));
		scrollPanel2.setBorder(new TitledBorder(new EtchedBorder(), "Rules List"));
		
		JScrollPane scroll = new JScrollPane(table);
		JScrollPane scroll2 = new JScrollPane(table1);

		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		scrollPanel.add(scroll);
		scrollPanel2.add(scroll2);


		manual.add(scrollPanel, BorderLayout.CENTER);
		auto.add(scrollPanel2, BorderLayout.CENTER);
		
		// Acrescentar ao painel de botões os botões;
		b1 = new JPanel();
		b2 = new JPanel();
		
		b1.setLayout(new BorderLayout());
		b2.setLayout(new BorderLayout());
		
		buttonPanel1 = new JPanel();
		buttonPanel2 = new JPanel();
		
		buttonPanel1.setLayout(new GridLayout(1, 2));
		buttonPanel2.setLayout(new GridLayout(1, 2));
		
		saveButton = new JButton("	Save changes  ");
		saveButton2 = new JButton("	Save changes  ");
		
		// Ao selecionaro Botao "Save changes" guardamos o resultado dos pesos que demos às regras ao ficheiros rules.cf	
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.writeRulesFile();				
			}
		});
		
		resultButton = new JButton("	Result	");
		resultButton2 = new JButton("	Result	");

		resultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// A cada peso introduzido, introduz no arraylist de regras o
				// peso correspondente a cada regra

				int weight = 0;
				for (int i = 0; i < r.getRules().size(); i++) {
					String s = (String) table.getValueAt(i, 1);
					
					if (s == null) {
						s = "0";
					}
					
					weight = Integer.parseInt(s);
					r.getRules().get(i).setWeight(weight);
				
				}

				//Ler ficheiros Spam.log e Ham.log
				r.readFileSpam(textspam.getText());
				
				r.readFileHam(textham.getText());
			
				// Introduzir o número de falsos positivos e falsos negativos na janela
				p = Integer.toString(r.getNumberOfFalsePositives());
				numberOfFalsePositives.setText(p);
				n = Integer.toString(r.getNumberOfFalseNegatives());
				numberOfFalseNegatives.setText(n);
				
				
			}
		});

		buttonPanel1.add(resultButton);
		buttonPanel1.add(saveButton);
			
		buttonPanel2.add(resultButton2);
		buttonPanel2.add(saveButton2);
		
		// Painel dos falsos positivos e falsos negativos
		fpfnPanel = new JPanel();
		fpfnPanel2 = new JPanel();
		
		fpfnPanel.setLayout(new GridLayout(2,2));
		fpfnPanel2.setLayout(new GridLayout(2,2));
		
		falsePositives = new JLabel("False Positives: ");
		falsePositives.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalsePositives = new JTextField();
		numberOfFalsePositives.setEditable(false);
		
		falsePositives2 = new JLabel("False Positives: ");
		falsePositives2.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalsePositives2 = new JTextField();
		numberOfFalsePositives2.setEditable(false);

		falseNegatives = new JLabel("False Negatives: ");
		falseNegatives.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalseNegatives = new JTextField();
		numberOfFalseNegatives.setEditable(false);
		
		falseNegatives2 = new JLabel("False Negatives: ");
		falseNegatives2.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalseNegatives2 = new JTextField();
		numberOfFalseNegatives2.setEditable(false);

		fpfnPanel.add(falsePositives);
		fpfnPanel.add(numberOfFalsePositives);
		fpfnPanel.add(falseNegatives);
		fpfnPanel.add(numberOfFalseNegatives);
		
		fpfnPanel2.add(falsePositives2);
		fpfnPanel2.add(numberOfFalsePositives2);
		fpfnPanel2.add(falseNegatives2);
		fpfnPanel2.add(numberOfFalseNegatives2);
		
		//Adicionais todos os botoes ao painel dos botoes
		b1.add(buttonPanel1, BorderLayout.NORTH);
		b1.add(fpfnPanel, BorderLayout.SOUTH);

		b2.add(buttonPanel2, BorderLayout.NORTH);
		b2.add(fpfnPanel2, BorderLayout.SOUTH);

		manual.add(b1, BorderLayout.SOUTH);
		auto.add(b2, BorderLayout.SOUTH);

		// Acrescentar os paineis à frame;
		frame.add(manual, BorderLayout.WEST);
		frame.add(auto, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	// Cria a tabela correspondente a Janela

	public void createTable(ArrayList<Rule> rules) {
		String[] columns = { "Rules", "Weights" };
		data = new String[r.getRules().size()][r.getRules().size()];

		for (int i = 0; i < rules.size(); i++) {

			// Matriz data[Regras][Pesos]

			data[i][0] = r.getRules().get(i).getName();

		}

		table = new JTable(data, columns) {

			// Tabela não editável na coluna das regras;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			};
		};
		table1 = new JTable(data, columns) {
			// Tabela não editável na coluna das regras;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			};
		};
	}

	public void checkWeights() {
		for (int j = 0; j < r.getRules().size(); j++) {
			System.out.println(r.getRules().get(j).getName() + " - " + r.getRules().get(j).getWeight());

		}
	}

}
