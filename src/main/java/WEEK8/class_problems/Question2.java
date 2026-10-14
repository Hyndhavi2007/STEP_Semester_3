
package WEEK8.class_problems;

import java.util.*;

abstract class Vehicle {
    private final String vehicleName;
    private Rental activeRental;

    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public abstract double calculateCharge(int days);

    public boolean isAvailable() {
        return activeRental == null;
    }

    public void setActiveRental(Rental rental) {
        activeRental = rental;
    }

    public void clearActiveRental() {
        activeRental = null;
    }
}

class StandardCar extends Vehicle {
    public StandardCar(String name) {
        super(name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50.0;
    }
}

class LuxuryCar extends Vehicle {
    public LuxuryCar(String name) {
        super(name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 100.0;
    }
}

class SUV extends Vehicle {
    public SUV(String name) {
        super(name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 80.0;
    }
}

class RentalCustomer {
    private String customerId;
    private String name;

    public RentalCustomer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private final Customer customer;
    private final Vehicle vehicle;
    private final int days;
    private final double totalCharge;
    private boolean returned;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateCharge(days);
        this.returned = false;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isReturned() {
        return returned;
    }

    public void markReturned() {
        returned = true;
    }

    public void displayDetails() {
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                vehicle.getVehicleName(), days, totalCharge);
    }
}

class RentalService {
    private final List<Rental> rentals = new ArrayList<>();

    public Rental rentVehicle(Customer customer, Vehicle vehicle, int days) {
        if (days <= 0) {
            System.out.println("Rental duration must be positive.");
            return null;
        }

        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getVehicleName()
                    + " is already rented.");
            return null;
        }

        Rental rental = new Rental(customer, vehicle, days);
        vehicle.setActiveRental(rental);
        rentals.add(rental);
        rental.displayDetails();

        return rental;
    }

    public void returnVehicle(Vehicle vehicle) {
        if (vehicle.isAvailable()) {
            System.out.println(vehicle.getVehicleName()
                    + " has no active rental.");
            return;
        }

        for (Rental rental : rentals) {
            if (rental.getVehicle() == vehicle && !rental.isReturned()) {
                rental.markReturned();
                vehicle.clearActiveRental();

                System.out.println(vehicle.getVehicleName()
                        + " returned. Now available.");
                return;
            }
        }
    }
}

public class Question2 {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        Customer customer = new Customer("John");

        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");

        service.rentVehicle(customer, luxury, 3);
        service.rentVehicle(customer, standard, 5);

        service.rentVehicle(customer, luxury, 2);
        service.returnVehicle(luxury);

        service.rentVehicle(customer, luxury, 1);
    }
}
