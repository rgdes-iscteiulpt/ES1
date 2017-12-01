package antiSpamFilter;

public class Rule {

	private String name;
	private double weight;

	public Rule(String name) {
		this.name = name;
		weight = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		if (weight > 5) {
			this.weight = 5;
		}
		if (weight < -5) {
			this.weight = -5;
		} else {
			this.weight = weight;
		}
	}

}
