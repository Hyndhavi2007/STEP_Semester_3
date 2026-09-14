package OOP.Assignment_problems;
public class F5 {

    static class Employee {

        private String empId;
        private String empName;
        private double salary;

        Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        public double getSalary() {
            return salary;
        }
    }

    static class ManagerEmployee extends Employee {

        private double teamBonus;

        ManagerEmployee(String empId, String empName,
                        double salary, double teamBonus) {

            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        double effectiveSalary() {
            return getSalary() + teamBonus;
        }
    }

    static class InternEmployee extends Employee {

        private double stipendCap;

        InternEmployee(String empId, String empName,
                       double salary, double stipendCap) {

            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        double effectiveSalary() {
            return Math.min(getSalary(), stipendCap);
        }
    }

    static class ParkingSlot {

        String slotNo;
        int capacity;
        int occupiedCount;

        ParkingSlot(String slotNo, int capacity,
                    int occupiedCount) {

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

    static class CompanyEmployeeRecord {

        String name;
        String empId;


        Employee employee;

        ParkingSlot slot;

        static int totalRecords = 0;

        CompanyEmployeeRecord(String name,
                              String empId,
                              Employee employee) {

            this.name = name;
            this.empId = empId;
            this.employee = employee;

            // Initially no parking slot is assigned.
            this.slot = null;

            totalRecords++;
        }

        String fullProfile() {

            String pay;
            String slotNumber;

            // Determine effective pay based on employee type.
            if (employee instanceof ManagerEmployee) {

                ManagerEmployee manager =
                        (ManagerEmployee) employee;

                pay = String.valueOf(
                        manager.effectiveSalary()
                );

            } else if (employee instanceof InternEmployee) {

                InternEmployee intern =
                        (InternEmployee) employee;

                pay = String.valueOf(
                        intern.effectiveSalary()
                );

            } else {

                pay = String.valueOf(
                        employee.getSalary()
                );
            }

            // Null-safe parking check.
            if (slot == null) {
                slotNumber = "no parking assigned";
            } else {
                slotNumber = slot.slotNo;
            }

            return name +
                    " | Pay: Rs " +
                    pay +
                    " | Slot: " +
                    slotNumber;
        }
    }
    public static void main(String[] args) {

        // Create employees.

        ManagerEmployee divya =
                new ManagerEmployee(
                        "E101",
                        "Divya",
                        70000,
                        8000
                );

        Employee karan =
                new Employee(
                        "E102",
                        "Karan",
                        40000
                );

        InternEmployee meera =
                new InternEmployee(
                        "E103",
                        "Meera",
                        12000,
                        10000
                );


        // Create employee records.

        CompanyEmployeeRecord record1 =
                new CompanyEmployeeRecord(
                        "Divya",
                        "E101",
                        divya
                );

        CompanyEmployeeRecord record2 =
                new CompanyEmployeeRecord(
                        "Karan",
                        "E102",
                        karan
                );

        CompanyEmployeeRecord record3 =
                new CompanyEmployeeRecord(
                        "Meera",
                        "E103",
                        meera
                );


        ParkingSlot slot1 =
                new ParkingSlot("A1", 1, 0);

        ParkingSlot slot2 =
                new ParkingSlot("A2", 1, 0);

        slot1.allot("TN01AB1111");
        record1.slot = slot1;

        slot2.allot("TN01AB2222");
        record2.slot = slot2;

        System.out.println(record1.fullProfile());
        System.out.println(record2.fullProfile());
        System.out.println(record3.fullProfile());

        System.out.println(
                "Total records: " +
                        CompanyEmployeeRecord.totalRecords
        );
    }
}
