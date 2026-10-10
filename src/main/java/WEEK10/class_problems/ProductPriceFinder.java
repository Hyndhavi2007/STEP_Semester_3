package WEEK10.class_problems;

public class ProductPriceFinder {

    // Find the first index where price >= target
    static int lowerBound(int[] prices, int target) {
        int low = 0;
        int high = prices.length;

        while (low < high) {
            int mid = (low+high) / 2;

            if (prices[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    // Find the first index where price > target
    static int upperBound(int[] prices, int target) {
        int low = 0;
        int high = prices.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (prices[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    public static void main(String[] args) {
        int[] prices = {100, 150, 150, 200, 300, 450};

        int minPrice = 150;
        int maxPrice = 300;

        int lower = lowerBound(prices, minPrice);
        int upper = upperBound(prices, maxPrice);

        int count = upper - lower;

        System.out.println("Lower bound index " + lower);
        System.out.println("Upper bound index " + upper);
        System.out.println("Count " + count);
    }
}
