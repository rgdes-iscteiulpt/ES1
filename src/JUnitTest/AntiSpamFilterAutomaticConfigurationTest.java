package JUnitTest;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;

class AntiSpamFilterAutomaticConfigurationTest {

	@Test
	void test() throws IOException {
		AntiSpamFilterAutomaticConfiguration a = new AntiSpamFilterAutomaticConfiguration();
		a.automaticConfiguration("rules.cf", "spam.log.txt", "ham.log.txt");
	}

}
