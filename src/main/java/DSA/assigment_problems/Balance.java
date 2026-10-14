package DSA.assigment_problems;
import java.util.*;

public class Balance {
    public static long countPeriods(int[] transactions, long k) {
        Map<Long, Integer> prefixCount = new HashMap<>();

        prefixCount.put(0L, 1);

        long prefixSum = 0;
        long count = 0;

        for (int value : transactions) {
            prefixSum += value;

            long needed = prefixSum - k;

            count += prefixCount.getOrDefault(needed, 0);

            prefixCount.put(
                    prefixSum,
                    prefixCount.getOrDefault(prefixSum, 0) + 1
            );
        }

        return count;
    }

    public static void main(String[] args) {
        int[] transactions = {3, 4, -7, 1, 3, 3, 1, -4};

        System.out.println(countPeriods(transactions, 7));

        int[] transactions2 = {1, 2, 3};
        System.out.println(countPeriods(transactions2, 10));
    }
}