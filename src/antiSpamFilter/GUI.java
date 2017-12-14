package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

	public JFrame frame = new JFrame("Anti-Spam Configuration For Professional Mail-Box");
	private JTable table;
	private JTable table1;
	private JPanel scrollPanel = new JPanel();
	private JPanel scrollPanel2 = new JPanel();
	private JPanel b1 = new JPanel();
	private JPanel b2 =  new JPanel();;
	private JPanel buttonPanel1 = new JPanel();
	private JPanel buttonPanel2 = new JPanel();
	private JPanel fpfnPanel = new JPanel();
	private JPanel fpfnPanel2 = new JPanel();
	public JLabel falsePositives = new JLabel("False Positives: ");
	public JLabel falsePositives2 = new JLabel("False Positives: ");
	private JTextField numberOfFalsePositives = new JTextField();
	private JTextField numberOfFalsePositives2 = new JTextField();
	private JTextField textrules = new JTextField();
	private JTextField textspam = new JTextField();
	private JTextField textham = new JTextField();
	public JLabel l1 = new JLabel("         Escolher Path:      ");
	public JLabel l2 = new JLabel("		rules.cf		"); //rules.cf
	public JLabel l3 = new JLabel("		spam.log		"); //spam.log.txt
	public JLabel l4 = new JLabel("		ham.log			"); //ham.log.txt
	private JPanel manual = new JPanel();
	private JPanel auto = new JPanel();
	public JLabel m = new JLabel("Configuração Manual");
	public JLabel a = new JLabel("Configuração Automática");
	private String p = null;
	private String pa = null;
	public JLabel falseNegatives = new JLabel("False Negatives: ");
	public JLabel falseNegatives2 = new JLabel("False Negatives: ");
	private JTextField numberOfFalseNegatives = new JTextField();
	private JTextField numberOfFalseNegatives2 = new JTextField();
	private String n = null;
	private String na = null;
	public JButton resultButton = new JButton("	Result	");;
	public JButton resultButton2 = new JButton("	Result	");
	public JButton saveButton = new JButton("	Save changes  ");
	public JButton saveButton2 = new JButton("	Save changes  ");
	public JButton update = new JButton("		Get Rules		");
	private JPanel path = new JPanel();
	private JPanel esquerda = new JPanel();
	private JPanel direita = new JPanel();
	private boolean updateBoolean =false;
	private boolean resultBoolean =false;
	private boolean result2Boolean =false;
	private boolean saveBoolean =false;
	private boolean save2Boolean =false;
	
	private FileManager r;
	private String[][] data;
	private String[][] data2;
	
	public GUI() {
		init();
	}

	// Cria a Janela de Regras/Pesos
	public void init() {
		frame = new JFrame("Anti-Spam Configuration For Professional Mail-Box");
		frame.setLayout(new BorderLayout());

		path.setLayout(new BorderLayout());
		
		esquerda.setLayout(new BorderLayout());
		esquerda.add(l1);
		
		direita.setLayout(new GridLayout(6, 1));

		direita.add(l2);
		direita.add(textrules);
		direita.add(l3);
		direita.add(textspam);
		direita.add(l4);
		direita.add(textham);
		
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentUpdateButton();
				updateBoolean=true;
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
		manual.setLayout(new BorderLayout());
		auto.setLayout(new BorderLayout());
									
		manual.add(m, BorderLayout.NORTH);
		auto.add(a, BorderLayout.NORTH);

		// Acrescentar o scroll à tabela	
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
		b1.setLayout(new BorderLayout());
		b2.setLayout(new BorderLayout());
		
		buttonPanel1.setLayout(new GridLayout(1, 2));
		buttonPanel2.setLayout(new GridLayout(1, 2));
		
		addButtons();
		
		// Painel dos falsos positivos e falsos negativos
		addFPanel();

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
		data2 = new String[r.getRules().size()][r.getRules().size()];

		for (int i = 0; i < rules.size(); i++) {

			// Matriz data[Regras][Pesos]

			data[i][0] = r.getRules().get(i).getName();
			data2[i][0] = r.getRules().get(i).getName();

		}

		table = new JTable(data, columns) {

			// Tabela não editável na coluna das regras;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			};
		};
		table1 = new JTable(data2, columns) {
			// Tabela não editável na coluna das regras;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			};
		};
	}

	//Executado no botao update - cria tabela com as regrs do ficheiro rules.cf 
	public void contentUpdateButton() {
		r = new FileManager(textrules.getText());
		createTable(r.getRules());
		updateGUI();
	}
	
	//Executado no botao result
	public void contentResultButton() {
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
		p = Double.toString(r.getNumberOfFalsePositives());
		numberOfFalsePositives.setText(p);
		n = Double.toString(r.getNumberOfFalseNegatives());
		numberOfFalseNegatives.setText(n);
	}
	
	//Executado no botao result2
	public void contentResultButton2() {
		AntiSpamFilterAutomaticConfiguration a = new AntiSpamFilterAutomaticConfiguration();
		try {
			//Gerar configuração automática
			a.automaticConfiguration(textrules.getText(), textspam.getText(), textham.getText());
			
			//Ler ficheiros gerados pela configuração automática
			r.readRf();
			r.readRs();
			
			//Introduzir configuração dos pesos na janela
			for (int i = 0; i <r.getRules().size(); i++) {
				data2[i][1]=Double.toString(r.getRules().get(i).getWeight());
			}
			
			// Introduzir o número de falsos positivos e falsos negativos na janela
			pa = Double.toString(r.getNumberOfFalsePositives());
			numberOfFalsePositives2.setText(pa);
			
			na = Double.toString(r.getNumberOfFalseNegatives());
			numberOfFalseNegatives2.setText(na);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	public void addButtons() {
		// Ao selecionaro Botao "Save changes" guardamos o resultado dos pesos que demos às regras ao ficheiros rules.cf	
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.writeRulesFile();
				saveBoolean=true;
				reset();
			}
		});
		
		
		saveButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.writeRulesFile();	
				save2Boolean=true;
				reset();
			}
		});
		
		resultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentResultButton();
				resultBoolean=true;
				reset();
			}
		});
		
		resultButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentResultButton2();
				result2Boolean=true;
				reset();
			}
		});

		buttonPanel1.add(resultButton);
		buttonPanel1.add(saveButton);
			
		buttonPanel2.add(resultButton2);
		buttonPanel2.add(saveButton2);
	}
	
	public void addFPanel() {
		fpfnPanel.setLayout(new GridLayout(2,2));
		fpfnPanel2.setLayout(new GridLayout(2,2));
		
		falsePositives.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalsePositives.setEditable(false);
		
		falsePositives2.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalsePositives2.setEditable(false);

		falseNegatives.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalseNegatives.setEditable(false);
		
		falseNegatives2.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalseNegatives2.setEditable(false);
	}
	
	
	public void reset() {
		if(updateBoolean == true && resultBoolean==true && result2Boolean==true && save2Boolean==true && saveBoolean==true) {
			 r.getRules().clear();
			 r.fPositives.clear();
			 r.fNegatives.clear();
			 r.problemList.clear();
			 updateBoolean =false;
			 resultBoolean =false;
			 result2Boolean =false;
			 saveBoolean =false;
			 save2Boolean =false;
			 
			 for(int i =0; i< data.length; i++) {
				 data[i][0]=null;
				 data[i][1]=null;
			 }
				 
			 for(int i =0; i< data2.length; i++) { 
				 data2[i][0]=null;
				 data2[i][1]=null;
			 }
		}
	}

	
}
