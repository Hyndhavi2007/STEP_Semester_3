
package WEEK8.class_problems;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class Room {
    private final String roomName;
    private final double pricePerNight;
    private final List<Reservation> reservations = new ArrayList<>();

    public Room(String roomName, double pricePerNight) {
        this.roomName = roomName;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomName() {
        return roomName;
    }

    public abstract double calculatePrice(long nights);

    public boolean isAvailable(LocalDate start, LocalDate end) {
        for (Reservation reservation : reservations) {
            if (reservation.isActive()
                    && start.isBefore(reservation.getEndDate())
                    && end.isAfter(reservation.getStartDate())) {
                return false;
            }
        }
        return true;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}

class StandardRoom extends Room {
    public StandardRoom(String name) {
        super(name, 150.0);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 150.0;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String name) {
        super(name, 200.0);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 200.0;
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(String name) {
        super(name, 350.0);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 350.0;
    }
}

class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Reservation {
    private final Room room;
    private final Customer customer;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDate cancellationDeadline;
    private final double totalPrice;
    private boolean active = true;

    public Reservation(Room room, Customer customer,
                       LocalDate startDate, LocalDate endDate,
                       LocalDate cancellationDeadline) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline = cancellationDeadline;

        long nights = ChronoUnit.DAYS.between(startDate, endDate);
        this.totalPrice = room.calculatePrice(nights);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public Room getRoom() {
        return room;
    }

    public void displayDetails() {
        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n",
                room.getRoomName(), startDate, endDate, totalPrice);
    }

    public void cancel() {
        if (!active) {
            System.out.println("Reservation is already cancelled.");
            return;
        }

        if (!LocalDate.now().isBefore(cancellationDeadline)) {
            System.out.println("Cancellation deadline has passed for "
                    + room.getRoomName() + ".");
            return;
        }

        active = false;
        System.out.println("Reservation for " + room.getRoomName()
                + " cancelled successfully.");
    }
}

class BookingManager {
    private final List<Reservation> reservations = new ArrayList<>();

    public Reservation bookRoom(Room room, Customer customer,
                                LocalDate start, LocalDate end,
                                LocalDate cancellationDeadline) {
        if (start == null || end == null || cancellationDeadline == null
                || !start.isBefore(end)) {
            System.out.println("Invalid reservation dates.");
            return null;
        }

        if (!room.isAvailable(start, end)) {
            System.out.println("Booking failed: " + room.getRoomName()
                    + " is not available for " + start + " to " + end + ".");
            return null;
        }

        Reservation reservation = new Reservation(
                room, customer, start, end, cancellationDeadline);

        room.addReservation(reservation);
        reservations.add(reservation);
        reservation.displayDetails();

        return reservation;
    }
}

public class Question3 {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        Customer customer = new Customer("John");

        Room deluxe = new DeluxeRoom("Deluxe Room 101");
        Room standard = new StandardRoom("Standard Room 205");

        LocalDate start1 = LocalDate.of(2026, 12, 1);
        LocalDate end1 = LocalDate.of(2026, 12, 5);

        LocalDate start2 = LocalDate.of(2026, 12, 3);
        LocalDate end2 = LocalDate.of(2026, 12, 7);

        LocalDate deadline = LocalDate.now().plusDays(30);

        Reservation r1 = manager.bookRoom(
                deluxe, customer, start1, end1, deadline);

        manager.bookRoom(
                standard, customer, start2, end2, deadline);

        manager.bookRoom(
                deluxe, customer, start2, end2, deadline);

        if (r1 != null) {
            r1.cancel();
        }
    }
}
