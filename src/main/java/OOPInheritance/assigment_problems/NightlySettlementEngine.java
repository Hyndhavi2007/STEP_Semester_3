
package OOPInheritance.assigment_problems.problem5;

import java.util.ArrayList;
import java.util.List;

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    private final int entryCode;
    private static int nextEntryCode = 1;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0;
        this.entryCode = nextEntryCode++;
    }

    public int getEntryCode() {
        return entryCode;
    }

    public static int getNextEntryCode() {
        return nextEntryCode;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        }
    }

    public void pay(double amount, String discountCode) {
        if (NightlySettlementEngine.isValidDiscountCode(discountCode)) {
            double discountedAmount = amount * 0.90;
            pay(discountedAmount);
        } else {
            pay(amount);
        }
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;
    private final List<Double> lateFeeHistory = new ArrayList<>();

    public RunnerEntry(String bibNumber, double entryFee,
                       String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    protected void applyLateFee(double amount) {
        if (amount > 0) {
            lateFeeHistory.add(amount);
        }
    }

    @Override
    public double getBalanceDue() {
        double penalties = 0;

        for (double fee : lateFeeHistory) {
            penalties += fee;
        }

        return super.getBalanceDue() + penalties;
    }
}

class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee,
                            String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }

    @Override
    public double getBalanceDue() {
        return super.getBalanceDue() - sponsorBonus;
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee,
                          int teamSize) {
        super(bibNumber, entryFee);

        if (teamSize <= 0) {
            throw new IllegalArgumentException(
                    "Team size must be positive");
        }

        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class NightlySettlementEngine {

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }

        return Character.isUpperCase(code.charAt(4))
                && Character.isLetter(code.charAt(4));
    }

    public static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relayCount = 0;
        int individualCount = 0;

        if (entries == null) {
            return "0 processed | 0 null skipped"
                    + " | 0 relay | 0 individual";
        }

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relayCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + relayCount + " relay | "
                + individualCount + " individual";
    }

    public static void main(String[] args) {
        RunnerEntry runner =
                new RunnerEntry("RUN100", 80, "Open");

        EliteRunnerEntry elite =
                new EliteRunnerEntry("ELT200", 150, "Elite", 20);

        RelayTeamEntry relay =
                new RelayTeamEntry("REL300", 300, 4);

        runner.pay(30);
        runner.pay(10, "M123A");
        runner.applyLateFee(20);

        System.out.println("Discount valid: "
                + isValidDiscountCode("M123A"));

        System.out.println("Runner balance: "
                + runner.getBalanceDue());

        System.out.println("Elite balance: "
                + elite.getBalanceDue());

        System.out.println("Runner entry code: "
                + runner.getEntryCode());

        System.out.println("Elite entry code: "
                + elite.getEntryCode());

        System.out.println("Relay entry code: "
                + relay.getEntryCode());

        RaceEntry[] entries = {runner, null, elite, relay};
        System.out.println(settleNight(entries));
    }
}
