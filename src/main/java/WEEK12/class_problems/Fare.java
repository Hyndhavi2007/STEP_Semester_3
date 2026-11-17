package WEEK12.class_problems;

import java.util.Scanner;

abstract class Ride {

    double distance;

    Ride(double distance) {
        this.distance = distance;
    }

    abstract double calculateFare();

    abstract String getType();
}

// Auto class
class Auto extends Ride {

    Auto(double distance) {
        super(distance);
    }

    double calculateFare() {
        return 30 + (15 * distance);
    }

    String getType() {
        return "Auto";
    }
}

// Cab class
class Cab extends Ride {

    double surge;

    Cab(double distance, double surge) {
        super(distance);
        this.surge = surge;
    }

    double calculateFare() {
        return (50 + 20 * distance) * surge;
    }

    String getType() {
        return "Cab";
    }
}

// Bike class
class Bike extends Ride {

    int waitingMinutes;

    Bike(double distance, int waitingMinutes) {
        super(distance);
        this.waitingMinutes = waitingMinutes;
    }

    double calculateFare() {
        return 20 + (10 * distance) + (2 * waitingMinutes);
    }

    String getType() {
        return "Bike";
    }
}

class Fare {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of rides:");

        int n = sc.nextInt();

        Ride[] rides = new Ride[n];

        // Read ride details
        for (int i = 0; i < n; i++) {

            String type = sc.next();

            double distance = sc.nextDouble();

            sc.next(); // Consume km

            if (type.equalsIgnoreCase("Auto")) {

                rides[i] = new Auto(distance);

            }

            else if (type.equalsIgnoreCase("Cab")) {

                sc.next(); // Consume surge
                double surge = sc.nextDouble();

                rides[i] = new Cab(distance, surge);

            }

            else if (type.equalsIgnoreCase("Bike")) {

                sc.next(); // Consume waiting
                int waiting = sc.nextInt();

                sc.next(); // Consume min

                rides[i] = new Bike(distance, waiting);
            }
        }

        double grandTotal = 0;

        System.out.println("Ride Fares:");

        // Calculate all fares using one loop
        for (int i = 0; i < n; i++) {

            double fare = rides[i].calculateFare();

            System.out.println(rides[i].getType() + ": " +
                    format(fare));

            grandTotal += fare;
        }

        System.out.println("Total: " + format(grandTotal));

        sc.close();
    }

    static String format(double value) {
        return String.format(java.util.Locale.US, "%.2f", value)
                .replaceAll("\\.?0+$", "");
    }
}
