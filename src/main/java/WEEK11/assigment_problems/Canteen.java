package WEEK11.assigment_problems;

import java.util.Stack;

class Canteen {

    static int[] priceStreak(int[] price) {

        int n = price.length;

        int[] result = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {

            // Remove previous days with smaller
            // or equal prices
            while (!stack.isEmpty() &&
                    price[stack.peek()] <= price[i]) {

                stack.pop();
            }

            // Calculate price streak
            if (stack.isEmpty()) {
                result[i] = i + 1;
            } else {
                result[i] = i - stack.peek();
            }

            // Push current day index
            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {

        int[] price = {100, 80, 60, 70, 60, 75, 85};

        int[] result = priceStreak(price);

        System.out.println("Price Streak:");

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }

        System.out.println();

        // Second example
        int[] price2 = {10, 20, 30};

        int[] result2 = priceStreak(price2);

        System.out.println("Price Streak:");

        for (int i = 0; i < result2.length; i++) {
            System.out.print(result2[i] + " ");
        }
    }
}
