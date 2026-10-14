 package WEEK8.assigment_problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Policy interface for different student types
interface CreditPolicy {
    int getCreditLimit();

    String getTypeName();
}

class RegularPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 24;
    }

    public String getTypeName() {
        return "Regular";
    }
}

class HonorsPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 28;
    }

    public String getTypeName() {
        return "Honors";
    }
}

class ExchangePolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 20;
    }

    public String getTypeName() {
        return "Exchange";
    }
}

// Student class uses a pluggable credit policy
class ElectiveStudent {
    private final String name;
    private final CreditPolicy policy;
    private int currentCredits;

    public ElectiveStudent(String name, CreditPolicy policy,
                           int currentCredits) {
        this.name = name;
        this.policy = policy;
        this.currentCredits = currentCredits;
    }

    public String getName() {
        return name;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getCreditLimit() {
        return policy.getCreditLimit();
    }

    public String getTypeName() {
        return policy.getTypeName();
    }

    public boolean canAddCredits(int credits) {
        return currentCredits + credits <= getCreditLimit();
    }

    public void addCredits(int credits) {
        currentCredits += credits;
    }

    public void removeCredits(int credits) {
        currentCredits -= credits;
    }
}

// Represents an enrollment
class EnrollmentRecord {
    private final ElectiveStudent student;
    private final Elective elective;

    public EnrollmentRecord(ElectiveStudent student, Elective elective) {
        this.student = student;
        this.elective = elective;
    }

    public ElectiveStudent getStudent() {
        return student;
    }

    public Elective getElective() {
        return elective;
    }
}

// Elective manages its seats and private FIFO waitlist
class Elective {
    private final String name;
    private final int credits;
    private final int capacity;

    private final List<EnrollmentRecord> enrolled = new ArrayList<>();
    private final Queue<ElectiveStudent> waitlist = new LinkedList<>();

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isFull() {
        return enrolled.size() >= capacity;
    }

    public boolean isEnrolled(ElectiveStudent student) {
        for (EnrollmentRecord record : enrolled) {
            if (record.getStudent() == student) {
                return true;
            }
        }
        return false;
    }

    public boolean isWaiting(ElectiveStudent student) {
        return waitlist.contains(student);
    }

    public boolean hasStudent(ElectiveStudent student) {
        return isEnrolled(student) || isWaiting(student);
    }

    public boolean addEnrollment(ElectiveStudent student) {
        if (isFull() || hasStudent(student)) {
            return false;
        }

        enrolled.add(new EnrollmentRecord(student, this));
        student.addCredits(credits);
        return true;
    }

    public boolean removeEnrollment(ElectiveStudent student) {
        for (int i = 0; i < enrolled.size(); i++) {
            if (enrolled.get(i).getStudent() == student) {
                enrolled.remove(i);
                student.removeCredits(credits);
                return true;
            }
        }
        return false;
    }

    public void addToWaitlist(ElectiveStudent student) {
        waitlist.offer(student);
    }

    public ElectiveStudent pollWaitlist() {
        return waitlist.poll();
    }

    public boolean isWaitlistEmpty() {
        return waitlist.isEmpty();
    }

    public int getWaitlistPosition(ElectiveStudent student) {
        int position = 1;

        for (ElectiveStudent waitingStudent : waitlist) {
            if (waitingStudent == student) {
                return position;
            }
            position++;
        }

        return -1;
    }
}

// Handles enrollment, dropping and waitlist promotion
class EnrollmentService {

    public void enroll(ElectiveStudent student, Elective elective) {
        // Prevent duplicate enrollment or waitlisting
        if (elective.hasStudent(student)) {
            System.out.println("Enrollment failed: " + student.getName()
                    + " is already enrolled or waitlisted.");
            return;
        }

        // Credit limit is checked BEFORE seat availability
        if (!student.canAddCredits(elective.getCredits())) {
            System.out.println("Enrollment failed: " + student.getName()
                    + " would exceed the " + student.getTypeName()
                    + " credit limit ("
                    + (student.getCurrentCredits() + elective.getCredits())
                    + "/" + student.getCreditLimit() + ").");
            return;
        }

        // If full, add student to the FIFO waitlist
        if (elective.isFull()) {
            System.out.println(elective.getName() + " is full.");
            elective.addToWaitlist(student);

            System.out.println(student.getName()
                    + " added to waitlist (position "
                    + elective.getWaitlistPosition(student) + ").");
            return;
        }

        elective.addEnrollment(student);

        System.out.println(student.getName() + " enrolled in "
                + elective.getName() + " (credits: "
                + student.getCurrentCredits() + "/"
                + student.getCreditLimit() + ").");
    }

    public void drop(ElectiveStudent student, Elective elective) {
        if (!elective.removeEnrollment(student)) {
            System.out.println("Drop failed: " + student.getName()
                    + " is not enrolled in " + elective.getName() + ".");
            return;
        }

        System.out.println(student.getName() + " dropped "
                + elective.getName() + " (credits: "
                + student.getCurrentCredits() + "/"
                + student.getCreditLimit() + ").");

        promoteWaitlistedStudents(elective);
    }

    private void promoteWaitlistedStudents(Elective elective) {
        while (!elective.isFull() && !elective.isWaitlistEmpty()) {
            ElectiveStudent candidate = elective.pollWaitlist();

            // Recheck the credit limit at promotion time
            if (!candidate.canAddCredits(elective.getCredits())) {
                System.out.println("Promotion skipped: "
                        + candidate.getName()
                        + " would exceed the "
                        + candidate.getTypeName()
                        + " credit limit.");
                continue;
            }

            elective.addEnrollment(candidate);

            System.out.println(candidate.getName()
                    + " promoted from waitlist and enrolled in "
                    + elective.getName() + " (credits: "
                    + candidate.getCurrentCredits() + "/"
                    + candidate.getCreditLimit() + ").");
        }
    }
}

// Main class
public class Question4 {
    public static void main(String[] args) {
        Elective cloudComputing =
                new Elective("Cloud Computing", 4, 2);

        ElectiveStudent asha =
                new ElectiveStudent("Asha", new RegularPolicy(), 20);

        ElectiveStudent ravi =
                new ElectiveStudent("Ravi", new HonorsPolicy(), 22);

        ElectiveStudent neha =
                new ElectiveStudent("Neha", new ExchangePolicy(), 12);

        ElectiveStudent kiran =
                new ElectiveStudent("Kiran", new RegularPolicy(), 22);

        EnrollmentService service = new EnrollmentService();

        service.enroll(asha, cloudComputing);
        service.enroll(ravi, cloudComputing);
        service.enroll(neha, cloudComputing);
        service.enroll(kiran, cloudComputing);

        service.drop(asha, cloudComputing);
    }
}
