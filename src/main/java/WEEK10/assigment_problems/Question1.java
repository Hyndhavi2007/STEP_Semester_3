
package WEEK10.assigment_problems;

class OrderNode {
    int data;
    OrderNode next;

    OrderNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Question1 {

    public static OrderNode removeKthFromEnd(OrderNode head, int k) {
        OrderNode dummy = new OrderNode(0);
        dummy.next = head;

        OrderNode fast = dummy;
        OrderNode slow = dummy;

        // Move fast k + 1 steps ahead
        for (int i = 0; i <= k; i++) {
            fast = fast.next;
        }

        // Move both pointers until fast reaches null
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Remove the k-th node from the end
        slow.next = slow.next.next;

        return dummy.next;
    }

    static void printList(OrderNode head) {
        OrderNode current = head;

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
        OrderNode head = new OrderNode(10);
        head.next = new OrderNode(20);
        head.next.next = new OrderNode(30);
        head.next.next.next = new OrderNode(40);
        head.next.next.next.next = new OrderNode(50);

        head = removeKthFromEnd(head, 2);
        printList(head);

        OrderNode head2 = new OrderNode(10);
        head2.next = new OrderNode(20);
        head2.next.next = new OrderNode(30);
        head2.next.next.next = new OrderNode(40);
        head2.next.next.next.next = new OrderNode(50);

        head2 = removeKthFromEnd(head2, 5);
        printList(head2);
    }
}
