package WEEK12.assigment_problems;

import java.util.*;

class node {
    int key;
    String page;
    node prev;
    node next;

    node(int key, String page) {
        this.key = key;
        this.page = page;
        this.prev = null;
        this.next = null;
    }
}

class RecentCache {

    int capacity;

    HashMap<Integer, node> map = new HashMap<>();

    node head;
    node tail;

    int lastEvicted = -1;
    boolean updated = false;

    RecentCache(int capacity) {
        this.capacity = capacity;

        head = new node(0, "");
        tail = new node(0, "");

        head.next = tail;
        tail.prev = head;
    }

    // Remove a node from its current position
    void remove(node temp) {
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
    }

    // Add node at the beginning (most recent)
    void addFirst(node temp) {
        temp.next = head.next;
        temp.prev = head;

        head.next.prev = temp;
        head.next = temp;
    }

    // Get page using key
    String get(int key) {

        if (!map.containsKey(key)) {
            return "-1";
        }

        node temp = map.get(key);

        // Make it most recently used
        remove(temp);
        addFirst(temp);

        return temp.page;
    }

    // Insert or update page
    void put(int key, String page) {

        lastEvicted = -1;
        updated = false;

        // If key already exists
        if (map.containsKey(key)) {

            node temp = map.get(key);

            temp.page = page;

            remove(temp);
            addFirst(temp);

            updated = true;
            return;
        }

        // If cache is full
        if (map.size() == capacity) {

            node lru = tail.prev;

            lastEvicted = lru.key;

            remove(lru);
            map.remove(lru.key);
        }

        // Create new node
        node temp = new node(key, page);

        addFirst(temp);
        map.put(key, temp);
    }

    // Display cache from most recent to least recent
    void display() {

        System.out.print("Cache (most recent first): ");

        node temp = head.next;

        while (temp != tail) {

            System.out.print(temp.key + "=" + temp.page);

            temp = temp.next;

            if (temp != tail) {
                System.out.print(", ");
            }
        }

        System.out.println();
    }
}

public class Pages {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int capacity = sc.nextInt();
        int n = sc.nextInt();

        RecentCache cache = new RecentCache(capacity);

        for (int i = 0; i < n; i++) {

            String operation = sc.next();

            if (operation.equals("put")) {

                int key = sc.nextInt();
                String page = sc.next();

                cache.put(key, page);

                System.out.print("put(" + key + "," + page + ")");

                if (cache.updated) {
                    System.out.println(" updated");
                }
                else if (cache.lastEvicted != -1) {
                    System.out.println(" evicted " + cache.lastEvicted);
                }
                else {
                    System.out.println();
                }
            }

            else if (operation.equals("get")) {

                int key = sc.nextInt();

                String result = cache.get(key);

                System.out.println("get(" + key + ") -> " + result);
            }
        }

        cache.display();

        sc.close();
    }
}
