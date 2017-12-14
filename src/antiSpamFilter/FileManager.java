package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * Classe que trata da leitura e escrita de ficheiros
 *
 */
public class FileManager {

	private File fileRules;
	private File fileSpam;
	private File fileHam;
	private ArrayList<Rule> rules;
	private String ruleEmail;
	private double falsePositives;
	private double falseNegatives;
	public ArrayList<Double> fPositives;
	public ArrayList<Double> fNegatives;
	public ArrayList<String> problemList;

	/**
	 * Default constructor
	 * @param filerulesname
	 */
	public FileManager(String filerulesname) {
		fileRules = new File(filerulesname);
		rules = new ArrayList<Rule>();
		readFileRules(fileRules);
	}

	/**
	 * M�todo que l� o ficheiro f
	 * @param f � o ficheiro a ser lido 
	 */

	public void readFileRules(File f) {
		Rule rule;
		try {
			Scanner sc = new Scanner(fileRules);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				String[]partes = line.split(":");
				rule = new Rule(partes[0]);
				rules.add(rule);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Obt�m a lista de Rules
	 * @return ArrayList de rules
	 */

	public ArrayList<Rule> getRules() {
		return rules;
	}

	/**
	 * M�todo que l� o ficheiro dado como par�metro
	 * Os pesos das regras do ficheiro s�o somados e � verificado se as mensagens s�o falsos negativos
	 * @param filename � o ficheiro a ser lido
	 */

	public void readFileSpam(String filename) {
		fileSpam = new File(filename);
		ArrayList<String> rulesEmail = new ArrayList<>();
		int weights =0;
		falseNegatives = 0;
		try {
			Scanner sc = new Scanner(fileSpam);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] division = line.split("	");

				String emailId = division[1];

				//comparar rule do email com o array das rules e somar os pesos
				for (int i = 1; i < division.length; i++) {
					for(int j=0; j<rules.size(); j++) {
						ruleEmail = division[i];
						if(ruleEmail.equals(rules.get(j).getName())){
							weights +=  rules.get(j).getWeight();
						}
					}		
				}

				if(weights <= 5){
					falseNegatives++;
				}				
				weights=0;

				// confirmar se le as regras
				rulesEmail.add(ruleEmail);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * M�todo que l� o ficheiro dado como par�metro.
	 * Os pesos das regras do ficheiro s�o somados e � verificado se as mensagens s�o falsos positivos
	 * @param filename � o ficheiro a ser lido
	 */

	public void readFileHam(String filename) {
		fileHam = new File(filename);
		ArrayList<String> rulesEmail = new ArrayList<>();
		int weights =0;
		falsePositives = 0;
		try {
			Scanner sc = new Scanner(fileHam);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] division = line.split("	");

				String emailId = division[1];

				//comparar rule do email com o array das rules e somar os pesos
				for (int i = 1; i < division.length; i++) {
					for(int j=0; j<rules.size(); j++) {
						ruleEmail = division[i];
						if(ruleEmail.equals(rules.get(j).getName())){
							weights +=  rules.get(j).getWeight();
						}
					}		
				}

				if(weights > 5){
					falsePositives++;
				}

				weights=0;

				// confirmar se le as regras
				rulesEmail.add(ruleEmail);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * L� o ficheiro que cont�m o n�mero de falsos positivos e falsos negativos calculados pelo algoritmo
	 */
	public void readRf() {
		fPositives =  new ArrayList<>();
		fNegatives =  new ArrayList<>();
		try {
			File rf = new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rf");
			Scanner sc = new Scanner(rf);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] division = line.split(" ");	
				String falsePositive= division[0];
				String falseNegative = division[1];
				Double fp = Double.parseDouble(falsePositive);
				fPositives.add(fp);
				Double fn = Double.parseDouble(falseNegative);
				fNegatives.add(fn);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * L� ficheiro com os pesos calculados pelo algoritmo, adicionando todas as linhas do ficheiro a uma ArrayList
	 */

	public void readRs() {
		problemList= new ArrayList<>();
		try {
			File rs = new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
			Scanner sc = new Scanner(rs);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				problemList.add(line);
			}	
			sc.close();
		} catch (FileNotFoundException e) {}
		getConfiguration();
	}

	/**
	 * Obt�m a linha do ficheiro com melhor configura��o de falsos positivos
	 * @return linha de inteiros
	 */

	public int getLine() {
		double valueFp= -1;
		double valueFn= -1;
		int linha =0;
		for(int i=0; i<fPositives.size(); i++) {
			for (int j = 0; j < fNegatives.size(); j++) {
				if(valueFp == -1 && valueFn==-1) {
					valueFp =fPositives.get(0);
					valueFn =fNegatives.get(0);
					falsePositives =  valueFp;
					falseNegatives = valueFn;
				}else if(valueFp>fPositives.get(i)){
					valueFp =fPositives.get(i);
					valueFn =fNegatives.get(i);
					linha = i;
					falsePositives = valueFp;
					falseNegatives = valueFn;
				}
			}
		}
		return linha;		
	}

	/**
	 * Obt�m a configura��o dos pesos correspondentes � linha obtida
	 * @return ArrayList de pesos
	 */

	public ArrayList<Double> getConfiguration() {
		ArrayList<Double> weights = new ArrayList<Double>();
		double d=0;
		String s = null;
		int linha = getLine();
		for (int i = 0; i < problemList.size(); i++) {
			if(i== linha) {
				s = problemList.get(i);
				String[] division = s.split(" ");
				for (int j = 0; j < division.length; j++) {
					d= Double.parseDouble(division[j]);
					weights.add(d);
					rules.get(j).setWeight(d);
				}
			}
		}

		return weights;
	}

	/**
	 * Obt�m o n�mero de falsos positivos
	 * @return falsos positivos
	 */

	public double getNumberOfFalsePositives(){
		return falsePositives;
	}

	/**
	 * Obt�m o n�mero de falsos negativos
	 * @return falsos negativos
	 */

	public double getNumberOfFalseNegatives(){
		return falseNegatives;
	}

	/** Escreve um novo ficheiro de regras com os respetivos pesos atribu�dos
	 */

	public void writeRulesFile(){
		if(fileRules.exists()) {
			try {
				fileRules.createNewFile();
				PrintWriter pw = new PrintWriter(fileRules);
				for (int i = 0; i < rules.size(); i++) {
					pw.write(rules.get(i).getName() + ":" + rules.get(i).getWeight() + "\n");
				}
				pw.flush();
				pw.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
}