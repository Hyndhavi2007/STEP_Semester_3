
package WEEK8.class_problems;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

abstract class Employee {
    private final String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getMaximumLeaveDays();
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    public int getMaximumLeaveDays() {
        return 30;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    public int getMaximumLeaveDays() {
        return 10;
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String name) {
        super(name);
    }

    @Override
    public int getMaximumLeaveDays() {
        return 5;
    }
}

class LeaveRequest {
    private final Employee employee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(Employee employee, LocalDate startDate,
                        LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public long getRequestedDays() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public void updateStatus(LeaveStatus newStatus) {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change status: " + status
                    + " request cannot revert to Pending or be reviewed again.");
            return;
        }

        if (newStatus == LeaveStatus.PENDING) {
            System.out.println("Cannot change status: review must approve "
                    + "or reject the request.");
            return;
        }

        status = newStatus;

        System.out.println("Leave request for " + employee.getName()
                + (newStatus == LeaveStatus.APPROVED
                ? " approved." : " rejected."));
        System.out.println("Status: " + status);
    }

    public void displayDetails() {
        System.out.println("Leave request submitted by "
                + employee.getName() + " for " + startDate + " to "
                + endDate + ". Status: " + status + ".");
    }
}

class LeaveManager {
    public LeaveRequest submitRequest(Employee employee,
                                      LocalDate startDate,
                                      LocalDate endDate) {
        if (startDate == null || endDate == null
                || endDate.isBefore(startDate)) {
            System.out.println("Invalid leave dates.");
            return null;
        }

        LeaveRequest request = new LeaveRequest(
                employee, startDate, endDate);

        if (request.getRequestedDays() > employee.getMaximumLeaveDays()) {
            System.out.println("Leave request exceeds the permitted "
                    + "leave days for " + employee.getName() + ".");
            return null;
        }

        request.displayDetails();
        return request;
    }

    public void reviewRequest(LeaveRequest request, LeaveStatus decision) {
        if (request == null) {
            System.out.println("Invalid leave request.");
            return;
        }

        if (decision == LeaveStatus.PENDING) {
            System.out.println("Manager must approve or reject the request.");
            return;
        }

        request.updateStatus(decision);
    }
}

public class Question4 {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("John Doe");
        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest johnRequest = manager.submitRequest(
                john,
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 12));

        manager.reviewRequest(johnRequest, LeaveStatus.APPROVED);

        LeaveRequest janeRequest = manager.submitRequest(
                jane,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5));

        // Attempt to return an approved request to Pending.
        if (johnRequest != null) {
            johnRequest.updateStatus(LeaveStatus.PENDING);
        }
    }
}
