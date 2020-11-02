import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Caesar {

  private static final int ALPHABET_SIZE = 26;
  private static final double[] REAL_FREQUENCIES =
      {0.082, 0.015, 0.028, 0.043, 0.13, 0.022, 0.02, 0.061, 0.07, 0.0015, 0.0077, 0.04, 0.024,
          0.067, 0.075, 0.019, 0.00095, 0.06, 0.063, 0.091, 0.028, 0.0098, 0.024, 0.0015, 0.02,
          0.00074};

  public static void main(String[] args) {
    double[] c = countChars(args[0].toLowerCase());
    List<Pair> chi2scores = new ArrayList<>();
    for(int i = 0; i < ALPHABET_SIZE; i++) {
      chi2scores.add(new Pair(i, chi2(c, i)));
    }
    Collections.sort(chi2scores);
    for(Pair p : chi2scores) {
      System.out.println(String.format("Key - %d, Score - %f: %s",
          p.getKey(), p.getScore(), shift(args[0].toLowerCase(), p.getKey())));
    }
  }

  private static String shift(String s, int k) {
    StringBuilder o = new StringBuilder();
    for(int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if(c >= 'a' && c <= 'z') {
        c = (char) ('a' + (c - 'a' + ALPHABET_SIZE - k) % ALPHABET_SIZE);
      }
      o.append(c);
    }
    return o.toString();
  }

  private static double[] countChars(String s) {
    double[] f = new double[ALPHABET_SIZE];
    double t = 0.0;
    for(int i = 0; i < ALPHABET_SIZE; i++) {
      f[i] = 0.0d;
    }
    for(int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if(c >= 'a' && c <= 'z') {
        f[c - 'a']++;
        t++;
      }
    }
    for(int i = 0; i < ALPHABET_SIZE; i++) {
      f[i] /= t;
    }
    return f;
  }

  private static double chi2(double[] c, int k) {
    double chi2 = 0.0d;
    for(int i = 0; i < ALPHABET_SIZE; i++) {
      double d = c[(i + k) % ALPHABET_SIZE] - REAL_FREQUENCIES[i];
      chi2 += d * d;
    }
    return chi2;
  }

  private static class Pair implements Comparable<Pair> {
    private final int key;
    private final double score;

    public Pair(int key, double score) {
      this.key = key;
      this.score = score;
    }

    public int compareTo(Pair o) {
      return Double.compare(getScore(), o.getScore());
    }

    public int getKey() {
      return key;
    }

    public double getScore() {
      return score;
    }
  }

}
