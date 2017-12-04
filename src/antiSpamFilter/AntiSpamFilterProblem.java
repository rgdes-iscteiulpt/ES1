package antiSpamFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {
	
	private String rulescf;
	private String spam;
	private String ham;
	
	
	  public AntiSpamFilterProblem(String rulescf, String spam, String ham) {
		  this(335);
		  this.rulescf=rulescf;
		  this.spam=spam;
		  this.ham=ham;
	    // 10 variables (anti-spam filter rules) by default 
	  
	  }

	  public AntiSpamFilterProblem(Integer numberOfVariables) {
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(2);
	    setName("AntiSpamFilterProblem");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-5.0);
	      upperLimit.add(5.0);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	  }

	  public void evaluate(DoubleSolution solution){
	    double aux, xi, xj;
	    double[] fx = new double[getNumberOfObjectives()];
	    double[] x = new double[getNumberOfVariables()];
	    FileManager fm = new FileManager(rulescf);
	    	
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	      fm.getRules().get(i).setWeight(x[i]);
	    }

	   fm.readFileHam(ham);
	   fm.readFileSpam(spam);
	   
	   
	    solution.setObjective(0, fm.getNumberOfFalsePositives());
	    solution.setObjective(1, fm.getNumberOfFalseNegatives());
	  }
	}
