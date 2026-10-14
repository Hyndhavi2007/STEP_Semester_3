package WEEK8.assigment_problems;

import java.util.*;

interface ShippingType {
    double calculateCharge(double weight);
    String getName();
}

class StandardShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 40 + (10 * weight);
    }

    public String getName() {
        return "Standard";
    }
}

class ExpressShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 80 + (15 * weight);
    }

    public String getName() {
        return "Express";
    }
}

class FragileShipping implements ShippingType {
    private final ShippingType standard = new StandardShipping();

    public double calculateCharge(double weight) {
        return standard.calculateCharge(weight) + 50;
    }

    public String getName() {
        return "Fragile";
    }
}

interface NotificationChannel {
    void notifyStatus(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    public void notifyStatus(String parcelId, ParcelStatus status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    public void notifyStatus(String parcelId, ParcelStatus status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

enum ParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}

class ParcelCustomer {
    private String name;

    public ParcelCustomer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Parcel {
    private String parcelId;
    private double weight;
    private ShippingType shippingType;
    private ParcelCustomer customer;
    private ParcelStatus status = ParcelStatus.BOOKED;
    private List<NotificationChannel> channels;

    public Parcel(String parcelId, double weight,
                  ShippingType shippingType,
                  ParcelCustomer customer,
                  List<NotificationChannel> channels) {

        if (weight <= 0) {
            throw new IllegalArgumentException(
                    "Weight must be greater than zero."
            );
        }

        this.parcelId = parcelId;
        this.weight = weight;
        this.shippingType = shippingType;
        this.customer = customer;
        this.channels = new ArrayList<>(channels);
    }

    public String getParcelId() {
        return parcelId;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public double calculateCharge() {
        return shippingType.calculateCharge(weight);
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notifyStatus(parcelId, status);
        }
    }

    private void changeStatus(ParcelStatus newStatus) {
        status = newStatus;
        notifyChannels();
    }

    public boolean updateStatus(ParcelStatus newStatus) {
        if (status == ParcelStatus.DELIVERED
                || status == ParcelStatus.CANCELLED) {
            System.out.println(
                    "Invalid transition: " + status + " → "
                            + newStatus + " is not allowed."
            );
            return false;
        }

        if (newStatus.ordinal() != status.ordinal() + 1
                || newStatus == ParcelStatus.CANCELLED) {
            System.out.println(
                    "Invalid transition: " + status + " → "
                            + newStatus + " is not allowed."
            );
            return false;
        }

        changeStatus(newStatus);
        return true;
    }

    public boolean cancel() {
        if (status != ParcelStatus.BOOKED) {
            System.out.println(
                    "Cancellation failed: " + parcelId
                            + " can be cancelled only while BOOKED."
            );
            return false;
        }

        status = ParcelStatus.CANCELLED;
        notifyChannels();
        System.out.println("Parcel " + parcelId + " cancelled.");
        return true;
    }
}

class ParcelService {
    private Map<String, Parcel> parcels = new HashMap<>();

    public Parcel bookParcel(String parcelId, double weight,
                             ShippingType type,
                             ParcelCustomer customer,
                             List<NotificationChannel> channels) {

        if (parcels.containsKey(parcelId)) {
            throw new IllegalArgumentException(
                    "Parcel ID already exists."
            );
        }

        Parcel parcel = new Parcel(
                parcelId, weight, type, customer, channels
        );

        parcels.put(parcelId, parcel);

        System.out.printf(
                Locale.US,
                "Parcel %s booked (%s, %.0f kg).%n",
                parcelId, type.getName(), weight
        );

        System.out.printf(
                Locale.US,
                "Charge: ₹%.2f%n",
                parcel.calculateCharge()
        );

        // Notify all subscribed channels about initial BOOKED status.
        for (NotificationChannel channel : channels) {
            channel.notifyStatus(parcelId, ParcelStatus.BOOKED);
        }

        return parcel;
    }
}

public class Question2 {
    public static void main(String[] args) {
        ParcelService service = new ParcelService();

        ParcelCustomer customer =
                new ParcelCustomer("Asha");

        List<NotificationChannel> channels =
                Arrays.asList(
                        new SmsChannel(),
                        new EmailChannel()
                );

        Parcel parcel = service.bookParcel(
                "P101",
                2,
                new ExpressShipping(),
                customer,
                channels
        );

        parcel.updateStatus(ParcelStatus.PICKED_UP);

        parcel.cancel();

        parcel.updateStatus(ParcelStatus.IN_TRANSIT);

        // This is invalid because OUT_FOR_DELIVERY must come first.
        parcel.updateStatus(ParcelStatus.DELIVERED);
    }
}