package DSA.assigment_problems;
import java.util.*;

public class Mall {
    public static List<Long> footfallReport(
            int[] visitors, int[][] queries) {

        int n = visitors.length;
        long[] prefix = new long[n + 1];

        // Precompute prefix sums
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }

        List<Long> result = new ArrayList<>();

        for (int[] query : queries) {
            int start = query[0];
            int end = query[1];

            result.add(prefix[end + 1] - prefix[start]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};

        int[][] queries = {
                {0, 2},
                {2, 5},
                {4, 6},
                {3, 3}
        };

        System.out.println(footfallReport(visitors, queries));
    }
}