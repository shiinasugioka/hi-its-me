import java.util.*;

public class StableMatching {
    /*
    * Generates a stable matching.
    * 
    * @param prefM Preferences of M. prefM[i] lists the indices
    * of F that the i-th M prefers, in order of preference.
    * 
    * @param prefF Preferences of F. prefM[i] lists the
    * indices of M that the i-th F prefers, in order of preference.
    * 
    * @param M Optimal if true, the generated stable matching should be most
    * optimal for M. Otherwise, it should be most optimal for F.
    * 
    * @return Computed stable matching. It is a 1D array, where arr[i]=j means the
    * i-th M is matched to the j-th F.
    */
    public static int[] findStableMatching(int[][] prefM, int[][] prefF, boolean MOptimal) {
        assert prefM.length == prefF.length;
        int N = prefM.length;

        int[][] prefProposer = prefF;
        int[][] prefAccepter = prefM;

        if (!MOptimal) {
            prefProposer = prefM;
            prefAccepter = prefF;
        }
        
        int[] result = new int[N];
        Arrays.fill(result, -1);
        
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < N; i++) {
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int next = stack.pop();
            for (int i = 0; i < N; i++) {
                int h = prefProposer[next][i];
                if (result[h] == -1) {
                    result[h] = next;
                    break;
                } else {
                    int curr = result[h];
                    if (!preferKeep(prefAccepter[h], curr, next, N)) {
                        result[h] = next;
                        stack.push(curr);
                        break;
                    }
                }
            }
        }

        if (!MOptimal) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < result.length; i++) {
                map.put(result[i], i);
            }
            for (int i = 0; i < result.length; i++) {
                result[i] = map.get(i);
            }
        }
        return result;
    }
    
    // returns true if M prefers current match over next proposed option
    private static boolean preferKeep(int[] preference, int curr, int next, int N) {
        for (int i = 0; i < N; i++) {
            if (preference[i] == curr) {
                return true;
            }
            if (preference[i] == next) {
                return false;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        int[][] prefM = {
            { 1, 0, 2}, // Preferences of M0
            { 0, 2, 1},
            { 2, 0, 1}
        };
        int[][] prefF = {
            { 0, 1, 2}, // Preferences of F0
            { 1, 2, 0},
            { 0, 1, 2}
        };
        
        System.out.printf("M-optimal: ");
        System.out.println(Arrays.toString(findStableMatching(prefM, prefF, true)));
        System.out.printf("F-optimal: ");
        System.out.println(Arrays.toString(findStableMatching(prefM, prefF, false)));
    }
}
