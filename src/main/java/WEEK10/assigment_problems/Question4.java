
package WEEK10.assigment_problems;

class TrainNode {
    int data;
    TrainNode next;

    TrainNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Question4 {

    // Reverse a linked list
    private static TrainNode reverse(TrainNode head) {
        TrainNode prev = null;
        TrainNode current = head;

        while (current != null) {
            TrainNode nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }

        return prev;
    }

    public static boolean isPalindrome(TrainNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        // Find the middle of the list
        TrainNode slow = head;
        TrainNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the second half
        TrainNode secondHalf = reverse(slow.next);

        // Connect the first half to the reversed half
        slow.next = secondHalf;

        boolean palindrome = true;
        TrainNode first = head;
        TrainNode second = secondHalf;

        // Compare both halves
        while (second != null) {
            if (first.data != second.data) {
                palindrome = false;
                break;
            }

            first = first.next;
            second = second.next;
        }

        // Restore the original list
        slow.next = reverse(secondHalf);

        return palindrome;
    }

    static void printList(TrainNode head) {
        TrainNode current = head;

        while (current != null) {
            System.out.print(current.data);

            if (current.next != null) {
                System.out.print(" -> ");
            }

            current = current.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        TrainNode head = new TrainNode(1);
        head.next = new TrainNode(2);
        head.next.next = new TrainNode(2);
        head.next.next.next = new TrainNode(1);

        System.out.println(isPalindrome(head));
        printList(head);
    }
}
