package JUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;


import antiSpamFilter.FileManager;
import antiSpamFilter.Rule;

class FileManagerTest {

	int linha;
	String certo = "rules.cf";
	FileManager fmcerto = new FileManager(certo);
	
	
	@Test
	public void TestFileRules() {
		String certo = "rules.cf";
		ArrayList<Rule> rules = new ArrayList<Rule>();
		FileManager fmcerto = new FileManager(certo);
		fmcerto.readFileRules(new File (certo));
		rules.addAll(fmcerto.getRules());
		
		
		String errado = "rules";
		FileManager fmerrado = new FileManager(errado);
		fmerrado.readFileRules(new File (errado));
	}
	
	@Test
	void testeFileHam() {
		ArrayList<String> rulesEmail = new ArrayList<String>();
		FileManager fm= new FileManager("rules.cf");
		fm.readFileHam("ham.log.txt");
		fm.readFileHam("abc");
		fm.getNumberOfFalsePositives();
	}
	

	@Test
	public void TestFileSpam() {
		String certo = "rules.cf";
		FileManager fm = new FileManager(certo); 
		
		String spamcerto = "spam.log.txt";
		fm.readFileSpam(spamcerto);
		
		String spamerrado = "spam.log";
		fm.readFileSpam(spamerrado);
		assertNotEquals(spamerrado,spamcerto);
		
		fm.getNumberOfFalsePositives();
		fm.getNumberOfFalseNegatives();
	}
	

	@Test	
	void testListaRule() {
		ArrayList<Rule> rules = new ArrayList<Rule>();
		FileManager fm= new FileManager("rules.cf");
		fm.getRules();
		rules.addAll(fm.getRules());
		assertEquals(335, rules.size());
	}
	
	@Test	
	void testReadConfigurationFiles() {
		String certo = "rules.cf";
		FileManager fm = new FileManager(certo);
		fm.readRf();
		fm.readRs();
		
	}
	
	@Test
	int testgetLine() {
		double valueFp= -1.0;
		double valueFn= -1.0;
		linha =0;
		
		ArrayList<Double> fP = new ArrayList<Double>();
		fP.add(4.0);
		fP.add(5.0);
		fP.add(0.0);
		
		ArrayList<Double> fN = new ArrayList<Double>();
		fN.add(10.0);
		fN.add(15.0);
		fN.add(20.0);
		
		for(int i=0; i< fP.size(); i++) {
			assertEquals(3, fP.size());
			for (int j = 0; j < fN.size(); j++) {
				if(valueFp == -1.0 && valueFn==-1.0) {
					assertEquals(-1.0, valueFp);
					assertEquals(-1.0, valueFn);
					
					valueFp =fP.get(0);
					assertEquals(4.0, valueFp);

					valueFn =fN.get(0);
					assertEquals(10.0, valueFn);
					
				}else if(valueFp>fP.get(i)){
					valueFp =fP.get(i);
					valueFn =fN.get(i);
					linha = i;
					
					assertEquals(0.0, valueFp);
					assertEquals(20.0, valueFn);
					assertEquals(2, linha);
					
				}
			}
		}
		return linha;
	}
	
	@Test
	void testgetConfiguration() {
		
		ArrayList<Double> weights = new ArrayList<Double>();	
		ArrayList<String> problemList = new ArrayList<String>();
		problemList.add("-3.9549697981309686");
		problemList.add("0.15078245642155075");
		problemList.add("0.48988243891226624");
		 
		ArrayList<Rule> rules = new ArrayList<Rule>();
		
		Rule r1 = new Rule("BAYES_00");
		rules.add(r1);
		
		Rule r2 = new Rule("FREEMAIL_FROM ");
		rules.add(r2);
		
		Rule r3 = new Rule("RDNS_NONE");
		rules.add(r3);
		
		double d=0;
		String s = null;
		
		int a = testgetLine();
		for (int i = 0; i < problemList.size(); i++) {
			if(i== a) {
				assertEquals(2, a);
				
				s = problemList.get(i);
				String[] division = s.split(" ");
				for (int j = 0; j < division.length; j++) {
					d= Double.parseDouble(division[j]);
					weights.add(d);
					rules.get(j).setWeight(d);
				}
			}
		}
	}	
	
		
	@Test
	void testwriteRulesFile() {
		String certo = "rules.cf";
		FileManager fm = new FileManager(certo);
		fm.writeRulesFile();
		
	}

}
