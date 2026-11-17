package WEEK12.class_problems;

import java.util.*;

class Job {

    String name;
    int remainingTime;
    int finishTime;

    Job(String name, int time) {
        this.name = name;
        this.remainingTime = time;
    }
}

class LabScheduler {

    Queue<Job> queue = new LinkedList<>();

    int quantum;
    int currentTime = 0;

    LabScheduler(int quantum) {
        this.quantum = quantum;
    }

    void addJob(String name, int time) {

        Job job = new Job(name, time);

        queue.add(job);
    }

    void schedule() {

        ArrayList<String> execution = new ArrayList<>();

        ArrayList<Job> finished = new ArrayList<>();

        while (!queue.isEmpty()) {

            // Remove first job
            Job job = queue.poll();

            int start = currentTime;

            // Execute job
            int runTime = Math.min(quantum,
                    job.remainingTime);

            currentTime += runTime;

            job.remainingTime -= runTime;

            int end = currentTime;

            // Store execution details
            execution.add(job.name +
                    "(" + start + "-" + end + ")");

            // Check whether job is finished
            if (job.remainingTime > 0) {

                // Add unfinished job to back
                queue.add(job);

            } else {

                // Record finishing time
                job.finishTime = currentTime;

                finished.add(job);
            }
        }

        // Print time slices
        for (int i = 0; i < execution.size(); i++) {

            if (i > 0) {
                System.out.print(" ");
            }

            System.out.print(execution.get(i));
        }

        System.out.println();

        // Print finishing times
        System.out.println("finish");

        for (Job job : finished) {

            System.out.println(job.name + " " +
                    job.finishTime);
        }
    }
}

class RoundRobin {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter quantum:");

        int quantum = sc.nextInt();

        System.out.println("Enter number of jobs:");

        int n = sc.nextInt();

        LabScheduler scheduler =
                new LabScheduler(quantum);

        // Read jobs
        for (int i = 0; i < n; i++) {

            String name = sc.next();

            int time = sc.nextInt();

            scheduler.addJob(name, time);
        }

        System.out.println("Schedule:");

        scheduler.schedule();

        sc.close();
    }
}

