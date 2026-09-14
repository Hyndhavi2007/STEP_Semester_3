package Constructors.class_problems;

public class ReconciliationEngine{

    static class BusTicketAccount {
        private static String depotName;

        private String bookingId;
        private double ticketFare;
        private double amountPaid;

        static {
            depotName = "SRM Bus Depot";
        }

        BusTicketAccount(String bookingId, double ticketFare) {
            if (bookingId == null || bookingId.trim().isEmpty()) {
                throw new IllegalArgumentException();
            }

            if (ticketFare < 0) {
                throw new IllegalArgumentException();
            }

            this.bookingId = bookingId;
            this.ticketFare = ticketFare;
            this.amountPaid = 0.0;
        }

        BusTicketAccount(String bookingId) {
            this(bookingId, 0.0);
        }

        public final double calculatePenalty(int minutesLate) {
            if (minutesLate < 0) {
                throw new IllegalArgumentException();
            }

            if (minutesLate == 0) {
                return 0.0;
            }

            double penalty = 0.0;

            int firstTier = Math.min(minutesLate, 5);
            penalty += firstTier * ticketFare * 0.005;

            if (minutesLate > 5) {
                int secondTier = Math.min(minutesLate, 15) - 5;
                penalty += secondTier * ticketFare * 0.01;
            }

            if (minutesLate > 15) {
                int thirdTier = minutesLate - 15;
                penalty += thirdTier * ticketFare * 0.02;
            }

            double minimumPenalty = ticketFare * 0.01;

            return Math.max(penalty, minimumPenalty);
        }

        public void processAccount(double amount, int minutesLate) {
            if (amount <= 0 || minutesLate < 0) {
                throw new IllegalArgumentException();
            }

            if (amount > ticketFare - amountPaid) {
                amountPaid = ticketFare;
            } else {
                amountPaid += amount;
            }
        }
    }

    static class SleeperBusTicketAccount extends BusTicketAccount {

        SleeperBusTicketAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        SleeperBusTicketAccount(String bookingId) {
            super(bookingId);
        }

        @Override
        public void processAccount(double amount, int minutesLate) {
            double discountedAmount = amount * 0.90;
            super.processAccount(discountedAmount, minutesLate);
        }
    }

    static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        if (accounts == null ||
                amounts == null ||
                minutesLateArray == null) {
            throw new IllegalArgumentException();
        }

        if (accounts.length != amounts.length ||
                accounts.length != minutesLateArray.length) {
            throw new IllegalArgumentException();
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;

        double grandTotalPenalties = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            BusTicketAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {
                int minutesLate = minutesLateArray[i];
                double amount = amounts[i];

                if (account instanceof SleeperBusTicketAccount) {
                    account.processAccount(amount, minutesLate);
                    sleeperCount++;
                } else {
                    account.processAccount(amount, minutesLate);
                    regularCount++;
                }

                grandTotalPenalties +=
                        account.calculatePenalty(minutesLate);

                processed++;

            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Account at index " + i + " skipped"
                );
            }
        }

        System.out.println(
                processed + " processed | " +
                        nullSkipped + " null skipped | " +
                        sleeperCount + " sleeper | " +
                        regularCount + " regular | " +
                        "grand total penalties = Rs " +
                        grandTotalPenalties
        );
    }

    public static void main(String[] args) {
        BusTicketAccount sleeper =
                new SleeperBusTicketAccount("BK001", 2000);

        BusTicketAccount regular =
                new BusTicketAccount("BK002", 1200);

        BusTicketAccount[] accounts = {
                sleeper,
                null,
                regular
        };

        double[] amounts = {
                1200,
                900,
                700
        };

        int[] minutesLateArray = {
                10,
                5,
                0
        };

        processBatch(
                accounts,
                amounts,
                minutesLateArray
        );
    }
}