package antiSpamFilter;

/**
 * Classe que cria as Rules
 */

public class Rule {

	private String name;
	private double weight;

	/**
	 * Default constructor. Tem associoado o nome da Rule e é definido o peso a 0 por defeito 
	 * @param name corresponde ao nome de Rule
	 */
	public Rule(String name) {
		this.name = name;
		weight = 0;
	}

	/**
	 * Obtém o nome da Rule
	 * @return nome da Rule
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Define o nome da Rule
	 * @param name da Rule
	 */

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtém o peso da Rule
	 * @return peso da Rule
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Define o peso da Rule entre -5 e 5
	 * @param weight é o peso da Rule
	 */
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
