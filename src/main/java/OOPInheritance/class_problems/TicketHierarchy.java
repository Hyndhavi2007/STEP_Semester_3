package OOPInheritance.class_problems;

import java.util.Arrays;

class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double balanceDue;

    public final String ticketId;
    private static int ticketCounter = 1000;

    private final double[] lateFeeHistory = new double[10];
    private int feeCount = 0;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
        ticketCounter++;
        this.ticketId = "TCK-" + ticketCounter;
    }

    public EventTicket(double basePrice) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        this.attendeeId = null;
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
        ticketCounter++;
        this.ticketId = "TCK-" + ticketCounter;
    }

    public void pay(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid payment");
        }
        balanceDue -= amount;
        if (balanceDue < 0) {
            balanceDue = 0;
        }
    }

    public void pay(double amount, String mode) {
        System.out.println("Payment mode: " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public static int getTicketsIssued() {
        return ticketCounter - 1000;
    }

    protected void applyLateFee(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid late fee");
        }
        if (feeCount >= lateFeeHistory.length) {
            throw new IllegalStateException("Late fee limit reached");
        }

        balanceDue += amount;
        lateFeeHistory[feeCount++] = amount;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, feeCount);
    }

    public void printTicket() {
        System.out.println(
                "Standard Event Ticket | Balance Due: " + balanceDue
        );
    }

    public void printTicket(StringBuilder report) {
        report.append("Standard | Balance: ")
                .append(balanceDue)
                .append(" | ");
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'F') {
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
}

class WorkshopTicket extends EventTicket {
    protected String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public WorkshopTicket(double basePrice) {
        super(basePrice);
        this.track = "";
    }

    public String getTrack() {
        return track;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }

    public void addLateFee(double amount) {
        applyLateFee(amount);
    }

    @Override
    public void printTicket() {
        System.out.println(
                "Workshop Ticket | Track: " + track
                        + " | Balance Due: " + getBalanceDue()
        );
    }

    @Override
    public void printTicket(StringBuilder report) {
        report.append("Workshop | Track: ")
                .append(track)
                .append(" | Balance: ")
                .append(getBalanceDue())
                .append(" ");
    }
}

class PremiumWorkshopTicket extends WorkshopTicket {
    private final double kitFee;

    public PremiumWorkshopTicket(
            String attendeeId, double basePrice, String track, double kitFee) {
        super(attendeeId, basePrice, track);
        if (kitFee < 0) {
            throw new IllegalArgumentException("Invalid kit fee");
        }
        this.kitFee = kitFee;
    }

    @Override
    public void printTicket() {
        System.out.println(
                "Premium Workshop Ticket | Track: " + track
                        + " | Kit Fee: " + kitFee
                        + " | Balance Due: " + getBalanceDue()
        );
    }
}

class HackathonTicket extends EventTicket {
    private final String teamName;

    public HackathonTicket(String attendeeId, double basePrice, String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public void printTicket() {
        System.out.println(
                "Hackathon Ticket | Team: " + teamName
                        + " | Balance Due: " + getBalanceDue()
        );
    }
}

class GroupTicket extends EventTicket {
    private final int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        if (groupSize <= 0) {
            throw new IllegalArgumentException("Group size must be positive");
        }
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}

public class TicketHierarchy {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue());

        String[] ids = {"STU1", "ST1", "STU2", " ", "STU3"};
        System.out.println(EventTicket.registerBatch(ids, 500));
    }
}
