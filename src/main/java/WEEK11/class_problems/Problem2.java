package WEEK11.class_problems;

class Problem2 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static Node reverse(Node head) {

        Node prev = null;
        Node current = head;

        while (current != null) {

            Node nextNode = current.next;

            current.next = prev;

            prev = current;
            current = nextNode;
        }

        return prev;
    }

    static boolean isPalindrome(Node head) {

        if (head == null || head.next == null) {
            return true;
        }

        Node slow = head;
        Node fast = head;

        // Find middle of linked list
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse second half
        Node secondHalf = reverse(slow.next);

        Node firstHalf = head;
        Node temp = secondHalf;

        // Compare both halves
        boolean result = true;

        while (temp != null) {

            if (firstHalf.data != temp.data) {
                result = false;
                break;
            }

            firstHalf = firstHalf.next;
            temp = temp.next;
        }

        // Restore original list
        slow.next = reverse(secondHalf);

        return result;
    }

    static void display(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);

        System.out.println("Linked List:");
        display(head);

        if (isPalindrome(head)) {
            System.out.println("Palindrome: true");
        } else {
            System.out.println("Palindrome: false");
        }
    }
}
