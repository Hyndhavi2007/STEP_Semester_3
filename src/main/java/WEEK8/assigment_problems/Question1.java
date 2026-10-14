package WEEK8.assigment_problems;

import java.util.*;

interface ScoringRule {
    double calculate(Score score);
    String getTrackName();
}

class InnovationTrack implements ScoringRule {
    public double calculate(Score s) {
        return s.getIdea() * 0.5
                + s.getExecution() * 0.3
                + s.getPresentation() * 0.2;
    }

    public String getTrackName() {
        return "Innovation";
    }
}

class OpenTrack implements ScoringRule {
    public double calculate(Score s) {
        return (s.getIdea() + s.getExecution()
                + s.getPresentation()) / 3.0;
    }

    public String getTrackName() {
        return "Open";
    }
}

class Student {
    private String name;
    private Team team;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

class Score {
    private int idea;
    private int execution;
    private int presentation;

    public Score(int idea, int execution, int presentation) {
        if (!valid(idea) || !valid(execution) || !valid(presentation)) {
            throw new IllegalArgumentException(
                    "Scores must be between 0 and 10."
            );
        }

        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    private boolean valid(int value) {
        return value >= 0 && value <= 10;
    }

    public int getIdea() {
        return idea;
    }

    public int getExecution() {
        return execution;
    }

    public int getPresentation() {
        return presentation;
    }
}

class Project {
    private String title;
    private Team team;
    private Score score;

    public Project(String title, Team team) {
        this.title = title;
        this.team = team;
    }

    public String getTitle() {
        return title;
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}

class Team {
    private String name;
    private List<Student> members;
    private ScoringRule track;
    private Project project;

    public Team(String name, List<Student> members,
                ScoringRule track) {
        this.name = name;
        this.members = new ArrayList<>(members);
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public List<Student> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public ScoringRule getTrack() {
        return track;
    }

    public Project getProject() {
        return project;
    }

    public boolean submitProject(String title) {
        if (project != null || title == null
                || title.trim().isEmpty()) {
            return false;
        }

        project = new Project(title, this);
        return true;
    }
}

class Judge {
    private String name;

    public Judge(String name) {
        this.name = name;
    }

    public void giveScore(Project project, Score score,
                          Hackathon hackathon) {
        hackathon.recordScore(project, score);
    }
}

class Hackathon {
    enum State {
        OPEN, JUDGING, PUBLISHED
    }

    private String name;
    private List<Team> teams = new ArrayList<>();
    private State state = State.OPEN;

    public Hackathon(String name) {
        this.name = name;
    }

    public boolean registerTeam(Team team) {
        if (state != State.OPEN) {
            System.out.println("Registration failed: Hackathon is closed.");
            return false;
        }

        int size = team.getMembers().size();

        if (size < 2 || size > 4) {
            System.out.println(
                    "Registration failed: A team must have 2 to 4 members."
            );
            return false;
        }

        Set<Student> uniqueMembers =
                new HashSet<>(team.getMembers());

        if (uniqueMembers.size() != size) {
            System.out.println(
                    "Registration failed: Duplicate student in team."
            );
            return false;
        }

        for (Student student : team.getMembers()) {
            if (student.getTeam() != null) {
                System.out.println(
                        "Registration failed: " + student.getName()
                                + " already belongs to a team."
                );
                return false;
            }
        }

        teams.add(team);

        for (Student student : team.getMembers()) {
            student.setTeam(team);
        }

        System.out.println(
                "Team " + team.getName() + " registered ("
                        + size + " members, "
                        + team.getTrack().getTrackName() + " track)."
        );

        return true;
    }

    public boolean submitProject(Team team, String title) {
        if (state != State.OPEN || !teams.contains(team)) {
            System.out.println("Project submission failed.");
            return false;
        }

        if (!team.submitProject(title)) {
            System.out.println(
                    "Project submission failed: Team can submit only one project."
            );
            return false;
        }

        System.out.println(
                "Project '" + title + "' submitted by "
                        + team.getName() + "."
        );

        return true;
    }

    public boolean recordScore(Project project, Score score) {
        if (state == State.PUBLISHED) {
            System.out.println(
                    "Rescore rejected: Results have already been published."
            );
            return false;
        }

        if (project == null || project.getScore() != null
                || !containsProject(project)) {
            System.out.println(
                    "Score rejected: Invalid project or score already recorded."
            );
            return false;
        }

        state = State.JUDGING;
        project.setScore(score);

        System.out.println(
                "Score recorded for '" + project.getTitle() + "'."
        );

        double finalScore =
                project.getTeam().getTrack().calculate(score);

        System.out.printf(Locale.US,
                "Final score: %.2f%n", finalScore);

        return true;
    }

    private boolean containsProject(Project project) {
        for (Team team : teams) {
            if (team.getProject() == project) {
                return true;
            }
        }
        return false;
    }

    public void publishResults() {
        state = State.PUBLISHED;
        System.out.println("Results published.");
    }
}

public class Question1 {
    public static void main(String[] args) {
        Hackathon hackathon =
                new Hackathon("Code Sprint");

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");

        Team byteBusters = new Team(
                "ByteBusters",
                Arrays.asList(asha, ravi, neha),
                new InnovationTrack()
        );

        Team soloCoder = new Team(
                "SoloCoder",
                Arrays.asList(kiran),
                new OpenTrack()
        );

        hackathon.registerTeam(byteBusters);
        hackathon.registerTeam(soloCoder);

        hackathon.submitProject(
                byteBusters, "SmartAttend"
        );

        Judge judge = new Judge("Judge 1");

        judge.giveScore(
                byteBusters.getProject(),
                new Score(8, 7, 9),
                hackathon
        );

        hackathon.publishResults();

        judge.giveScore(
                byteBusters.getProject(),
                new Score(10, 7, 9),
                hackathon
        );
    }
}