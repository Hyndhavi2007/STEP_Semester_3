package Constructors.assignment_problems;
public final class SurgeFee {
    private final double minimumSurgePercent;

    public SurgeFee(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Invalid minimum surge percent");
        }

        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Invalid order value or delay");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        int firstTier = Math.min(delayMinutes, 5);
        int secondTier = Math.min(Math.max(delayMinutes - 5, 0), 10);
        int thirdTier = Math.max(delayMinutes - 15, 0);

        double surgeFee =
                orderValue * 0.005 * firstTier
                        + orderValue * 0.01 * secondTier
                        + orderValue * 0.02 * thirdTier;

        double minimumFee =
                orderValue * minimumSurgePercent / 100.0;

        return Math.max(surgeFee, minimumFee);
    }

    public static void main(String[] args) {
        SurgeFee calculator =
                new SurgeFee(1.0);

        System.out.println(
                "Rs " + calculator.calculateSurgeFee(500, 0)
        );

        System.out.println(
                "Rs " + calculator.calculateSurgeFee(500, 1)
        );

        System.out.println(
                "Rs " + calculator.calculateSurgeFee(500, 16)
        );
    }
}