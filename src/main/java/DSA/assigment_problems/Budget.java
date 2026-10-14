package DSA.assigment_problems;
import java.util.*;
public class Budget{
    public static int[] longestStreak(int[] costs, long budget) {
        int left = 0;
        int windowSum = 0;
        int maxLength = 0;
        int bestStart = -1;

        for (int right = 0; right < costs.length; right++) {
            windowSum += costs[right];

            while (windowSum > budget) {
                windowSum -= costs[left];
                left++;
            }

            int length = right - left + 1;

            if (length > maxLength) {
                maxLength = length;
                bestStart = left;
            }
        }

        if (maxLength == 0) {
            return new int[]{0, -1};
        }

        return new int[]{maxLength, bestStart};
    }

    public static void main(String[] args) {
        int[] costs = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        long budget = 8;

        int[] result = longestStreak(costs, budget);
        System.out.println(Arrays.toString(result));

        int[] costs2 = {9, 10};
        System.out.println(
                Arrays.toString(longestStreak(costs2, 8))
        );
    }
}