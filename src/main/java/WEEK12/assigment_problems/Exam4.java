package WEEK12.assigment_problems;
import java.util.Scanner;

class DueDayNODE {
    String title;
    int dueDay;
    DueDayNODE next;

    DueDayNODE(String title, int dueDay) {
        this.title = title;
        this.dueDay = dueDay;
        this.next = null;
    }
}

class LibraryReturns {

    // Main method required by the problem
    static DueDayNODE sortByDueDay(DueDayNODE head) {
        if (head == null || head.next == null) {
            return head;
        }

        // Find the middle of the linked list
        DueDayNODE slow = head;
        DueDayNODE fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Divide the list into two halves
        DueDayNODE secondHalf = slow.next;
        slow.next = null;

        // Recursively sort both halves
        DueDayNODE left = sortByDueDay(head);
        DueDayNODE right = sortByDueDay(secondHalf);

        // Merge the sorted halves
        return merge(left, right);
    }

    static DueDayNODE merge(DueDayNODE first, DueDayNODE second) {
        DueDayNODE dummy = new DueDayNODE("", 0);
        DueDayNODE current = dummy;

        while (first != null && second != null) {

            // <= keeps equal due-day books in their original order
            if (first.dueDay <= second.dueDay) {
                current.next = first;
                first = first.next;
            } else {
                current.next = second;
                second = second.next;
            }

            current = current.next;
        }

        // Attach remaining nodes
        if (first != null) {
            current.next = first;
        } else {
            current.next = second;
        }

        return dummy.next;
    }

    static void display(DueDayNODE head) {
        DueDayNODE current = head;

        while (current != null) {
            System.out.print(current.title + ":" + current.dueDay);

            if (current.next != null) {
                System.out.print(" -> ");
            }

            current = current.next;
        }

        System.out.println(" -> null");
    }
}

public class Exam4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        DueDayNODE head = null;
        DueDayNODE tail = null;

        for (int i = 0; i < n; i++) {
            String input = sc.next();

            String[] parts = input.split(":");

            String title = parts[0];
            int day = Integer.parseInt(parts[1]);

            DueDayNODE newNode = new DueDayNODE(title, day);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        head = LibraryReturns.sortByDueDay(head);

        LibraryReturns.display(head);

        sc.close();
    }
}
