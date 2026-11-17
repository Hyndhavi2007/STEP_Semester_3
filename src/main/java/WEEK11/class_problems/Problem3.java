package WEEK11.class_problems;

class Problem3 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static Node addNumbers(Node a, Node b) {

        Node dummy = new Node(0);
        Node current = dummy;

        int carry = 0;

        while (a != null || b != null || carry != 0) {

            int sum = carry;

            if (a != null) {
                sum += a.data;
                a = a.next;
            }

            if (b != null) {
                sum += b.data;
                b = b.next;
            }

            carry = sum / 10;

            int digit = sum % 10;

            current.next = new Node(digit);
            current = current.next;
        }

        return dummy.next;
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

        // First number: 342
        Node a = new Node(2);
        a.next = new Node(4);
        a.next.next = new Node(3);

        // Second number: 465
        Node b = new Node(5);
        b.next = new Node(6);
        b.next.next = new Node(4);

        Node result = addNumbers(a, b);

        System.out.println("First Number:");
        display(a);

        System.out.println("Second Number:");
        display(b);

        System.out.println("Sum:");
        display(result);
    }
}
