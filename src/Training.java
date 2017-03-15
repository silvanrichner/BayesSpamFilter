import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Training {

  public static double middlevalue;
  String path;


  Map<String, Integer> countHam;
  Map<String, Integer> countSpam;
  Map<String, Double> probHam;
  Map<String, Double> probSpam;
  int hamMailCount = 0;
  int spamMailCount = 0;
  MailChecker mc;

  public Training(String path, double defaultValue) {
    this.path = path;
    countHam = new TreeMap<String, Integer>();
    countSpam = new TreeMap<String, Integer>();
    probHam = new TreeMap<String, Double>();
    probSpam = new TreeMap<String, Double>();
    mc = new MailChecker(probSpam, probHam, defaultValue);
    run();
    middlevalue=calibrate();

  }


  private void run() {
    System.out.println("Starting to read training data...");

    File folder = new File(path + "ham-train");
    File[] listOfFiles = folder.listFiles();
    Arrays.stream(listOfFiles).forEach((f) -> countHamWords(Reader.read(f.toString())));

    for (Map.Entry<String, Integer> entry : countHam.entrySet()) {
      probHam.put(entry.getKey(), ((double) entry.getValue()) / ((double) hamMailCount));
    }
    System.out.println("Ham training data read");


    folder = new File(path + "spam-train");
    listOfFiles = folder.listFiles();
    Arrays.stream(listOfFiles).forEach((f) -> countSpamWords(Reader.read(f.toString())));
    for (Map.Entry<String, Integer> entry : countSpam.entrySet()) {
      probSpam.put(entry.getKey(), ((double) entry.getValue()) / ((double) spamMailCount));
    }
    System.out.println("Spam training data read");
  }

  private void countHamWords(String data) {

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

  private double calibrate() {
    File folder = new File(path + "ham-calibrate");
    File[] listOfFiles = folder.listFiles();

    double sum = 0;
    int count = 0;

    for (File f : listOfFiles) {
      double d = mc.check(Reader.read(f.toString()));
      sum += d;
      count++;
    }
    double hamCalib = sum/count;
    System.out.println("hamcalibrate: " + hamCalib);

    sum = 0;
    count = 0;

    folder = new File(path + "spam-calibrate");
    listOfFiles = folder.listFiles();
    for (File f : listOfFiles) {
      double d = mc.check(Reader.read(f.toString()));
      sum += d;
      count++;
    }
    double spamCalib = sum/count;

    System.out.println("spamcalibrate: " + spamCalib);
    System.out.println("middleValue: " + (hamCalib + spamCalib) / 2);
    return (hamCalib + spamCalib) / 2;
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
