import java.util.HashMap;
import java.util.Map;

public class MailChecker {

  private Training training;

  public MailChecker(Training training) {
    this.training = training;
  }

  public double check(String mail) {
    //   System.out.println(mail);
    String[] words = mail.split(" ");
    Map<String, Double> map = new HashMap<>();
    //  System.out.println("email enthält "+words.length+" wörter");
    int i = 0;
    for (String s : words) {
      double spamProb, hamProb;
      if (training.probSpam.containsKey(s)) {
        i++;
        spamProb = training.probSpam.get(s);
        hamProb = training.probHam.get(s);
        map.put(s, spamProb / (spamProb + hamProb));


        //  System.out.println(s + ": " + spamProb / (spamProb + hamProb));
      }
    }




/*TODO Implement Bayes formula, list contains all Words this email contains
    word.hamValue is the probability the word is ham
    word.spamValue vice versa
    word.spaminess is spamvalue/(spamvalue+hamvalue)
    https://en.wikipedia.org/wiki/Naive_Bayes_spam_filtering#Other_expression_of_the_formula_for_combining_individual_probabilities

    Problem: n wird negativ und gross... damit 1/1... etwas witzlos

    Andere Variante failed weil Wert zu klein wird(floating point underflow)
*/


    //Variante 1 nach Wikipedia

    float n = 0;
    for (String key : map.keySet()) {
      n += (Math.log(1 - map.get(key)) - Math.log(map.get(key)));
    }
   // System.out.println(1 + "/" + "(1+" + "E^" + (n));
    double p = 1 / (1 + Math.exp(n));
   // System.out.println("mail contained " + i + " significant words out of " + words.length + ". Probability that this Email is Spam is " + " 1/(1+e^" + n + ")" + "=" + p);
    return p;


    //Variante 2 mit floating point underflow für lange Strings
/*
    float multiplyProb = 1.0f;
    float prodOfOneMinusProb = 1.0f;
    for (int j = 0; j < words.length; j++) {
      if (map.containsKey(words[j])) {
        multiplyProb *= map.get(words[j]);
        prodOfOneMinusProb *= (1.0f - map.get(words[j]));
      }
    }
    float probOfSpam = multiplyProb / (multiplyProb + prodOfOneMinusProb);
    System.out.println(probOfSpam);
    return probOfSpam;
*/
  }
}








