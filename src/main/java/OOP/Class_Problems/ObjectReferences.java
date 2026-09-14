package OOP.Class_Problems;
public class ObjectReferences {

    static class HostelRoom {
        String roomNo;
        int beds;
        int occupied;

        HostelRoom(String roomNo, int beds, int occupied) {
            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        void allot(String name) {
            if (occupied < beds) {
                occupied++;
                System.out.println(
                        name + " allotted to room " + roomNo
                );
            } else {
                System.out.println(
                        "Room " + roomNo + " is already full."
                );
            }
        }
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    static void safeAllot(HostelRoom[] rooms, String studentName) {

        HostelRoom room = findAvailableRoom(rooms);

        // The array contains references to HostelRoom objects, not copies
        // of those objects. Passing the array passes a reference to the same
        // objects, so changes to a room are visible through the original array.

        if (room != null) {
            room.allot(studentName);
        } else {
            System.out.println(
                    "No rooms available for " + studentName
            );
        }
    }

    public static void main(String[] args) {

        HostelRoom[] rooms1 = {
                new HostelRoom("C-214", 3, 2),
                new HostelRoom("C-507", 2, 2)
        };

        System.out.println("First case:");
        safeAllot(rooms1, "Divya");

        System.out.println();

        HostelRoom[] rooms2 = {
                new HostelRoom("C-214", 3, 3),
                new HostelRoom("C-507", 2, 2)
        };

        System.out.println("Second case:");
        safeAllot(rooms2, "Divya");
    }
}
