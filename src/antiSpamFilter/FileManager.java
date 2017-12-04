package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

	private File fileRules;
	private File fileSpam;
	private File fileHam;
	private ArrayList<Rule> rules;
	private String ruleEmail;
	private double falsePositives;
	private double falseNegatives;
	private ArrayList<Double> fPositives;
	private ArrayList<Double> fNegatives;
	private ArrayList<String> problemList;
	
	public FileManager(String filerulesname) {
		fileRules = new File(filerulesname);
		rules = new ArrayList<Rule>();
		readFileRules(fileRules);
	}

	public void readFileRules(File f) {
		Rule rule;
		try {
			Scanner sc = new Scanner(fileRules);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				
				//se ficheiro tiver pesos das regras de avaliação anterior, apaga los
				char last = line.charAt(line.length()-3);
				char first = line.charAt(0);
				String regra = first+"";
				
					if(last == ':') {
						//já guardei a 1ª letra, começo pela segundo e ignoro as ultimas 4- " : 1" (exemplo se peso da regra for 1)
						for (int i = 1; i < line.length()-4; i++) {
							regra = regra.concat(line.charAt(i)+"");
						}
					}
					else
						regra=line;
					
				rule = new Rule(regra);
				rules.add(rule);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Rule> getRules() {
		return rules;
	}

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
				
				// verificar se as mensagens são spam ou ham
				if(weights <= 5){
					falseNegatives++;
				}
				
				
				System.out.println("w:" + weights);
				weights=0;
				
				
			// confirmar se le as regras
			rulesEmail.add(ruleEmail);
		}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		System.out.println("rulesEmail: " + rulesEmail);
	}
	
	
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
				
				// verificar se as mensagens são spam ou ham
				if(weights > 5){
					falsePositives++;
				}
				
				
				//System.out.println("w:" + weights);
				weights=0;
				
				
			// confirmar se le as regras
			rulesEmail.add(ruleEmail);
		}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		System.out.println("rulesEmail: " + rulesEmail);
	}
	
	
	//Ler ficheiro com o nr de falsos positivos e falsos negativos calculados pelo algoritmo
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
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("fP" + fPositives);
			System.out.println("fn" + fNegatives);
			
		}
		
		
		//Ler ficheiro com o pesos calculados pelo algoritmo com o pesos calculados
		public void readRs() {
			problemList= new ArrayList<>();
			try {
				File rs = new File("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
				Scanner sc = new Scanner(rs);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					problemList.add(line);
				}	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			getConfiguration();
		}
		
		//Obter linha com a melhor configuração de fp
		public int getLine() {
			double valueFp= -1;
			double valueFn= -1;
			int linha =0;
			for(int i=0; i<fPositives.size(); i++) {
				for (int j = 0; j < fNegatives.size(); j++) {
					if(valueFp == -1 && valueFn==-1) {
						valueFp =fPositives.get(0);
						valueFn =fNegatives.get(0);
						falsePositives = (int) valueFp;
					}else if(valueFp>fPositives.get(i)){
						valueFp =fPositives.get(i);
						valueFn =fNegatives.get(i);
						linha = i;
						falsePositives = valueFp;
						falseNegatives = valueFn;
					}
				}
			}
			System.out.println("vfp " + valueFp);
			System.out.println("vfn " + valueFn);
			return linha;		
		}
		
		//Obter a configuração dos pesos correspondentes à linha obtida
		public ArrayList<Double> getConfiguration() {
			ArrayList<Double> weights = new ArrayList<Double>();
			double d=0;
			String s = null;
			int linha = getLine();
			for (int i = 0; i < problemList.size(); i++) {
				if(i== linha) {
					s = problemList.get(i);
					System.out.println("s" + s);
					String[] division = s.split(" ");
					for (int j = 0; j < division.length; j++) {
						//System.out.println(division[j].toString());
						d= Double.parseDouble(division[j]);
						//System.out.println("d" + d);
						weights.add(d);
						//System.out.println("w" + weights);
						rules.get(j).setWeight(d);
					}
				}
			}
		
			return weights;
		}
		
	public double getNumberOfFalsePositives(){
		return falsePositives;
	}
	
	public double getNumberOfFalseNegatives(){
		return falseNegatives;
	}
	
	//Guardamos o resultado dos pesos que demos às regras ao ficheiros rules.cf	
	public void writeRulesFile(){
		if(fileRules.exists()) {
			try {
				fileRules.createNewFile();
				PrintWriter pw = new PrintWriter(fileRules);
				for (int i = 0; i < rules.size(); i++) {
					pw.write(rules.get(i).getName() + " : " + rules.get(i).getWeight() + "\n");
				}
				pw.flush();
				pw.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
}