package WEEK10.assigment_problems;

class TicketNode {
    int data;
    TicketNode next;

    TicketNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Question3 {

    public static TicketNode mergeSorted(TicketNode a, TicketNode b) {
        // Dummy node simplifies merging
        TicketNode dummy = new TicketNode(0);
        TicketNode tail = dummy;

        // Attach the smaller node each time
        while (a != null && b != null) {
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }

            tail = tail.next;
        }

        // Attach whichever list still has nodes
        if (a != null) {
            tail.next = a;
        } else {
            tail.next = b;
        }

        return dummy.next;
    }

    public static void printList(TicketNode head) {
        TicketNode current = head;

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
        // First sorted list: 3 -> 8 -> 15
        TicketNode a = new TicketNode(3);
        a.next = new TicketNode(8);
        a.next.next = new TicketNode(15);

        // Second sorted list: 5 -> 8 -> 12 -> 20
        TicketNode b = new TicketNode(5);
        b.next = new TicketNode(8);
        b.next.next = new TicketNode(12);
        b.next.next.next = new TicketNode(20);

        TicketNode merged = mergeSorted(a, b);
        printList(merged);

        // Sample 2: first list is empty
        TicketNode merged2 = mergeSorted(null, new TicketNode(4));
        printList(merged2);
    }
}
