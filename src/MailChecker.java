import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MailChecker {
	private Map<String, Double> probSpam;
	private Map<String, Double> probHam;
	private final double defaultValue = 0.0001;
	

	public MailChecker(Map<String, Double> probSpam, Map<String, Double> probHam) {
		this.probHam = probHam;
		this.probSpam = probSpam;
	}
	
	public double check(String mail){
		String[] words = mail.split(" ");
		Set<String> wordSet = new HashSet<String>();
		Collections.addAll(wordSet, words);
		List<Double> wordValues = new ArrayList<Double>();
		
		for(String s : wordSet){
			double spam;
			if(probSpam.containsKey(s)){
				spam = probSpam.get(s);
			}else{
				spam = defaultValue;
			}
			
			double ham;
			if(probHam.containsKey(s)){
				ham = probHam.get(s);
			}else{
				ham = defaultValue;
			}
			
			wordValues.add(spam / (spam + ham));
		}
		
		double product = 1;
		for(double wv : wordValues){
			product *= wv;
		}
		
		double oneMinusProduct = 1;
		for(double wv : wordValues){
			product *= (1 - wv);
		}
		
		return (product / (product + oneMinusProduct));
	}

}
