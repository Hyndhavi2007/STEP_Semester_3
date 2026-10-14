
package OOPInheritance.assigment_problems.problem4;

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

    public String announce() {
        return "Race entry: " + bibNumber;
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
    public String announce() {
        return "Runner: " + bibNumber
                + ", Category: " + category;
    }
}

class EliteRunnerEntry extends RunnerEntry {
    public EliteRunnerEntry(String bibNumber, double entryFee,
                            String category) {
        super(bibNumber, entryFee, category);
    }

    @Override
    public String announce() {
        return "Elite runner: " + bibNumber;
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
    public String announce() {
        return "Relay team: " + bibNumber
                + ", Team size: " + teamSize;
    }
}

public class RaceDayAnnouncer {
    public static String announceAll(RaceEntry[] entries) {
        StringBuilder result = new StringBuilder();

        if (entries == null) {
            return "";
        }

        for (RaceEntry entry : entries) {
            if (entry == null) {
                continue;
            }

            if (result.length() > 0) {
                result.append("\n");
            }

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                result.append(relay.announce());
            } else {
                result.append(entry.announce());
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        RaceEntry[] entries = {
                new RunnerEntry("RUN100", 80, "Open"),
                new EliteRunnerEntry("ELT200", 150, "Elite"),
                new RelayTeamEntry("REL300", 300, 4),
                null
        };

        System.out.println(announceAll(entries));
    }
}
