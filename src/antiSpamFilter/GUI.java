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

/**
 * Classe da Interface Gr�fica do Utilizador: Cria Frame e objectos associados �
 * mesma Introduz valores nas tabelas Rgras/Pesos
 */
/**
 * @author hogum
 *
 */
public class GUI {

	public JFrame frame = new JFrame("Anti-Spam Configuration For Professional Mail-Box");
	private JTable table;
	private JTable table1;
	private JPanel scrollPanel = new JPanel();
	private JPanel scrollPanel2 = new JPanel();
	private JPanel b1 = new JPanel();
	private JPanel b2 = new JPanel();;
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
	public JLabel l2 = new JLabel("		rules.cf		"); // rules.cf
	public JLabel l3 = new JLabel("		spam.log		"); // spam.log.txt
	public JLabel l4 = new JLabel("		ham.log			"); // ham.log.txt
	private JPanel manual = new JPanel();
	private JPanel auto = new JPanel();
	public JLabel m = new JLabel("Configura��o Manual");
	public JLabel a = new JLabel("Configura��o Autom�tica");
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
	private boolean updateBoolean = false;
	private boolean resultBoolean = false;
	private boolean result2Boolean = false;
	private boolean saveBoolean = false;
	private boolean save2Boolean = false;

	private FileManager r;
	private String[][] data;
	private String[][] data2;

	/**
	 * Default Constructor GUI
	 */

	public GUI() {
		init();
	}

