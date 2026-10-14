
package WEEK8.class_problems;

import java.util.*;

class FoodItem {
    private final String name;
    private final double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Restaurant {
    private final String name;

    public Restaurant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class FoodCustomer {
    private final String name;

    public FoodCustomer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class LineItem {
    private final FoodItem foodItem;
    private final int quantity;

    public LineItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return foodItem.getPrice() * quantity;
    }
}

interface IPaymentMethod {
    boolean pay(double amount);

    String getMethodName();
}

class CreditCardPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.printf("Payment via Credit Card successful. Amount: $%.2f%n",
                amount);
        return true;
    }

    @Override
    public String getMethodName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    private final boolean paymentSucceeds;

    public DigitalWalletPayment(boolean paymentSucceeds) {
        this.paymentSucceeds = paymentSucceeds;
    }

    @Override
    public boolean pay(double amount) {
        if (paymentSucceeds) {
            System.out.printf(
                    "Payment via Digital Wallet successful. Amount: $%.2f%n",
                    amount);
            return true;
        }

        System.out.println("Payment via Digital Wallet failed.");
        return false;
    }

    @Override
    public String getMethodName() {
        return "Digital Wallet";
    }
}

class CashOnDelivery implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.printf(
                "Cash on Delivery selected. Amount due: $%.2f%n", amount);
        return true;
    }

    @Override
    public String getMethodName() {
        return "Cash on Delivery";
    }
}

class NotificationService {
    public void notifyCustomer(Customer customer, String message) {
        System.out.println("Notification to " + customer.getName()
                + ": " + message);
    }
}

enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID
}

class Order {
    private static int nextOrderId = 123;

    private final int orderId;
    private final Customer customer;
    private final Restaurant restaurant;
    private final List<LineItem> items = new ArrayList<>();
    private final NotificationService notificationService;
    private OrderStatus status = OrderStatus.CREATED;

    public Order(Customer customer, Restaurant restaurant,
                 NotificationService notificationService) {
        this.orderId = nextOrderId++;
        this.customer = customer;
        this.restaurant = restaurant;
        this.notificationService = notificationService;

        System.out.println("Order created.");
    }

    public void addItem(FoodItem foodItem, int quantity) {
        if (status != OrderStatus.CREATED) {
            System.out.println("Cannot add items after placing the order.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        items.add(new LineItem(foodItem, quantity));

        System.out.println("Added " + foodItem.getName()
                + " (Qty " + quantity + ").");
    }

    public double calculateTotal() {
        double total = 0.0;

        for (LineItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }

    public void placeOrder(IPaymentMethod paymentMethod) {
        if (status != OrderStatus.CREATED) {
            System.out.println("Order has already been placed.");
            return;
        }

        if (items.isEmpty()) {
            System.out.println(
                    "Cannot place order: Order must contain at least one item.");
            return;
        }

        status = OrderStatus.PENDING_PAYMENT;
        System.out.println("Order placed successfully.");

        boolean paymentSuccessful = paymentMethod.pay(calculateTotal());

        if (paymentSuccessful) {
            status = OrderStatus.PAID;
            System.out.println("Order status: " + status);
            notificationService.notifyCustomer(customer,
                    "Order #" + orderId + " placed and paid.");
        } else {
            System.out.println("Order status: " + status);
            notificationService.notifyCustomer(customer,
                    "Order #" + orderId + " placed, awaiting payment.");
        }
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getOrderId() {
        return orderId;
    }
}

public class Question5 {
    public static void main(String[] args) {
        Customer customer = new Customer("John");
        Restaurant restaurant = new Restaurant("Food Corner");
        NotificationService notifications = new NotificationService();

        // First order: payment succeeds.
        Order order1 = new Order(customer, restaurant, notifications);

        order1.placeOrder(new CreditCardPayment());

        order1.addItem(new FoodItem("Pizza", 200.00), 2);
        order1.addItem(new FoodItem("Soda", 30.00), 1);
        order1.placeOrder(new CreditCardPayment());

        System.out.println();

        // Second order: payment fails.
        Order order2 = new Order(customer, restaurant, notifications);

        order2.addItem(new FoodItem("Burger", 150.00), 1);
        order2.placeOrder(new DigitalWalletPayment(false));
    }
}
