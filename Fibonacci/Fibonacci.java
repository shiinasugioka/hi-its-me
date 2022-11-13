import java.util.*;

public class Fibonacci {
    /*
    * Returns the nth term in the Fibonacci Sequence, with
    * F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2) for n > 1
    *
    * @param n The term index
    *
    * @return The nth Fibonacci number
    */
    public static int F(int n) {
        Map<Integer, Integer> map = new HashMap<>();

        if (n < 0) {
            throw new ArithmeticException("n occurances cannot be negative");
        }
        return memoize(n, map); 
    }

    private static int memoize(int n, Map<Integer, Integer> map) {
        if (n <= 1) {
            return n;
        } else if (!map.containsKey(n)) {
            map.put(n, memoize(n - 1, map) + memoize(n - 2, map));
        }
        return map.get(n);
    }
    
    public static void main(String[] args) {
        int i = 0;
        while (i <= 500) {
            System.out.println(i + "th fib number: " + F(i));
            i++;
        }
    }
}
