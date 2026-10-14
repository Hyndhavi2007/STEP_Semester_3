package Abstraction.class_problems;
abstract class PaymentMethod {
    private static int counter = 1001;
    private final String transactionId;

    public PaymentMethod() {
        transactionId = "TXN-" + counter++;
    }

    public abstract String processPayment(double amount);

    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public String getTransactionId() {
        return transactionId;
    }
}

class CreditCardPayment extends PaymentMethod {
    private String cardNumberLastFour;

    public CreditCardPayment(String cardNumberLastFour) {
        this.cardNumberLastFour = cardNumberLastFour;
    }

    @Override
    public String processPayment(double amount) {
        return "Charged $" + amount + " to card ending "
                + cardNumberLastFour + " - Txn " + getTransactionId();
    }
}

class CashPayment extends PaymentMethod {
    public CashPayment() {
        super();
    }

    @Override
    public String processPayment(double amount) {
        return "Received $" + amount + " in cash - Txn "
                + getTransactionId();
    }
}

public class Question1 {

    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    static void testPayment() {
        CreditCardPayment cc = new CreditCardPayment("4471");

        // Upcasting: CreditCardPayment stored in a PaymentMethod variable.
        PaymentMethod ref = cc;

        System.out.println(cc.processPayment(250.0));
        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        CashPayment cash = new CashPayment();
        System.out.println(cash.processPayment(40.0));

        printConfirmation(ref, 250.0);
    }

    public static void main(String[] args) {
        testPayment();
    }
}