	/**
	 * M�todo que cria frame inicial, onde se escolhem os ficheiros (Rules, Ham,
	 * Spam)
	 */

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
				updateBoolean = true;
			}
		});

		path.add(update, BorderLayout.SOUTH); // s� depois de darmos path para rules � que podes ler as rules e fazer o
												// resto da gui
		path.add(esquerda, BorderLayout.WEST);
		path.add(direita, BorderLayout.EAST);
		frame.add(path, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * M�todo que desenha a frame principal, posteriormente a escolher a path, que
	 * ir� conter: as tabelas Regras/Pesos para cada tipo de configura��o
	 * (Autom�tica/Manual), o n�mero de falsos positivos e falsos negativos e bot�es
	 * que permitem guardar altera��es ou executar testes;
	 */
	public void updateGUI() {
		// Um painel para cada tipo de configura��o
		manual.setLayout(new BorderLayout());
		auto.setLayout(new BorderLayout());

		manual.add(m, BorderLayout.NORTH);
		auto.add(a, BorderLayout.NORTH);

		// Acrescentar o scroll � tabela
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

		// Acrescentar ao painel de bot�es os bot�es;
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

		// Adicionais todos os botoes ao painel dos botoes
		b1.add(buttonPanel1, BorderLayout.NORTH);
		b1.add(fpfnPanel, BorderLayout.SOUTH);

		b2.add(buttonPanel2, BorderLayout.NORTH);
		b2.add(fpfnPanel2, BorderLayout.SOUTH);

		manual.add(b1, BorderLayout.SOUTH);
		auto.add(b2, BorderLayout.SOUTH);

		// Acrescentar os paineis � frame;
		frame.add(manual, BorderLayout.WEST);
		frame.add(auto, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * M�todo que cria as tabela Regras/Pesos para cada tipo de
	 * configura��o(Manual/Autom�tica); Introduz as regras que constam no ArrayList
	 * rules nas tabelas;
	 * 
	 * @param rules
	 *            corresponde ao Arraylist de Regras
	 * 
	 */

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

			// Tabela n�o edit�vel na coluna das regras;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			};
		};
		table1 = new JTable(data2, columns) {
			// Tabela n�o edit�vel na coluna das regras;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			};
		};
	}

	/**
	 * M�todo invocado no m�todo Init() quando pressionado o bot�o update ("Get
	 * Rules"); Cria um FileManager que l� o ficheiro de regras correspondente �
	 * path introduzida; Invoca o m�todo createTable() e posteriormente o
	 * updateGUI();
	 * 
	 */

	public void contentUpdateButton() {
		r = new FileManager(textrules.getText());
		createTable(r.getRules());
		updateGUI();
	}

	/**
	 * M�todo executado quando pressionado o bot�o resultButton correspondente �
	 * configura��o Manual; Consiste em inserir os pesos correspondentes �s regras
	 * da tabela, ao ArrayList rules (rules.getRule().setWeight()); L� os ficheiros
	 * Ham e spam e introduz o n�mero de Falsos positivos e negativos;
	 */

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

		// Ler ficheiros Spam.log e Ham.log
		r.readFileSpam(textspam.getText());
		r.readFileHam(textham.getText());

		// Introduzir o n�mero de falsos positivos e falsos negativos na janela
		p = Double.toString(r.getNumberOfFalsePositives());
		numberOfFalsePositives.setText(p);
		n = Double.toString(r.getNumberOfFalseNegatives());
		numberOfFalseNegatives.setText(n);
	}

	/**
	 * M�todo executado quando pressionado o bot�o resultButton correspondente �
	 * configura��o Autom�tica; Consiste em gerar autom�ticamente os pesos
	 * respectivos a cada regra, atrav�s de um algoritmo, e adiciona-os na tabela;
	 * L� os ficheiros que cont�m os pesos e o n�mero de falsos positivos,
	 * calculados pelo algoritmo e introduz o esses valores na tabela;
	 */

	public void contentResultButton2() {
		AntiSpamFilterAutomaticConfiguration a = new AntiSpamFilterAutomaticConfiguration();
		try {
			// Gerar configura��o autom�tica
			a.automaticConfiguration(textrules.getText(), textspam.getText(), textham.getText());

			// Ler ficheiros gerados pela configura��o autom�tica
			r.readRf();
			r.readRs();

			// Introduzir configura��o dos pesos na janela
			for (int i = 0; i < r.getRules().size(); i++) {
				data2[i][1] = Double.toString(r.getRules().get(i).getWeight());
			}

			// Introduzir o n�mero de falsos positivos e falsos negativos na janela
			pa = Double.toString(r.getNumberOfFalsePositives());
			numberOfFalsePositives2.setText(pa);

			na = Double.toString(r.getNumberOfFalseNegatives());
			numberOfFalseNegatives2.setText(na);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * M�todo que adiciona ActionListeners aos bot�es saveButton e resultButton para
	 * cada tipo de configura��o (Autom�tica/Manual);
	 */
	public void addButtons() {
		// Ao selecionaro Botao "Save changes" guardamos o resultado dos pesos que demos
		// �s regras ao ficheiros rules.cf
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.writeRulesFile();
				saveBoolean = true;
				reset();
			}
		});

		saveButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.writeRulesFile();
				save2Boolean = true;
				reset();
			}
		});

		resultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentResultButton();
				resultBoolean = true;
				reset();
			}
		});

		resultButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentResultButton2();
				result2Boolean = true;
				reset();
			}
		});

		buttonPanel1.add(resultButton);
		buttonPanel1.add(saveButton);

		buttonPanel2.add(resultButton2);
		buttonPanel2.add(saveButton2);
	}

	/**
	 * M�todo que adiciona os Paineis de Falsos positivos e negativos,
	 * correspondentes a cada tipo de configura��o (Autom�tica/Manual), � frame;
	 */
	public void addFPanel() {
		fpfnPanel.setLayout(new GridLayout(2, 2));
		fpfnPanel2.setLayout(new GridLayout(2, 2));

		falsePositives.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalsePositives.setEditable(false);

		falsePositives2.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalsePositives2.setEditable(false);

		falseNegatives.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalseNegatives.setEditable(false);

		falseNegatives2.setHorizontalAlignment(JLabel.CENTER);
		numberOfFalseNegatives2.setEditable(false);
	}

	/**
	 * M�todo que verifica se todos os bot�es foram accionados, e em caso
	 * afirmativo, elemina todos os valores contidos nos Arraylists e contidos na
	 * tabela; Limpa os valores do sistema ap�s utiliza��o total;
	 */
	public void reset() {
		if (updateBoolean == true && resultBoolean == true && result2Boolean == true && save2Boolean == true
				&& saveBoolean == true) {
			r.getRules().clear();
			r.fPositives.clear();
			r.fNegatives.clear();
			r.problemList.clear();
			updateBoolean = false;
			resultBoolean = false;
			result2Boolean = false;
			saveBoolean = false;
			save2Boolean = false;

			for (int i = 0; i < data.length; i++) {
				data[i][0] = null;
				data[i][1] = null;
			}

			for (int i = 0; i < data2.length; i++) {
				data2[i][0] = null;
				data2[i][1] = null;
			}
		}
	}

}
