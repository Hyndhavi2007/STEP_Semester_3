package Constructors.class_problems;

import java.util.Arrays;

public class FairSplitter{

    static class FareSplitter {
        private String tripId;
        private double totalFare;
        private int passengerCount;

        FareSplitter(String tripId, double totalFare, int passengerCount) {
            if (totalFare < 0 || passengerCount <= 0) {
                throw new IllegalArgumentException();
            }

            this.tripId = tripId;
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        FareSplitter(String tripId, double totalFare) {
            this(tripId, totalFare, 1);
        }

        FareSplitter(String tripId) {
            this.tripId = tripId;
            this.totalFare = 0.0;
            this.passengerCount = 0;
        }

        double[] fareBreakdown() {
            if (passengerCount <= 0) {
                return new double[] {0.0, 0.0};
            }

            long fareInPaise = Math.round(totalFare * 100);
            long basePaise = fareInPaise / passengerCount;
            long remainder = fareInPaise % passengerCount;

            double[] result = new double[passengerCount];

            for (int i = 0; i < passengerCount; i++) {
                long share = basePaise;

                if (i == passengerCount - 1) {
                    share += remainder;
                }

                result[i] = share / 100.0;
            }

            return result;
        }

        boolean isConfirmationOverdue(int confirmed, int expected) {
            if (confirmed < 0 || expected < 0) {
                throw new IllegalArgumentException();
            }

            return confirmed < expected;
        }
    }

    public static void main(String[] args) {
        FareSplitter split1 =
                new FareSplitter("TRIP001", 100000, 3);

        System.out.println(Arrays.toString(split1.fareBreakdown()));

        FareSplitter split2 =
                new FareSplitter("TRIP003");

        System.out.println(Arrays.toString(split2.fareBreakdown()));

        System.out.println(
                split1.isConfirmationOverdue(2, 3)
        );
    }
}