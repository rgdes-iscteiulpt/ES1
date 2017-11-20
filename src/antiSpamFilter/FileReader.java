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
	
	public FileReader() {
		fileRules = new File("rules.cf");
		rules = new ArrayList<Rule>();
		readFileRules(fileRules);
		
		fileSpam = new File("spam.log.txt");
		readFileSpamHam(fileSpam);
		
		fileHam = new File("ham.log.txt");
		readFileSpamHam(fileHam);
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
	
	
	public void readFileSpamHam(File filename) {
		ArrayList<String> rulesEmail = new ArrayList<>();
		
		try {
			Scanner sc = new Scanner(filename);
			
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] division = line.split("	");
				String emailId = division[1];
			
				for(int i = 1; i < division.length; i++ ) {
					ruleEmail = division[i];
					
					//confirmar se le as regras
					rulesEmail.add(ruleEmail);
				}
				
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("rulesEmail: " + rulesEmail);
	}
	
	
	
}
