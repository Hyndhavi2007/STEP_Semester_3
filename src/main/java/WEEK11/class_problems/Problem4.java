package WEEK11.class_problems;


class Problem4 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static int getLength(Node head) {

        int count = 0;

        while (head != null) {
            count++;
            head = head.next;
        }

        return count;
    }

    static Node findIntersection(Node a, Node b) {

        int lenA = getLength(a);
        int lenB = getLength(b);

        // Calculate length difference
        int diff = Math.abs(lenA - lenB);

        // Move longer list ahead
        if (lenA > lenB) {

            for (int i = 0; i < diff; i++) {
                a = a.next;
            }

        } else {

            for (int i = 0; i < diff; i++) {
                b = b.next;
            }
        }

        // Find common node
        while (a != null && b != null) {

            if (a == b) {
                return a;
            }

            a = a.next;
            b = b.next;
        }

        return null;
    }

    public static void main(String[] args) {

        // Common linked list
        Node common = new Node(8);
        common.next = new Node(4);
        common.next.next = new Node(5);

        // First linked list: 4 -> 1 -> 8 -> 4 -> 5
        Node a = new Node(4);
        a.next = new Node(1);
        a.next.next = common;

        // Second linked list: 5 -> 6 -> 1 -> 8 -> 4 -> 5
        Node b = new Node(5);
        b.next = new Node(6);
        b.next.next = new Node(1);
        b.next.next.next = common;

        Node intersection = findIntersection(a, b);

        if (intersection != null) {
            System.out.println("Intersection Node: " + intersection.data);
        } else {
            System.out.println("No Intersection");
        }
    }
}
