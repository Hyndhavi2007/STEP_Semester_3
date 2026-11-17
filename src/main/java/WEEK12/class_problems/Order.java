package WEEK12.class_problems;


class Order {

    // Static nested class
    static class LineItem {

        String productName;
        int quantity;
        double unitPrice;

        LineItem(String productName, int quantity, double unitPrice) {
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        double total() {
            return quantity * unitPrice;
        }
    }

    private LineItem[] items;
    private int count;

    Order(int capacity) {
        items = new LineItem[capacity];
        count = 0;
    }

    // Add item to order
    void addItem(LineItem item) {
        if (count < items.length) {
            items[count++] = item;
        }
    }

    // Member inner class
    class Cursor {

        int index = 0;

        boolean hasNext() {
            return index < count;
        }

        LineItem next() {
            if (!hasNext()) {
                return null;
            }

            return items[index++];
        }

        void reset() {
            index = 0;
        }
    }

    Cursor getCursor() {
        return new Cursor();
    }

    double orderTotal() {

        double total = 0;

        Cursor cursor = getCursor();

        while (cursor.hasNext()) {
            total += cursor.next().total();
        }

        return total;
    }
}

class Main {

    public static void main(String[] args) {

        Order order = new Order(100);

        order.addItem(new Order.LineItem("Pen", 3, 10));
        order.addItem(new Order.LineItem("Book", 2, 250));
        order.addItem(new Order.LineItem("Bag", 1, 700));

        Order.Cursor cursor = order.getCursor();

        double total = 0;

        while (cursor.hasNext()) {

            Order.LineItem item = cursor.next();

            double itemTotal = item.total();

            System.out.println(item.productName + " " +
                    (int) itemTotal);

            total += itemTotal;
        }

        System.out.println("Total " + (int) total);

        System.out.println(
                "cursor: inner (reads the order's array)");

        System.out.println(
                "line item: static nested (needs no order)");
    }
}
