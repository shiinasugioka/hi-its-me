import java.util.*;

public class StableMatching {
    /*
    * Generates a stable matching.
    * 
    * @param prefHorses Preferences of the horses. prefHorses[i] lists the indices
    * of the riders that the i-th horse prefers, in order of preference.
    * 
    * @param prefRiders Preferences of the riders. prefHorses[i] lists the
    * indices of the horses that the i-th rider prefers, in order of preference.
    * 
    * @param horseOptimal if true, the generated stable matching should be most
    * optimal for the horses. Otherwise, it should be most optimal for the riders.
    * 
    * @return Computed stable matching. It is a 1D array, where arr[i]=j means the
    * i-th horse is matched to the j-th rider.
    */
    public static int[] findStableMatching(int[][] prefHorses, int[][] prefRiders, boolean horseOptimal) {
        assert prefHorses.length == prefRiders.length;
        int N = prefHorses.length;

        int[][] prefProposer = prefRiders;
        int[][] prefAccepter = prefHorses;

        if (!horseOptimal) {
            prefProposer = prefHorses;
            prefAccepter = prefRiders;
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

        if (!horseOptimal) {
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
    
    // returns true if horse prefers current match over next proposed option
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
        int[][] prefHorses = {
            { 1, 0, 2}, // Preferences of h0
            { 0, 2, 1},
            { 2, 0, 1}
        };
        int[][] prefRiders = {
            { 0, 1, 2}, // Preferences of h0
            { 1, 2, 0},
            { 0, 1, 2}
        };
        
        System.out.printf("Horse-optimal: ");
        System.out.println(Arrays.toString(findStableMatching(prefHorses, prefRiders, true)));
        System.out.printf("Rider-optimal: ");
        System.out.println(Arrays.toString(findStableMatching(prefHorses, prefRiders, false)));
    }
}
