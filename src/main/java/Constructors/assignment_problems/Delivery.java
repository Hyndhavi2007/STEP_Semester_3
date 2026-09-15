package Constructors.assignment_problems;
public class Delivery {
    private String orderId;
    private String timeSlot;

    public Delivery(String orderId, String timeSlot) {
        this.orderId = orderId;
        this.timeSlot = timeSlot;
    }

    public Delivery(String orderId) {
        this(orderId, "ASAP");
    }

    public boolean isPeakHour() {
        return timeSlot.equals("12:00-13:00")
                || timeSlot.equals("13:00-14:00")
                || timeSlot.equals("19:00-20:00")
                || timeSlot.equals("20:00-21:00");
    }

    public static void main(String[] args) {
        Delivery slot1 =
                new Delivery("ORD101", "13:00-14:00");

        Delivery slot2 =
                new Delivery("ORD102");

        System.out.println(slot1.isPeakHour());
        System.out.println(slot2.isPeakHour());
    }
}
