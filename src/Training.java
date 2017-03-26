import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Training {

  private static double ALPHA = 0.005;
  private static double ONLY_ADD_IF_PROBABILITY_IS_HIGER_THAN=0.001;
  String path;
  Map<String, Integer> countHam;
  Map<String, Integer> countSpam;
  Map<String, Double> probHam;
  Map<String, Double> probSpam;
  int hamMailCount = 0;
  int spamMailCount = 0;

  public Training(String path) {
    this.path = path;
    countHam = new TreeMap<>();
    countSpam = new TreeMap<>();
    probHam = new TreeMap<>();
    probSpam = new TreeMap<>();
    run();

  }

  private void run() {
    System.out.println("Starting to read training data...");

    //Read HamTrain Data
    File folder = new File(path + "ham-train");
    File[] listOfFiles = folder.listFiles();

    //counts word occurences
    Arrays.stream(listOfFiles).forEach((f) -> countHamWords(Reader.read(f.toString())));

    //computes probability for a word to be a HamWord
    for (Map.Entry<String, Integer> entry : countHam.entrySet()) {
      double probability = ((double) entry.getValue()) / ((double) hamMailCount);
      if (probability > ONLY_ADD_IF_PROBABILITY_IS_HIGER_THAN) {
        probHam.put(entry.getKey(), probability);
      }
    }


    System.out.println("Ham training data read... has now "+ probHam.size()+" entries");

    //Read HamTrain Data
    folder = new File(path + "spam-train");
    listOfFiles = folder.listFiles();

    //counts word occurences
    Arrays.stream(listOfFiles).forEach((f) -> countSpamWords(Reader.read(f.toString())));

    //computes probability for a word to be a HamWord
    for (Map.Entry<String, Integer> entry : countSpam.entrySet()) {
      double probability = ((double) entry.getValue()) / ((double) spamMailCount);
      if (probability > ONLY_ADD_IF_PROBABILITY_IS_HIGER_THAN) {
        //  System.out.println(String.format("%.3f", probability) + " " + entry.getKey());
        probSpam.put(entry.getKey(), probability);
      }
    }

    System.out.println("Spam training data read... has now "+ probSpam.size()+" entries");

    //Combine possibilities with Value
    for (String key : probHam.keySet()) {
      if (!probSpam.containsKey(key)) {
        probSpam.put(key, ALPHA);
      }
    }
    for (String key : probSpam.keySet()) {
      if (!probHam.containsKey(key)) {
        probHam.put(key, ALPHA);
      }
      // System.out.println(entry.getKey()+" "+ entry.getValue()+ "----- "+probHam.get(entry.getKey()));
    }
      System.out.println("training data processesed successfully");
  }

  private void countHamWords(String data) {

    while (data.contains("  ")) {
      data = data.replace("  ", " ");
    }
    String[] words = data.split(" ");
    Set<String> wordSet = new HashSet<String>();
    Collections.addAll(wordSet, words);

    for (String s : wordSet) {
      if (countHam.containsKey(s)) {
        countHam.put(s, countHam.get(s).intValue() + 1);
      } else {
        countHam.put(s, 1);
      }

    }

    hamMailCount++;
  }

  private void countSpamWords(String data) {
    while (data.contains("  ")) {
      data = data.replace("  ", " ");
    }
    String[] words = data.split(" ");
    Set<String> wordSet = new HashSet<String>();
    Collections.addAll(wordSet, words);

    for (String s : wordSet) {
      if (countSpam.containsKey(s)) {
        countSpam.put(s, countSpam.get(s).intValue() + 1);
      } else {
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

  public Map<String, Integer> getProbSpamCOUNT() {
    return countSpam;
  }

  public Map<String, Integer> getProbHamCOUNT() {
    return countHam;
  }

}
