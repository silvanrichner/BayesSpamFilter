import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Training {
	
	String path;
	
	Map<String, Integer> countHam;
	Map<String, Integer> countSpam;
	Map<String, Double> probHam;
	Map<String, Double> probSpam;
	int hamMailCount = 0;
	int spamMailCount = 0;

	public Training(String path) {
		this.path = path;
		countHam = new HashMap<String, Integer>();
		countSpam = new HashMap<String, Integer>();
		probHam = new HashMap<String, Double>();
		probSpam = new HashMap<String, Double>();
		
		run();
	}
	
	private void run(){
		System.out.println("Starting to read training data...");
		
		File folder = new File(path + "ham-train");
		File[] listOfFiles = folder.listFiles();
		Arrays.stream(listOfFiles).forEach((f) -> countHamWords(Reader.read(f.toString())));
		for (Map.Entry<String, Integer> entry : countHam.entrySet()) {
			probHam.put(entry.getKey(),  ((double) entry.getValue()) / ((double)hamMailCount));
		}
		System.out.println("Ham training data read");
		
		
		folder = new File(path + "spam-train");
		listOfFiles = folder.listFiles();
		Arrays.stream(listOfFiles).forEach((f) -> countSpamWords(Reader.read(f.toString())));
		for (Map.Entry<String, Integer> entry : countSpam.entrySet()) {
			probSpam.put(entry.getKey(),  ((double) entry.getValue()) / ((double)spamMailCount));
		}
		System.out.println("Spam training data read");
		
//		for (Map.Entry<String, Integer> entry : countHam.entrySet()) {
//			if(entry.getValue() > 20){
//				System.out.println(entry.getKey() + ": " + entry.getValue());
//			}
//		}
	}

	private void countHamWords(String data) {
		String[] words = data.split(" ");
		Set<String> wordSet = new HashSet<String>();
		Collections.addAll(wordSet, words);
		
		for(String s : wordSet){
			if(countHam.containsKey(s)){
				countHam.put(s, countHam.get(s).intValue()+1);
			}else{
				countHam.put(s, 1);
			}
			
		}
		
		hamMailCount++;
	}
	
	private void countSpamWords(String data) {
		String[] words = data.split(" ");
		Set<String> wordSet = new HashSet<String>();
		Collections.addAll(wordSet, words);
		
		for(String s : wordSet){
			if(countSpam.containsKey(s)){
				countSpam.put(s, countSpam.get(s).intValue()+1);
			}else{
				countSpam.put(s, 1);
			}
			
		}
		
		spamMailCount++;
	}
	
	public Map<String, Double> getProbHam() {
		return probHam;
	}

	public Map<String, Double> getProbSpam() {
		return probSpam;
	}
}
