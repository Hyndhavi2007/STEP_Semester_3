package Java_String_Concepts.class_problems;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class UniqueLetterHunt {
    public static char findFirstNonRepeatingChar(String text) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char ch : text.toCharArray()) {
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }
        for (char ch : text.toCharArray()) {
            if (frequency.get(ch) == 1) {
                return ch;
            }
        }
        return '\0';
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word or sentence: ");
        String text = scanner.nextLine();
        char result = findFirstNonRepeatingChar(text);
        if (result != '\0') {
            System.out.println("First Non-Repeating Character: '" + result + "'");
        } else {
            System.out.println("No Non-Repeating Character Found");
        }
        scanner.close();
    }
}
