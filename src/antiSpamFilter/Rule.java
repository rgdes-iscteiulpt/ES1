package antiSpamFilter;

public class Rule {

	private String name;
	private int weight;

	public Rule(String name) {
		this.name = name;
		// Cada regra inicia com peso a 0;
		weight = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		if (weight > 5) {
			this.weight = 5;
		}
		if (weight < -5) {
			this.weight = -5;
		}
		this.weight = weight;
	}

}
