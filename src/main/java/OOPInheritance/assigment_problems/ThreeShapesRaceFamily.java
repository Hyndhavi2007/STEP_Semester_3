
package OOPInheritance.assigment_problems.problem2;

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
        if (amount > 0) amountPaid += amount;
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public void announce() {
        System.out.println("Race entry: " + bibNumber);
    }
}

class RunnerEntry extends RaceEntry {
    protected String category;

    public RunnerEntry(String bibNumber, double entryFee,
                       String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public void announce() {
        System.out.println("Runner: " + bibNumber
                + ", Category: " + category);
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
    public double getBalanceDue() {
        return super.getBalanceDue() - sponsorBonus;
    }

    @Override
    public void announce() {
        System.out.println("Elite runner: " + bibNumber);
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

    @Override
    public void announce() {
        System.out.println("Relay team: " + bibNumber
                + ", Team size: " + teamSize);
    }
}

public class ThreeShapesRaceFamily {
    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "EliteRunnerEntry";
        } else if (entry instanceof RunnerEntry) {
            return "RunnerEntry";
        } else if (entry instanceof RelayTeamEntry) {
            return "RelayTeamEntry";
        }
        return "RaceEntry";
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;
        for (RaceEntry entry : entries) {
            if (entry != null) {
                total += entry.getBalanceDue();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        RaceEntry[] entries = {
                new RunnerEntry("RUN100", 100, "Open"),
                new EliteRunnerEntry("ELT200", 200, "Elite", 50),
                new RelayTeamEntry("REL300", 300, 4)
        };

        for (RaceEntry entry : entries) {
            entry.announce();
            System.out.println(classifyGeneration(entry));
        }

        System.out.println("Total balance: "
                + getTotalBalanceDue(entries));
    }
}
