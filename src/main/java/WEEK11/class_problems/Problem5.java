package WEEK11.class_problems;

class Problem5{

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Reverse linked list
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

    static void reorderList(Node head) {

        if (head == null || head.next == null) {
            return;
        }

        Node slow = head;
        Node fast = head;

        // Step 1: Find middle
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Split the list
        Node secondHalf = slow.next;
        slow.next = null;

        // Reverse second half
        secondHalf = reverse(secondHalf);

        // Step 3: Merge alternately
        Node first = head;
        Node second = secondHalf;

        while (second != null) {

            Node temp1 = first.next;
            Node temp2 = second.next;

            first.next = second;
            second.next = temp1;

            first = temp1;
            second = temp2;
        }
    }

    static void display(Node head) {

        while (head != null) {

            System.out.print(head.data);

            if (head.next != null) {
                System.out.print(" -> ");
            }

            head = head.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {

        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        System.out.println("Original Linked List:");
        display(head);

        reorderList(head);

        System.out.println("Reordered Linked List:");
        display(head);
    }
}
