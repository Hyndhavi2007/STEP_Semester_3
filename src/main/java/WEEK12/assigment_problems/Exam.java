
package WEEK12.assigment_problems;

import java.util.HashMap;

class NODE {
    int key;
    String page;
    NODE prev;
    NODE next;

    NODE(int key, String page) {
        this.key = key;
        this.page = page;
    }
}

class ExamRecentPagesCache {
    private int capacity;
    private HashMap<Integer, NODE> map;
    private NODE head;
    private NODE tail;

    ExamRecentPagesCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        head = new NODE(0, "");
        tail = new NODE(0, "");

        head.next = tail;
        tail.prev = head;
    }

    // Remove a node from its current position
    private void removeNode(NODE node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Add a node at the most recently used position
    private void addToEnd(NODE node) {
        node.prev = tail.prev;
        node.next = tail;

        tail.prev.next = node;
        tail.prev = node;
    }

    // Move a node to the most recently used position
    private void moveToEnd(NODE node) {
        removeNode(node);
        addToEnd(node);
    }

    // Get a page using its key
    public String get(int key) {
        if (!map.containsKey(key)) {
            return null;
        }

        NODE node = map.get(key);
        moveToEnd(node);

        return node.page;
    }

    // Insert or update a page
    public void put(int key, String page) {
        if (capacity <= 0) {
            return;
        }

        if (map.containsKey(key)) {
            NODE node = map.get(key);
            node.page = page;
            moveToEnd(node);
            return;
        }

        if (map.size() == capacity) {
            NODE leastRecent = head.next;
            removeNode(leastRecent);
            map.remove(leastRecent.key);
        }

        NODE newNode = new NODE(key, page);
        map.put(key, newNode);
        addToEnd(newNode);
    }
}

public class Exam {
    public static void main(String[] args) {

        ExamRecentPagesCache cache =
                new ExamRecentPagesCache(3);

        cache.put(1, "Google");
        cache.put(2, "YouTube");
        cache.put(3, "Instagram");

        System.out.println(cache.get(1));

        cache.put(4, "Facebook");

        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }
}
