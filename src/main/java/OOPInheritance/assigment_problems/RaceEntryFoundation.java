
package OOPInheritance.assigment_problems.problem1;

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

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

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public static String registerBatch(
            String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered
                + " | Rejected: " + rejected;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee,
                       String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public void announce() {
        System.out.println("Runner " + bibNumber
                + " - " + category);
    }
}

public class RaceEntryFoundation {
    public static void main(String[] args) {
        RunnerEntry runner =
                new RunnerEntry("BIB2001", 80, "Open 10K");

        runner.pay(30);
        System.out.println("Balance: " + runner.getBalanceDue());
        runner.announce();

        String[] bibs = {"BIB1", "B1", "BIB2"};
        System.out.println(RaceEntry.registerBatch(bibs, 80));
    }
}
