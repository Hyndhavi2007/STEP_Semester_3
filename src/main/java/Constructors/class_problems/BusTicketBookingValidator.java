package Constructors.class_problems;
public class BusTicketBookingValidator {

    static class BusTicket {
        private String passengerName;
        private String destination;
        private boolean checkedIn;

        // No no-argument constructor is provided.
        public BusTicket(String passengerName, String destination) {

            if (!isMeaningfulName(passengerName)) {
                throw new IllegalArgumentException(
                        "Invalid passenger name"
                );
            }

            if (!isMeaningfulDestination(destination)) {
                throw new IllegalArgumentException(
                        "Invalid destination"
                );
            }

            this.passengerName = passengerName.trim();
            this.destination = destination.trim();
            this.checkedIn = false;
        }

        private static boolean isMeaningfulName(String name) {

            if (name == null || name.trim().isEmpty()) {
                return false;
            }

            // Name must contain letters and spaces only.
            for (char ch : name.trim().toCharArray()) {
                if (!Character.isLetter(ch) && ch != ' ') {
                    return false;
                }
            }

            return true;
        }

        private static boolean isMeaningfulDestination(String destination) {
            return destination != null &&
                    !destination.trim().isEmpty();
        }

        public void markCheckedIn() {
            if (!checkedIn) {
                checkedIn = true;
            }
        }

        public String getPassengerName() {
            return passengerName;
        }

        public String getDestination() {
            return destination;
        }
    }

    static void processBatch(String[][] rawBookings) {

        BusTicket[] acceptedTickets =
                new BusTicket[rawBookings.length];

        int accepted = 0;
        int rejected = 0;
        int duplicates = 0;

        for (String[] booking : rawBookings) {

            if (booking == null || booking.length < 2) {
                rejected++;
                continue;
            }

            String name = booking[0];
            String destination = booking[1];

            try {
                BusTicket ticket =
                        new BusTicket(name, destination);

                boolean duplicate = false;

                for (int i = 0; i < accepted; i++) {

                    if (acceptedTickets[i]
                            .getPassengerName()
                            .equalsIgnoreCase(ticket.getPassengerName())
                            &&
                            acceptedTickets[i]
                                    .getDestination()
                                    .equalsIgnoreCase(ticket.getDestination())) {

                        duplicate = true;
                        break;
                    }
                }

                if (duplicate) {
                    duplicates++;
                } else {
                    acceptedTickets[accepted++] = ticket;
                }

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println(
                "Valid: " + accepted +
                        " | Rejected: " + rejected +
                        " | Duplicates skipped: " + duplicates
        );
    }

    public static void main(String[] args) {

        String[][] rawBookings = {
                {"Divya", "Chennai"},
                {"", "Bangalore"},
                {"Ravi123", "Pune"},
                {"Divya", "Chennai"},
                {" ", " "}
        };

        processBatch(rawBookings);
    }
}