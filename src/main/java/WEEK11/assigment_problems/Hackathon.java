package WEEK11.assigment_problems;

import java.util.Stack;

class Hackathon{

    static String expandBanner(String code) {

        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();

        StringBuilder current = new StringBuilder();

        int count = 0;

        for (int i = 0; i < code.length(); i++) {

            char ch = code.charAt(i);

            // If character is a digit
            if (Character.isDigit(ch)) {

                count = count * 10 + (ch - '0');

            }

            // If opening bracket
            else if (ch == '[') {

                countStack.push(count);
                stringStack.push(current);

                current = new StringBuilder();

                count = 0;

            }

            // If closing bracket
            else if (ch == ']') {

                int repeat = countStack.pop();

                StringBuilder previous = stringStack.pop();

                // Repeat current string
                for (int j = 0; j < repeat; j++) {
                    previous.append(current);
                }

                current = previous;

            }

            // If character is a letter
            else {

                current.append(ch);

            }
        }

        return current.toString();
    }

    public static void main(String[] args) {

        String code = "3[ab]2[c]";

        String result = expandBanner(code);

        System.out.println("Expanded Banner: " + result);

        // Second example
        String code2 = "2[x3[y]]z";

        String result2 = expandBanner(code2);

        System.out.println("Expanded Banner: " + result2);
    }
}
