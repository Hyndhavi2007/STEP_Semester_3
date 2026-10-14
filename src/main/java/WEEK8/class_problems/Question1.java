
package WEEK8.class_problems;

import java.util.*;

abstract class Question {
    private final int questionNumber;
    private final String questionText;

    public Question(int questionNumber, String questionText) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {
    private final String correctAnswer;

    public MultipleChoiceQuestion(int number, String text, String correctAnswer) {
        super(number, text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return answer != null &&
                correctAnswer.equalsIgnoreCase(answer.trim());
    }
}

class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(int number, String text, boolean correctAnswer) {
        super(number, text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return answer != null &&
                Boolean.toString(correctAnswer).equalsIgnoreCase(answer.trim());
    }
}

class Examination {
    private final String title;
    private final List<Question> questions = new ArrayList<>();

    public Examination(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}

class Attempt {
    private final Student student;
    private final Examination examination;
    private final Map<Integer, String> answers = new HashMap<>();
    private boolean submitted = false;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void answerQuestion(int questionNumber, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers after submission.");
            return;
        }

        for (Question question : examination.getQuestions()) {
            if (question.getQuestionNumber() == questionNumber) {
                answers.put(questionNumber, answer);
                System.out.println("Question " + questionNumber
                        + " answered with '" + answer + "'.");
                return;
            }
        }

        System.out.println("Question " + questionNumber + " not found.");
    }

    public void submit() {
        if (submitted) {
            System.out.println("Attempt has already been submitted.");
            return;
        }

        submitted = true;
        student.recordSubmittedAttempt(examination.getTitle());

        System.out.println("Examination '" + examination.getTitle()
                + "' submitted successfully.");

        int correct = 0;

        for (Question question : examination.getQuestions()) {
            String answer = answers.get(question.getQuestionNumber());

            if (question.evaluate(answer)) {
                correct++;
            }
        }

        System.out.println("Result for '" + examination.getTitle()
                + "' attempt: " + correct + "/"
                + examination.getQuestions().size() + " correct.");
    }

    public boolean isSubmitted() {
        return submitted;
    }
}

class Student {
    private final String name;
    private final Map<String, Attempt> attempts = new HashMap<>();

    public Student(String name) {
        this.name = name;
    }

    public Attempt startExamination(Examination examination) {
        Attempt existing = attempts.get(examination.getTitle());

        if (existing != null && existing.isSubmitted()) {
            System.out.println("A submitted attempt already exists for "
                    + examination.getTitle() + ".");
            return null;
        }

        if (existing != null) {
            System.out.println("An attempt is already in progress.");
            return existing;
        }

        Attempt attempt = new Attempt(this, examination);
        attempts.put(examination.getTitle(), attempt);

        System.out.println("Examination '" + examination.getTitle()
                + "' started by " + name + ".");

        return attempt;
    }

    public void recordSubmittedAttempt(String examinationTitle) {
        // The attempt is already recorded when it is started.
        // This method can be extended to save submission details.
    }
}

public class Question1 {
    public static void main(String[] args) {
        Examination exam = new Examination("Math Quiz");

        exam.addQuestion(new MultipleChoiceQuestion(
                1, "What is 2 + 2?", "A"));
        exam.addQuestion(new MultipleChoiceQuestion(
                2, "What is 3 + 3?", "B"));

        Student student = new Student("Student");

        Attempt attempt = student.startExamination(exam);

        if (attempt != null) {
            attempt.answerQuestion(1, "A");
            attempt.answerQuestion(2, "C");
            attempt.submit();

            attempt.answerQuestion(1, "B");
            student.startExamination(exam);
        }
    }
}
