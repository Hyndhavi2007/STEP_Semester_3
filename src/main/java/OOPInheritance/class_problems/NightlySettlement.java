package OOPInheritance.class_problems;

public class NightlySettlement {
    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (ticket instanceof GroupTicket) {
                groupCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + groupCount + " group | "
                + individualCount + " individual";
    }

    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);

        System.out.println(t1.ticketId);
        System.out.println(EventTicket.getTicketsIssued());
        System.out.println(EventTicket.isValidPromoCode("F123A"));
        System.out.println(EventTicket.isValidPromoCode("F12A"));
        System.out.println(EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue());

        EventTicket[] tickets = {
                new GroupTicket(2000, 5),
                null,
                new EventTicket(500)
        };
        System.out.println(processNightlySettlement(tickets));
    }
}
