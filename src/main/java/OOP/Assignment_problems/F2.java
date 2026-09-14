package OOP.Assignment_problems;
public class F2 {

    // Existing tested class
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

    // ManagerEmployee extends Employee without modifying Employee
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

    // InternEmployee also extends Employee
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

    public static void main(String[] args) {

        Employee plain =
                new Employee("E101", "Arun", 40000);

        ManagerEmployee manager =
                new ManagerEmployee("E102", "Divya", 70000, 8000);

        InternEmployee intern =
                new InternEmployee("E103", "Meera", 12000, 10000);

        Employee[] employees = {
                plain,
                manager,
                intern
        };

        for (Employee employee : employees) {

            if (employee instanceof ManagerEmployee) {

                ManagerEmployee managerEmployee =
                        (ManagerEmployee) employee;

                System.out.println(
                        "Manager effective pay: Rs " +
                                managerEmployee.effectiveSalary()
                );

            } else if (employee instanceof InternEmployee) {

                InternEmployee internEmployee =
                        (InternEmployee) employee;

                System.out.println(
                        "Intern effective pay: Rs " +
                                internEmployee.effectiveSalary()
                );

            } else {

                System.out.println(
                        "Plain employee pay: Rs " +
                                employee.getSalary()
                );
            }
        }
    }
}