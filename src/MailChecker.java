import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MailChecker {
  private Map<String, Double> probSpam;
  private Map<String, Double> probHam;
  private double defaultValue;


  public MailChecker(Map<String, Double> probSpam, Map<String, Double> probHam, double defaultValue) {
    this.probHam = probHam;
    this.probSpam = probSpam;
    this.defaultValue = defaultValue;
  }

  public double check(String mail) {
    //mail=mail.toLowerCase();

    String[] words = mail.split(" ");
    Set<String> wordSet = new HashSet<String>();
    Collections.addAll(wordSet, words);
    List<Double> wordValues = new ArrayList<Double>();

    for (String s : wordSet) {
      double spam;
      if (probSpam.containsKey(s)) {
        spam = probSpam.get(s);
      } else {
        spam = defaultValue;
      }
      double ham;
      if (probHam.containsKey(s)) {
        ham = probHam.get(s);
      } else {
        ham = defaultValue;
      }
      if(probHam.containsKey(s)||probSpam.containsKey(s))
      wordValues.add(spam / (spam + ham));

    }


		/*
        FAILED DUE TO FLOATING POINT UNDERFLOW
        double product = 1;
		for(double wv : wordValues){
			product *= wv;
		}

		double oneMinusProduct = 1;
		for(double wv : wordValues){
			oneMinusProduct*=(1 - wv);
		}
		*/

    double n = 0;
    for (double wv : wordValues) {
      n += (Math.log(1 - wv) - Math.log(wv));
    }

    double p = 1 / (1 + Math.pow(Math.E, n));
    return p;
  }

}
