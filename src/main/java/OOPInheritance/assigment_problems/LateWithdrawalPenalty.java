
package OOPInheritance.assigment_problems;

import java.util.ArrayList;
import java.util.List;

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;
    private final List<Double> lateFeeHistory = new ArrayList<>();

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        }
    }

    protected void applyLateFee(double amount) {
        if (amount > 0) {
            lateFeeHistory.add(amount);
        }
    }

    public List<Double> getLateFeeHistory() {
        return new ArrayList<>(lateFeeHistory);
    }

    public double getBalanceDue() {
        double penalties = 0;
        for (double fee : lateFeeHistory) {
            penalties += fee;
        }
        return entryFee - amountPaid + penalties;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee,
                       String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class LateWithdrawalPenalty {
    public static void main(String[] args) {
        RunnerEntry runner =
                new RunnerEntry("RUN100", 80, "Open 10K");

        runner.pay(30);
        runner.applyLateFee(20);

        System.out.println("Balance: " + runner.getBalanceDue());
        System.out.println("Late fees: " + runner.getLateFeeHistory());
    }
}
