package String_Operations.class_problems;
import java.util.Scanner;

public class BankTransactionReference {

    static String normalizeReference(String raw) {
        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference.toUpperCase();
        }

        String bankCode = reference.substring(0, 3).toUpperCase();
        String remaining = reference.substring(3);

        return bankCode + remaining;
    }

    static String validateAndFormat(String reference) {

        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }
        for (int i = 3; i < reference.length(); i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }
        String date = reference.substring(3, 9);
        String sequence = reference.substring(9);
        String formattedDate =
                date.substring(0, 2) + "/" +
                        date.substring(2, 4) + "/" +
                        date.substring(4, 6);

        StringBuilder result = new StringBuilder();

        result.append("[")
                .append(reference.substring(0, 3))
                .append("] DATE: ")
                .append(formattedDate)
                .append(" | SEQ: ")
                .append(sequence);

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter transaction reference: ");
        String raw = sc.nextLine();

        String normalizedReference = normalizeReference(raw);

        System.out.println(validateAndFormat(normalizedReference));

        sc.close();
    }
}
