import java.io.File;
import java.util.Map;

public class MainFilter {
  static Training t;
  static MailChecker mc;
  final static String PATH = System.getProperty("user.dir") + File.separator + "res" + File.separator;
  final static double THRESHOLD=0.88;

  public static void main(String[] args) {
//
    //trains and calibrates Filter
    t = new Training(PATH);
    //checks
    mc = new MailChecker(t.getSpamliness());

    //Prints entrys of SpamCount if count>10
    // printSpamDictWithProb();

    //Prints entrys of HamCount if count>10
//    printHamDictWithProb();
    test();
  }

  public static void test() {

    File folder = new File(PATH + "ham-test");
    File[] listOfFiles = folder.listFiles();
    int spamCounter = 0;
    int hamCounter = 0;
    int gesamt=0;

    for (File f : listOfFiles) {
      double d = mc.check(Reader.read(f.toString()));
      if (d > THRESHOLD) {
        hamCounter++;
      } else {
        spamCounter++;
        //	System.out.println((Reader.read(f.toString())));
      }
      gesamt++;

    }
    System.out.println();
    System.out.println();
    System.out.println("---------Auswertung hamTest anz Mails: "+ gesamt+" ---------");
    System.out.println("hamCounter: " + hamCounter);
    System.out.println("spamCounter: " + spamCounter);
    System.out.println();

    hamCounter = 0;
    spamCounter = 0;
    gesamt=0;

    folder = new File(PATH + "spam-test");
    listOfFiles = folder.listFiles();
    for (File f : listOfFiles) {
      double d = mc.check(Reader.read(f.toString()));
      if (d > THRESHOLD) {
        hamCounter++;
      } else {
        spamCounter++;
      }
      gesamt++;
    }
    System.out.println("---------Auswertung spamTest anz Mails: "+ gesamt+" ---------");
    System.out.println("hamCounter: " + hamCounter);
    System.out.println("spamCounter: " + spamCounter);
  }


  private static void printHamDictWithProb() {

    for (Map.Entry<String, Integer> entry : t.getProbHamCOUNT().entrySet()) {
      if (entry.getValue() > 10) {
        System.out.println(entry.getValue() + " p:" + t.getProbHam().
            get(entry.getKey()).toString().substring(0, t.getProbHam().
            get(entry.getKey()).toString().length() > 4 ? 4 : 1) + "----------" +
            entry.getKey().substring(0, entry.getKey().length() < 10 ? entry.getKey().length() : 10));
      }
    }
  }

  private static void printSpamDictWithProb() {
    for (Map.Entry<String, Integer> entry : t.getProbSpamCOUNT().entrySet()) {
      if (entry.getValue() > 10) {
        System.out.println(entry.getValue() + " p:" + t.getProbSpam().
            get(entry.getKey()).toString().substring(0, t.getProbSpam().
            get(entry.getKey()).toString().length() > 4 ? 4 : 1) + "----------" +
            entry.getKey().substring(0, entry.getKey().length() < 10 ? entry.getKey().length() : 10));
      }
    }
  }

}
