package OOPInheritance.class_problems;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class NightlyAnnouncer {
    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder report = new StringBuilder();

        for (EventTicket ticket : tickets) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;

            try {
                System.setOut(new PrintStream(buffer));
                ticket.printTicket(); // Polymorphic call
            } finally {
                System.setOut(originalOut);
            }

            report.append(buffer.toString());

            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop = (WorkshopTicket) ticket;
                report.append("[Track via downcast: ")
                        .append(workshop.getTrack())
                        .append("] ");
            }
            report.append("| ");
        }

        return report.toString();
    }

    public static void main(String[] args) {
        EventTicket[] tickets = {
                new EventTicket("STU1", 500),
                new WorkshopTicket("STU2", 1200, "AI/ML")
        };
        System.out.println(batchPrint(tickets));
    }
}
