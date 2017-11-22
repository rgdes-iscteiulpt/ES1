package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
	private JPanel scrollPanel;
	private JPanel buttonPanel;
	private JPanel fpfnPanel;
	private JLabel falsePositives;
	private JTextField numberOfFalsePositives;
	private String p = null;
	private JLabel falseNegatives;
	private JTextField numberOfFalseNegatives;
	private String n = null;
	private JButton resultButton;
	private JButton saveButton;
	private File fileSpam;
	private File fileHam;

	private FileManager r;
	// private ArrayList<Rule> rules;
	private String[][] data;

	public GUI() {
		r = new FileManager();
		// this.rules = r.getRules();
		createTable(r.getRules());
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
		saveButton = new JButton("	Save changes  ");
		
		// Ao selecionaro Botao "Save changes" guardamos o resultado dos pesos que demos às regras ao ficheiros rules.cf	
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.writeRulesFile();				
			}
		});
		
		resultButton = new JButton("	Result	");
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
				fileSpam = new File("spam.log.txt");
				r.readFileSpamHam(fileSpam);
				
				fileHam = new File("ham.log.txt");
				r.readFileSpamHam(fileHam);
			
				
				//Verificar se o peso esta associado a respetiva regra
//				checkWeights();
				
				p = Integer.toString(r.getNumberOfFalsePositives());
				numberOfFalsePositives.setText(p);
				n = Integer.toString(r.getNumberOfFalseNegatives());
				numberOfFalseNegatives.setText(n);
				
				
			}
		});

		buttonPanel.add(resultButton);
		buttonPanel.add(saveButton);
		
		
		
		
		// Painel dos falsos positivos e falsos negativos
		fpfnPanel = new JPanel();
		fpfnPanel.setLayout(new GridLayout(2,2));
		
		falsePositives = new JLabel("False Positives: ");
		falsePositives.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalsePositives = new JTextField();
		p = Integer.toString(r.getNumberOfFalsePositives());
		numberOfFalsePositives.setText(p);
		numberOfFalsePositives.setEditable(false);
		
		falseNegatives = new JLabel("False Negatives: ");
		falseNegatives.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalseNegatives = new JTextField();
		
//		numberOfFalsePositives.setText(n);
		numberOfFalseNegatives.setEditable(false);
		
		fpfnPanel.add(falsePositives);
		fpfnPanel.add(numberOfFalsePositives);
		fpfnPanel.add(falseNegatives);
		fpfnPanel.add(numberOfFalseNegatives);
		
		
		// Acrescentar os paineis à frame;
		frame.add(buttonPanel, BorderLayout.EAST);
		frame.add(scrollPanel);
		frame.add(fpfnPanel, BorderLayout.SOUTH);
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

	}

	public void checkWeights() {
		for (int j = 0; j < r.getRules().size(); j++) {
			System.out.println(r.getRules().get(j).getName() + " - " + r.getRules().get(j).getWeight());

		}
	}

}
