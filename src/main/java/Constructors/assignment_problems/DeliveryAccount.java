package Constructors.assignment_problems;
public class DeliveryAccount {
    protected static final double MINIMUM_SURGE_PERCENT = 1.0;
    protected String studentId;
    protected double orderValue;

    static {
        System.out.println("Delivery Account System Started");
    }

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Invalid value");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        int first = Math.min(delayMinutes, 5);
        int second = Math.min(Math.max(delayMinutes - 5, 0), 10);
        int third = Math.max(delayMinutes - 15, 0);

        double fee =
                orderValue * 0.005 * first
                        + orderValue * 0.01 * second
                        + orderValue * 0.02 * third;

        double minimumFee =
                orderValue * MINIMUM_SURGE_PERCENT / 100.0;

        return Math.max(fee, minimumFee);
    }

    public void processAccount(
            DeliveryAccount account,
            double amount,
            int delayMinutes) {

        if (account == null) {
            return;
        }

        double fee = account.calculateSurgeFee(delayMinutes);

        if (account instanceof PremiumAccount) {
            fee = fee * 0.5;
        }

        System.out.println(account.studentId + " surge fee = " + fee);
    }

    public static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        if (accounts == null ||
                amounts == null ||
                delayMinutesArray == null) {

            System.out.println("Invalid batch");
            return;
        }

        if (accounts.length != amounts.length ||
                accounts.length != delayMinutesArray.length) {

            System.out.println("Invalid batch");
            return;
        }

        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double grandTotal = 0.0;

        for (int i = 0; i < accounts.length; i++) {

            DeliveryAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {
                double fee =
                        account.calculateSurgeFee(
                                delayMinutesArray[i]);

                if (account instanceof PremiumAccount) {
                    fee = fee * 0.5;
                    premium++;
                } else {
                    regular++;
                }

                processed++;
                grandTotal += fee;

            } catch (IllegalArgumentException e) {
                continue;
            }
        }

        System.out.println(
                processed + " processed | " +
                        nullSkipped + " null skipped | " +
                        premium + " premium | " +
                        regular + " regular | " +
                        "grand total surge fees = " +
                        grandTotal
        );
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
                new PremiumAccount("STU001", 500),
                null,
                new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {
                500,
                400,
                300
        };

        int[] delayMinutesArray = {
                10,
                5,
                0
        };

        processBatch(
                accounts,
                amounts,
                delayMinutesArray
        );
    }
}

class PremiumAccount extends DeliveryAccount {

    public PremiumAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public PremiumAccount(String studentId) {
        super(studentId);
    }
}