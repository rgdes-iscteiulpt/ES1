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
	private int falsePositives=0;
	private int falseNegatives=0;

	public FileManager() {
		fileRules = new File("rules.cf");
		rules = new ArrayList<Rule>();
		readFileRules(fileRules);
	}

	public void readFileRules(File f) {
		Rule rule;
		try {
			Scanner sc = new Scanner(fileRules);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				
				//se ficheiro tiver pesos das regras de avalia��o anterior, apaga los
				char first = line.charAt(0);
				String regra = first+"";
				//j� guardei a 1� letra, come�o pela segundo e ignoro as ultimas 4- " : 1" (exemplo se peso da regra for 1)
				for (int i = 1; i < line.length()-4; i++) {
					regra = regra.concat(line.charAt(i)+"");
				}

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

	public void readFileSpamHam(File file) {
		ArrayList<String> rulesEmail = new ArrayList<>();
		int weights =0;
//		falsePositives = 0;
//		falseNegatives = 0;
		try {
			Scanner sc = new Scanner(file);
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
							
							if(file.getName().equals("spam.log.txt") && weights > 5){
								falsePositives++;
//								System.out.println(falsePositives + "POS");
							}
							else if(file.getName().equals("ham.log.txt") && weights <= 5){
								falseNegatives++;
							}
						}
					}		
//					System.out.println("w:" + weights);
				}
				weights=0;
				
				
			// confirmar se le as regras
			rulesEmail.add(ruleEmail);
		}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		System.out.println("rulesEmail: " + rulesEmail);
	}
	

	public int getNumberOfFalsePositives(){
		return falsePositives;
	}
	
	public int getNumberOfFalseNegatives(){
		return falseNegatives;
	}
	
	//Guardamos o resultado dos pesos que demos �s regras ao ficheiros rules.cf	
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