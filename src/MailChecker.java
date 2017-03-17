import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MailChecker {
//	private Map<String, Double> probSpam;
//	private Map<String, Double> probHam;
	
	private Map<String, Double> spamliness;
//	private double defaultValue;

	public MailChecker(Map<String, Double> spamliness) {
//		this.probHam = probHam;
//		this.probSpam = probSpam;
		this.spamliness = spamliness;
//		this.defaultValue = defaultValue;
	}

	public double check(String mail) {
		// mail=mail.toLowerCase();

		String[] words = mail.split(" ");
		Set<String> wordSet = new HashSet<String>();
		Collections.addAll(wordSet, words);
		List<Double> wordValues = new ArrayList<Double>();

		for (String s : wordSet) {
//			//ignore the word if it doesn't appear in the training data
//			if (probHam.containsKey(s) || probSpam.containsKey(s)) {
//				//probablity of the word to be in a spam mail
//				double spam;
//				if (probSpam.containsKey(s)) {
//					spam = probSpam.get(s);
//				} else {
//					spam = defaultValue;
//				}
//				//probability of the word to be in a ham mail
//				double ham;
//				if (probHam.containsKey(s)) {
//					ham = probHam.get(s);
//				} else {
//					ham = defaultValue;
//				}
				
				//the overall "spamliness" of the word (non biased): Pr(S|W) = Pr(W|S) / ( Pr(W|S) + (Pr(W|H) )
//				wordValues.add(spam / (spam + ham));
			if(spamliness.containsKey(s)){
				wordValues.add(spamliness.get(s));
			}
//			}
		}

		/*
		 * FAILED DUE TO FLOATING POINT UNDERFLOW double product = 1; for(double
		 * wv : wordValues){ product *= wv; }
		 * 
		 * double oneMinusProduct = 1; for(double wv : wordValues){
		 * oneMinusProduct*=(1 - wv); }
		 */
		
		//combining all the spamliness values of the words in the mail ( n = sum(ln(1-wv) - ln(wv) )
		double n = 0;
		for (double wv : wordValues) {
			n += (Math.log(1 - wv) - Math.log(wv));
		}
		
		//calculate the final spamliness of the mail ( p = 1 / (1 + e^n) ) 
		double p = 1 / (1 + Math.pow(Math.E, n));
		return p;
	}

}
