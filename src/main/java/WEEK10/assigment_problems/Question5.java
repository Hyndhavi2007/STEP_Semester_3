
package WEEK10.assigment_problems;

class PolyNode {
    int coefficient;
    int exponent;
    PolyNode next;

    PolyNode(int coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
        this.next = null;
    }
}

public class Question5 {

    public static PolyNode addPolynomials(PolyNode p, PolyNode q) {
        PolyNode dummy = new PolyNode(0, 0);
        PolyNode tail = dummy;

        while (p != null && q != null) {
            if (p.exponent > q.exponent) {
                tail.next = new PolyNode(p.coefficient, p.exponent);
                p = p.next;
            } else if (p.exponent < q.exponent) {
                tail.next = new PolyNode(q.coefficient, q.exponent);
                q = q.next;
            } else {
                int sum = p.coefficient + q.coefficient;

                // Skip terms whose coefficients cancel to zero
                if (sum != 0) {
                    tail.next = new PolyNode(sum, p.exponent);
                    tail = tail.next;
                }

                p = p.next;
                q = q.next;
                continue;
            }

            tail = tail.next;
        }

        // Copy remaining terms from either polynomial
        while (p != null) {
            tail.next = new PolyNode(p.coefficient, p.exponent);
            tail = tail.next;
            p = p.next;
        }

        while (q != null) {
            tail.next = new PolyNode(q.coefficient, q.exponent);
            tail = tail.next;
            q = q.next;
        }

        return dummy.next;
    }

    public static void printPolynomial(PolyNode head) {
        if (head == null) {
            System.out.println("0");
            return;
        }

        PolyNode current = head;
        boolean first = true;

        while (current != null) {
            int coefficient = current.coefficient;
            int exponent = current.exponent;

            if (!first) {
                System.out.print(coefficient >= 0 ? " + " : " - ");
            } else if (coefficient < 0) {
                System.out.print("-");
            }

            int absCoefficient = Math.abs(coefficient);

            if (exponent == 0 || absCoefficient != 1) {
                System.out.print(absCoefficient);
            }

            if (exponent > 0) {
                System.out.print("x");
                if (exponent > 1) {
                    System.out.print("^" + exponent);
                }
            }

            first = false;
            current = current.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        // p = 5x^3 + 4x^2 + 2
        PolyNode p = new PolyNode(5, 3);
        p.next = new PolyNode(4, 2);
        p.next.next = new PolyNode(2, 0);

        // q = 5x^2 - 3x + 1
        PolyNode q = new PolyNode(5, 2);
        q.next = new PolyNode(-3, 1);
        q.next.next = new PolyNode(1, 0);

        PolyNode result = addPolynomials(p, q);
        printPolynomial(result);

        // Sample 2:
        // p = 3x^2 + 1
        PolyNode p2 = new PolyNode(3, 2);
        p2.next = new PolyNode(1, 0);

        // q = -3x^2 + 4x
        PolyNode q2 = new PolyNode(-3, 2);
        q2.next = new PolyNode(4, 1);

        PolyNode result2 = addPolynomials(p2, q2);
        printPolynomial(result2);
    }
}
