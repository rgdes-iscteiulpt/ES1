package JUnitTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import antiSpamFilter.FileManager;
import antiSpamFilter.GUI;
import antiSpamFilter.Rule;


class GuiTest {

	
	FileManager fm= new FileManager("rules.cf");
	
	@Test
	void testInit() throws IOException {	
		ArrayList<Rule>rules = new ArrayList<Rule>();

		Rule r1 = new Rule("BAYES_00");
		rules.add(r1);
		
		Rule r2 = new Rule("FREEMAIL_FROM ");
		rules.add(r2);
		
		Rule r3 = new Rule("RDNS_NONE");
		rules.add(r3);
		
		String[] columns = { "Rules", "Weights" };
		assertEquals("Rules", columns[0]);
		assertEquals("Weights", columns[1]);
		
		String[][] data = new String[rules.size()][rules.size()];
		assertEquals(3, data.length);
		
		String[][] data2 = new String[rules.size()][rules.size()];
		assertEquals(3, data2.length);
		
		for (int i = 0; i < rules.size(); i++) {
			data[i][0] = rules.get(0).getName();
			assertEquals("BAYES_00", rules.get(0).getName());
			data2[i][0] = rules.get(0).getName();
			assertEquals("BAYES_00", rules.get(0).getName());
		}	
		
		GUI g= new GUI();
		g.init();
		g.updateGUI();	
		
		assertEquals("Anti-Spam Configuration For Professional Mail-Box", g.frame.getTitle());
		assertEquals("		Get Rules		", g.update.getText());
		
		assertEquals("	Save changes  ", g.saveButton.getText());
		assertEquals("	Save changes  ", g.saveButton2.getText());
		
		assertEquals("	Result	", g.resultButton.getText());
		assertEquals("	Result	", g.resultButton2.getText());
		
		assertEquals("         Escolher Path:      ", g.l1.getText());
		assertEquals("		rules.cf		", g.l2.getText());
		assertEquals("		spam.log		", g.l3.getText());
		assertEquals("		ham.log			", g.l4.getText());
		
		assertEquals("Configuração Manual", g.m.getText());
		assertEquals("Configuração Automática", g.a.getText());
		
		assertEquals("False Positives: ", g.falsePositives.getText());
		assertEquals("False Positives: ", g.falsePositives2.getText());
		
		assertEquals("False Negatives: ", g.falseNegatives.getText());
		assertEquals("False Negatives: ", g.falseNegatives2.getText());
		
		
		g.contentUpdateButton();
		g.update.doClick();
		
		g.saveButton.doClick();
		g.saveButton2.doClick();
		
		g.resultButton.doClick();
		g.contentResultButton();
		
		g.contentResultButton2();
		g.resultButton2.doClick();
					
	}
}


