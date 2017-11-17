package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

	private File fileRules;
	private ArrayList<Rule> rules;

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
}
