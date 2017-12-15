package JUnitTest;

import org.junit.jupiter.api.Test;
import antiSpamFilter.Rule;

class RuleTest {

	@Test
	void test() {
		Rule r = new Rule("r1");		
		r.getName();
		r.getWeight();
		r.setName("");
		r.setName("r2");
		r.setWeight(-5);
		r.setWeight(5);
		r.setWeight(-10);
		r.setWeight(10.0);
	}

	
}
