package OOP.Class_Problems;
public class Capstone {

    static class FeeAccount {
        private String regNo;
        private double totalFee;
        private double amountPaid;

        FeeAccount(String regNo, double totalFee, double amountPaid) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        public void pay(double amount) {
            if (amount <= 0) {
                System.out.println(
                        "Payment rejected for " + regNo +
                                ": amount must be positive."
                );
                return;
            }

            amountPaid += amount;
        }

        public double getDue() {
            return totalFee - amountPaid;
        }
    }
    static class HostelFeeAccount extends FeeAccount {

        HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
            super(regNo, totalFee, amountPaid);
        }

        void payInTwoInstallments(double amount) {
            if (amount <= 0) {
                System.out.println(
                        "Payment rejected: amount must be positive."
                );
                return;
            }

            pay(amount / 2);
            pay(amount / 2);
        }
    }


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
                        "Room " + roomNo + " is full."
                );
            }
        }
    }


    static class SrmStudent {

        String name;
        String regNo;
        HostelFeeAccount feeAccount;
        HostelRoom room;

        static int totalStudents = 0;

        SrmStudent(String name, String regNo,
                   HostelFeeAccount feeAccount) {

            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;
            this.room = null;

            totalStudents++;
        }

        String fullStatus() {

            String roomStatus;

            if (room == null) {
                roomStatus = "unallotted";
            } else {
                roomStatus = room.roomNo;
            }

            return name +
                    " | Due: Rs " +
                    feeAccount.getDue() +
                    " | Room: " +
                    roomStatus;
        }
    }

    public static void main(String[] args) {
        HostelFeeAccount raviFee =
                new HostelFeeAccount("RA101", 200000, 0);

        HostelFeeAccount anithaFee =
                new HostelFeeAccount("RA102", 200000, 0);

        HostelFeeAccount karthikFee =
                new HostelFeeAccount("RA103", 200000, 0);
        SrmStudent ravi =
                new SrmStudent("Ravi", "RA101", raviFee);

        SrmStudent anitha =
                new SrmStudent("Anitha", "RA102", anithaFee);

        SrmStudent karthik =
                new SrmStudent("Karthik", "RA103", karthikFee);

        HostelRoom room1 =
                new HostelRoom("C-214", 2, 0);

        HostelRoom room2 =
                new HostelRoom("C-507", 2, 0);
        room1.allot(ravi.name);
        ravi.room = room1;

        room2.allot(anitha.name);
        anitha.room = room2;
        ravi.feeAccount.pay(60000);
        anitha.feeAccount.pay(20000);

        karthik.feeAccount.pay(-5000);

        System.out.println();
        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());

        System.out.println();
        System.out.println(
                "Total students: " +
                        SrmStudent.totalStudents
        );
    }
}