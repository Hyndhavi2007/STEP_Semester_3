package WEEK12.assigment_problems;

import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LoopDetector {

    static String detectLoop(Node head) {

        Node slow = head;
        Node fast = head;

        // Step 1: Detect whether a loop exists
        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                break;
            }
        }

        // No loop exists
        if (fast == null || fast.next == null) {
            return "Loop: no";
        }

        // Step 2: Find the loop length
        int length = 1;
        Node temp = slow.next;

        while (temp != slow) {
            length++;
            temp = temp.next;
        }

        // Step 3: Find the starting node of the loop
        Node p1 = head;
        Node p2 = slow;

        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }

        return "Loop: yes, starts at stop "
                + p1.data + ", length " + length;
    }
}

public class Delivery {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of stops:");
        int n = sc.nextInt();

        Node[] nodes = new Node[n];

        System.out.println("Enter stop numbers:");

        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(sc.nextInt());
        }

        // Connect nodes
        for (int i = 0; i < n - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }

        System.out.println("Enter pos:");
        int pos = sc.nextInt();

        // Create loop if pos is not -1
        if (pos != -1) {
            nodes[n - 1].next = nodes[pos];
        }

        System.out.println(
                LoopDetector.detectLoop(nodes[0])
        );

        sc.close();
    }
}

