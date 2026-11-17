package WEEK11.assigment_problems;

class Bus {

    int[] arr;
    int front;
    int size;
    int capacity;

    // Constructor
    Bus(int capacity) {

        this.capacity = capacity;

        arr = new int[capacity];

        front = 0;
        size = 0;
    }

    // Insert bus at front
    boolean insertFront(int bus) {

        if (isFull()) {
            return false;
        }

        front = (front - 1 + capacity) % capacity;

        arr[front] = bus;

        size++;

        return true;
    }

    // Insert bus at rear
    boolean insertLast(int bus) {

        if (isFull()) {
            return false;
        }

        int rear = (front + size) % capacity;

        arr[rear] = bus;

        size++;

        return true;
    }

    // Delete front bus
    boolean deleteFront() {

        if (isEmpty()) {
            return false;
        }

        front = (front + 1) % capacity;

        size--;

        return true;
    }

    // Delete rear bus
    boolean deleteLast() {

        if (isEmpty()) {
            return false;
        }

        size--;

        return true;
    }

    // Get front bus
    int getFront() {

        if (isEmpty()) {
            return -1;
        }

        return arr[front];
    }

    // Get rear bus
    int getRear() {

        if (isEmpty()) {
            return -1;
        }

        int rear = (front + size - 1) % capacity;

        return arr[rear];
    }

    // Check empty
    boolean isEmpty() {

        return size == 0;
    }

    // Check full
    boolean isFull() {

        return size == capacity;
    }
}

class Main {

    public static void main(String[] args) {

        Bus b = new Bus(3);

        System.out.println(b.insertLast(101));

        System.out.println(b.insertLast(102));

        System.out.println(b.insertFront(100));

        System.out.println(b.insertFront(99));

        System.out.println(b.getRear());

        System.out.println(b.isFull());

        System.out.println(b.deleteLast());

        System.out.println(b.insertFront(98));

        System.out.println(b.getFront());
    }
}

