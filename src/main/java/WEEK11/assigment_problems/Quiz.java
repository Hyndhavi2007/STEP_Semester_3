package WEEK11.assigment_problems;

import java.util.Stack;

class Quiz {

    // Check operator precedence
    static int precedence(char op) {

        if (op == '+' || op == '-') {
            return 1;
        }

        if (op == '*' || op == '/') {
            return 2;
        }

        return 0;
    }

    // Perform calculation
    static void calculate(Stack<Integer> values,
                          Stack<Character> operators) {

        int b = values.pop();
        int a = values.pop();

        char op = operators.pop();

        int result = 0;

        switch (op) {

            case '+':
                result = a + b;
                break;

            case '-':
                result = a - b;
                break;

            case '*':
                result = a * b;
                break;

            case '/':
                result = a / b;
                break;
        }

        values.push(result);
    }

    static int evaluate(String expr) {

        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;

        while (i < expr.length()) {

            char ch = expr.charAt(i);

            // Ignore spaces
            if (ch == ' ') {
                i++;
                continue;
            }

            // If digit, build multi-digit number
            if (Character.isDigit(ch)) {

                int number = 0;

                while (i < expr.length() &&
                        Character.isDigit(expr.charAt(i))) {

                    number = number * 10 +
                            (expr.charAt(i) - '0');

                    i++;
                }

                values.push(number);
                continue;
            }

            // If opening bracket
            else if (ch == '(') {

                operators.push(ch);

            }

            // If closing bracket
            else if (ch == ')') {

                while (!operators.isEmpty() &&
                        operators.peek() != '(') {

                    calculate(values, operators);
                }

                operators.pop(); // Remove '('

            }

            // If operator
            else if (ch == '+' || ch == '-' ||
                    ch == '*' || ch == '/') {

                while (!operators.isEmpty() &&
                        operators.peek() != '(' &&
                        precedence(operators.peek()) >= precedence(ch)) {

                    calculate(values, operators);
                }

                operators.push(ch);

            }

            i++;
        }

        // Perform remaining operations
        while (!operators.isEmpty()) {

            calculate(values, operators);
        }

        return values.pop();
    }

    public static void main(String[] args) {

        String expr = "2 * (3 + 4) - 10 / 5";

        int result = evaluate(expr);

        System.out.println("Expression: " + expr);
        System.out.println("Result: " + result);

        // Second example
        String expr2 = "(8 - 2) * (1 + 2) / 4";

        int result2 = evaluate(expr2);

        System.out.println("Expression: " + expr2);
        System.out.println("Result: " + result2);
    }
}
