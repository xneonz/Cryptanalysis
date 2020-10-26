/**
 * Implementation of Pollard's p - 1 algorithm to factorize large composites
 *
 * @author Jacob Baird
 */
public class Pollard {
  public static void main(String[] args) {
    long b = Long.parseLong(args[0]);
    long n = Long.parseLong(args[1]);
    long a = factMod(2L, b, n);
    long d = gcd(a - 1L, n);
    System.out.println(String.format("%d = %d x %d", n, d, n / d));
  }
  /*
  * This method returns the value a raised to the power of b! mod by n
   */
  private static long factMod(long a, long b, long n) {
    for(long i = 1L; i <= b; i++) {
      a = powMod(a, i, n);
    }
    return a;
  }
  /*
  * This method returns the value of b raised to the power of p mod by n
   */
  private static long powMod(long b, long p, long n) {
    if(p == 0L) {
      return 1L;
    } else if(p == 1L) {
      return b;
    } else {
      long h = powMod(b, p / 2L, n);
      return (p % 2 == 0) ? safeMult(h, h, n) : safeMult(safeMult(h, h, n), b, n);
    }
  }
  /*
  * This method safely multiplies a and b and mods by p within 64 bits
   */
  private static long safeMult(long a, long b, long p) {
    if(numBits(a) + numBits(b) <= 63L) {
      return (a * b) % p;
    } else {
      long maxBits = 63L - numBits(a);
      long maxCoef = 1L << (maxBits - 1L);
      return (safeMult(a, maxCoef, p) + safeMult(a, b - maxCoef, p)) % p;
    }
  }
  /*
  * This method counts the number of bits in a beginning with the leading 1
   */
  private static long numBits(long a) {
    long i = 0;
    while(1L << i <= a) {
      i++;
    }
    return i;
  }
  /*
  * This method calculates the greatest common factor of a and b, albeit slowly
   */
  private static long gcd(long a, long b) {
    long m = (a > b) ? b : a;
    while(m > 0L) {
      if(a % m == 0L && b % m == 0L) {
        return m;
      }
      m--;
    }
    return 1L;
  }
}