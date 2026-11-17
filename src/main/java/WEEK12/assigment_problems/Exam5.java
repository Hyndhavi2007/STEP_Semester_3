package WEEK12.assigment_problems;


import java.util.Scanner;

class TokenNODE {
    int data;
    TokenNODE next;

    TokenNODE(int data) {
        this.data = data;
        this.next = null;
    }
}

class TokenPalindrome {

    // Reverse a linked list
    static TokenNODE reverse(TokenNODE head) {
        TokenNODE prev = null;
        TokenNODE current = head;

        while (current != null) {
            TokenNODE nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }

        return prev;
    }

    // Check whether the linked list is a palindrome
    static boolean isPalindrome(TokenNODE head) {

        if (head == null || head.next == null) {
            return true;
        }

        // Find the end of the first half
        TokenNODE slow = head;
        TokenNODE fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the second half
        TokenNODE secondHalf = reverse(slow.next);

        // Connect the reversed second half
        slow.next = secondHalf;

        TokenNODE first = head;
        TokenNODE second = secondHalf;

        boolean palindrome = true;

        // Compare both halves
        while (second != null) {
            if (first.data != second.data) {
                palindrome = false;
                break;
            }

            first = first.next;
            second = second.next;
        }

        // Restore the original linked list
        slow.next = reverse(secondHalf);

        return palindrome;
    }

    // Display the linked list
    static void display(TokenNODE head) {
        TokenNODE current = head;

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }

        System.out.println("null");
    }
}

public class Exam5 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        TokenNODE head = null;
        TokenNODE tail = null;

        for (int i = 0; i < n; i++) {
            int value = sc.nextInt();

            TokenNODE newNode = new TokenNODE(value);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        boolean result = TokenPalindrome.isPalindrome(head);

        System.out.println("Palindrome: " + result);
        System.out.println();
        System.out.print("List after check: ");
        TokenPalindrome.display(head);

        sc.close();
    }
}
