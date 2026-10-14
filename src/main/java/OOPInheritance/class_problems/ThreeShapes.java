package OOPInheritance.class_problems;

public class ThreeShapes {
    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        } else if (ticket instanceof WorkshopTicket) {
            return "Single-level descendant";
        } else {
            return "Base generation";
        }
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;
        for (EventTicket ticket : tickets) {
            total += ticket.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standard = new EventTicket("STU1", 500);
        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premium =
                new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathon =
                new HackathonTicket("STU4", 800, "Byte Force");

        standard.printTicket();
        workshop.printTicket();
        premium.printTicket();
        hackathon.printTicket();

        System.out.println(classifyGeneration(premium));
        System.out.println(classifyGeneration(hackathon));

        EventTicket[] tickets = {standard, workshop, premium, hackathon};
        System.out.println(getTotalBalanceDue(tickets));
    }
}
