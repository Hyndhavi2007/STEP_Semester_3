package WEEK10.assigment_problems;

class Node {
    String url;
    Node prev;
    Node next;

    Node(String url) {
        this.url = url;
        this.prev = null;
        this.next = null;
    }
}

class BrowserHistory {
    private Node current;

    public BrowserHistory(String homepage) {
        current = new Node(homepage);
    }

    public void visit(String url) {
        Node newNode = new Node(url);

        // Discard all forward history
        current.next = null;

        // Link the new page after the current page
        newNode.prev = current;
        current.next = newNode;

        // Update the current page
        current = newNode;
    }

    public String back(int steps) {
        while (steps > 0 && current.prev != null) {
            current = current.prev;
            steps--;
        }

        return current.url;
    }

    public String forward(int steps) {
        while (steps > 0 && current.next != null) {
            current = current.next;
            steps--;
        }

        return current.url;
    }
}

public class Question2 {
    public static void main(String[] args) {
        BrowserHistory browser = new BrowserHistory("srm.edu");

        browser.visit("a.com");
        browser.visit("b.com");
        browser.visit("c.com");

        System.out.println(browser.back(1));     // b.com
        System.out.println(browser.back(1));     // a.com
        System.out.println(browser.forward(1));  // b.com

        browser.visit("d.com");

        System.out.println(browser.forward(2));  // d.com
        System.out.println(browser.back(2));     // a.com
        System.out.println(browser.back(7));     // srm.edu
    }
}
