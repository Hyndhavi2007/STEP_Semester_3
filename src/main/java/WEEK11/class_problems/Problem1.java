package WEEK11.class_problems;


class Problem1{

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static Node removeNthFromEnd(Node head, int n) {

        Node dummy = new Node(0);
        dummy.next = head;

        Node fast = dummy;
        Node slow = dummy;

        // Move fast pointer n steps ahead
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        // Move both pointers
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Remove the required node
        slow.next = slow.next.next;

        return dummy.next;
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
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        int n = 2;

        head = removeNthFromEnd(head, n);

        System.out.println("After removing node:");
        display(head);
    }
}
