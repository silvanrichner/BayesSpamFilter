import java.io.File;

/**
 * Created by pwg on 26.03.17.
 */
public class Calibration {
  private String path;
  private MailChecker mc;
  public double calibration;


  public Calibration(String path, MailChecker mc) {
    this.path = path;
    this.mc = mc;
    File folder = new File(path + "ham-calibrate");
    File[] listOfFiles = folder.listFiles();

    double sum = 0;
    int count = 0;

    for (File f : listOfFiles) {
      double d = mc.check(Reader.read(f.toString()));
      sum += d;
      count++;
    }
    double hamCalib = sum / count;
    System.out.println("------------------------");
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
    double spamCalib = sum / count;
    System.out.println("spamcalibrate: " + spamCalib);
    this.calibration = (hamCalib + spamCalib) / 2;
    System.out.println("------------------------");
  }

  public double getCalibrationValue() {
    return calibration;
  }
}
