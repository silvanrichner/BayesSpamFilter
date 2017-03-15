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

	public Training(String path) {
		this.path = path;
		System.out.println(path);
		countHam = new HashMap<String, Integer>();
		countSpam = new HashMap<String, Integer>();
		
		run();
	}
	
	private void run(){
		File folder = new File(path + "ham-train");
		File[] listOfFiles = folder.listFiles();
		Arrays.stream(listOfFiles).forEach((f) -> countHamWords(Reader.read(f.toString())));
		
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
	}
}
