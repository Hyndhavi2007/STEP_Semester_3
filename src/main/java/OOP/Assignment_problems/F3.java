package OOP.Assignment_problems;
public class F3 {

    static class ParkingSlot {
        String slotNo;
        int capacity;
        int occupiedCount;

        ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        void allot(String vehicleNo) {

            if (occupiedCount < capacity) {
                occupiedCount++;

                System.out.println(
                        vehicleNo +
                                " allotted to slot " +
                                slotNo
                );
            }
        }
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {

        for (ParkingSlot slot : slots) {

            if (slot != null &&
                    slot.occupiedCount < slot.capacity) {

                return slot;
            }
        }

        return null;
    }

    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {

        ParkingSlot availableSlot =
                findAvailableSlot(slots);

        if (availableSlot != null) {

            availableSlot.allot(vehicleNo);

        } else {

            System.out.println(
                    "No slots available for " +
                            vehicleNo
            );
        }
    }

    public static void main(String[] args) {

        // Case 1: One slot is available
        ParkingSlot[] slots1 = {
                new ParkingSlot("A1", 4, 3),
                new ParkingSlot("A2", 5, 5)
        };

        safeAllot(slots1, "TN09AB1234");

        System.out.println();

        // Case 2: Every slot is full
        ParkingSlot[] slots2 = {
                new ParkingSlot("A1", 4, 4),
                new ParkingSlot("A2", 5, 5)
        };

        safeAllot(slots2, "TN09AB1234");
    }
}
