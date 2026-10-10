package WEEK10.class_problems;

public class StudentMarksGrid {
    public static void main(String[] args) {

        String[] students = {"Anu", "Ravi", "Meena"};
        String[] subjects = {"Math", "Sci", "Eng"};

        // -1 represents missing marks
        int[][] marks = {
                {80, 90, 70},
                {60, 85, -1},
                {95, 75, 88}
        };

        // Calculate total marks for each student
        System.out.println("Totals");

        for (int i = 0; i < students.length; i++) {
            int total = 0;

            for (int j = 0; j < subjects.length; j++) {
                if (marks[i][j] != -1) {
                    total += marks[i][j];
                }
            }

            System.out.println(students[i] + " " + total);
        }

        // Find topper for each subject
        System.out.println("\nToppers");

        for (int j = 0; j < subjects.length; j++) {
            int highest = -1;
            String topper = "None";

            for (int i = 0; i < students.length; i++) {
                if (marks[i][j] != -1 && marks[i][j] > highest) {
                    highest = marks[i][j];
                    topper = students[i];
                }
            }

            System.out.println(
                    subjects[j] + " " + topper + " " + highest
            );
        }
    }
}
