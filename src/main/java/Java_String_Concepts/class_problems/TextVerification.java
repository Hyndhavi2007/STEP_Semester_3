package Java_String_Concepts.class_problems;
import java.util.Scanner;
public class TextVerification {
    public static boolean isPalindromeIterative(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    public static boolean isPalindromeRecursive(String text) {
        return checkRecursive(text, 0, text.length() - 1);
    }
    private static boolean checkRecursive(String text, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }

        return checkRecursive(text, left + 1, right - 1);
    }
    public static boolean isPalindromeArrayReversal(String text) {
        char[] original = text.toCharArray();
        char[] reversed = new char[original.length];

        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }

        return String.valueOf(original).equals(String.valueOf(reversed));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = scanner.nextLine();

        boolean iterative = isPalindromeIterative(text);
        boolean recursive = isPalindromeRecursive(text);
        boolean arrayReversal = isPalindromeArrayReversal(text);

        System.out.println(
                "Iterative: " + (iterative ? "Palindrome" : "Not Palindrome")
                        + " | Recursive: " + (recursive ? "Palindrome" : "Not Palindrome")
                        + " | Array Reversal: " + (arrayReversal ? "Palindrome" : "Not Palindrome")
        );
        if (iterative == recursive && recursive == arrayReversal) {
            System.out.println("All three approaches agree.");
        } else {
            System.out.println("Warning: The approaches do not agree.");
        }

        scanner.close();
    }
}
