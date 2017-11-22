package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

	private File fileRules;
	private File fileSpam;
	private File fileHam;
	private ArrayList<Rule> rules;
	private String ruleEmail;
	private int falsePositives;
	private int falseNegatives;

	public FileReader() {
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
				rule = new Rule(line);
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
		falsePositives=0;
		falseNegatives=0;
		
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
						}
					}		
				}
				
				
				// verificar se as mensagens são spam ou ham
				if(weights > 5 && file.getName().equals("spam.log.txt")){
					falsePositives++;
				}
				else if(weights <= 5 && file.getName().equals("ham.log.txt")){
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
		System.out.println("rulesEmail: " + rulesEmail);
	}
	

	public int getNumberOfFalsePositives(){
		return falsePositives;
	}
	
	public int getNumberOfFalseNegatives(){
		return falseNegatives;
	}
	

}
