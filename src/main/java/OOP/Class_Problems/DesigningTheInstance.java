package OOP.Class_Problems;

public class DesigningTheInstance {

    static class BrokenSrmStudent {
        static String name;
        static String regNo;
        static int attendance;

        BrokenSrmStudent(String name, String regNo, int attendance) {
            BrokenSrmStudent.name = name;
            BrokenSrmStudent.regNo = regNo;
            BrokenSrmStudent.attendance = attendance;
        }

        void printName() {
            System.out.println(name);
        }
    }


    static class SrmStudent {

        String name;
        String regNo;
        int attendance;
        static String university = "SRM University";
        static int admissionCount = 0;

        SrmStudent(String name, int attendance) {
            this.name = name;
            this.attendance = attendance;

            admissionCount++;

            // Generate registration number using admission count.
            this.regNo = "RA2311003010" +
                    String.format("%02d", admissionCount);
        }

        void printIdCard() {
            System.out.println(
                    name + " | " + regNo
            );
        }

        static void printTotalAdmissions() {
            System.out.println(
                    "Students admitted so far: " +
                            admissionCount
            );
        }
    }

    public static void main(String[] args) {

        System.out.println("Broken version:");

        BrokenSrmStudent student1 =
                new BrokenSrmStudent("Ravi", "RA101", 82);

        BrokenSrmStudent student2 =
                new BrokenSrmStudent("Meera", "RA102", 68);

        student1.printName();
        student2.printName();

        System.out.println();
        System.out.println("Fixed version:");

        SrmStudent s1 = new SrmStudent("Ravi", 82);
        SrmStudent s2 = new SrmStudent("Meera", 68);

        s1.printIdCard();
        s2.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}