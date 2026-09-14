package Constructors.class_problems;
public class PenaltyCalculator{

    static final class BoardingPenaltyCalculator {
        private final double minimumPenaltyPercent;

        BoardingPenaltyCalculator(double minimumPenaltyPercent) {
            if (minimumPenaltyPercent < 0) {
                throw new IllegalArgumentException();
            }

            this.minimumPenaltyPercent = minimumPenaltyPercent;
        }

        public final double calculatePenalty(
                double ticketFare,
                int minutesLate) {

            if (ticketFare < 0 || minutesLate < 0) {
                throw new IllegalArgumentException();
            }

            if (minutesLate == 0) {
                return 0.0;
            }

            double tieredPenalty = 0.0;

            int firstTier = Math.min(minutesLate, 5);
            tieredPenalty += firstTier * ticketFare * 0.005;

            if (minutesLate > 5) {
                int secondTier = Math.min(minutesLate, 15) - 5;
                tieredPenalty += secondTier * ticketFare * 0.01;
            }

            if (minutesLate > 15) {
                int thirdTier = minutesLate - 15;
                tieredPenalty += thirdTier * ticketFare * 0.02;
            }

            double minimumPenalty =
                    ticketFare * minimumPenaltyPercent / 100.0;

            return Math.max(tieredPenalty, minimumPenalty);
        }
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calculator =
                new BoardingPenaltyCalculator(1.0);

        System.out.println(
                "1 minute: Rs " +
                        calculator.calculatePenalty(1000, 1)
        );

        System.out.println(
                "0 minutes: Rs " +
                        calculator.calculatePenalty(1000, 0)
        );

        System.out.println(
                "16 minutes: Rs " +
                        calculator.calculatePenalty(1000, 16)
        );
    }
}
