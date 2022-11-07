// import java.util.Arrays;

public class Inversion {
    /*
    * Counts the number of inversions of a given permutation using
    * the Divide and Conquer paradigm.
    *
    * @param n The length of the permutation.
    *
    * @param perm A permutation of the elements [0, 1, ..., n-1].
    * That is, those elements 0,1,..., n-1 in some order.
    *
    * @return The number of inversions of perm.
    */
    public static int countInversions(int n, int[] perm) {
        assert perm.length == n;
        int result = 0;
        int start = 0;
        int stop = n - 1;

        result = helper(perm, start, stop);

        return result;
    }

    // start = 0, stop = n -1
    private static int helper(int[] A, int start, int stop) {
        int inversions = 0;

        if(start >= stop)
        return 0;

        int midpoint = (stop-start)/2 + start;

        inversions += helper(A, start, midpoint);
        inversions += helper(A, midpoint+1, stop);
        inversions += mergeAndCount(A, start, stop);

        return inversions;
    }

    private static int mergeAndCount(int[] A, int start, int stop) {
        int inv = 0;
        int[] temp = new int[A.length];
        int left = start;
        int mid = start + (stop - start) / 2;
        int right = mid + 1;
        int curr = start;
        while(left <= mid && right <= stop) {
            if(A[left] < A [right]) {
                temp[curr++] = A[left++];
            } else {
                temp[curr++] = A[right++];
                inv += (mid - left) + 1;
            }
        }
        while(left <= mid) {
            temp[curr++] = A[left++];
        }
        while(right <= stop) {
            temp[curr++]= A[right++];
            inv += (mid - left) + 1; //can skip. mid-left+1 is 0 here.
        }
        for(int i = start; i <= stop; i++) { //copy back into A, now sorted
            A[i] = temp[i];
        }
        return inv;
    }

    /*
    * If you want to write your own tests, put them here.
    */
    public static void main(String[] args) {
        int[] test = {5, 2, 4, 3, 1};
        System.out.println(countInversions(5, test)); // should be 5

        int[] test2 = {1, 3, 2, 4, 5};
        System.out.println(countInversions(5, test2)); // should be 1

        int[] test3 = {1, 2, 3, 5, 4};
        System.out.println(countInversions(5, test3)); // should be 1

        int[] test4 = {1, 2, 3, 4, 5};
        System.out.println(countInversions(5, test4)); // should be 0

        int[] test5 = {1};
        System.out.println(countInversions(1, test5)); // should be 0
    }
}
