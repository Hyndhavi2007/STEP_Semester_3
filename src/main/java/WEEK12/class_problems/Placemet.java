package WEEK12.class_problems;


import java.util.*;

class Candidate {

    String name;
    int[] scores;
    int total;

    int bestClimb;
    int startTest;
    int endTest;

    Candidate(String name, int[] scores) {

        this.name = name;
        this.scores = scores;

        total = 0;

        for (int score : scores) {
            total += score;
        }

        findBestClimb();
    }

    // Kadane's Algorithm
    void findBestClimb() {

        int currentSum = scores[1] - scores[0];

        int bestSum = currentSum;

        int currentStart = 0;

        int bestStart = 0;
        int bestEnd = 1;

        for (int i = 1; i < 5; i++) {

            int change = scores[i + 1] - scores[i];

            if (currentSum < 0) {

                currentSum = change;
                currentStart = i;

            } else {

                currentSum += change;
            }

            int currentEnd = i + 1;

            // Update best climb
            if (currentSum > bestSum ||
                    (currentSum == bestSum &&
                            (currentStart < bestStart ||
                                    (currentStart == bestStart &&
                                            currentEnd < bestEnd)))) {

                bestSum = currentSum;

                bestStart = currentStart;
                bestEnd = currentEnd;
            }
        }

        bestClimb = bestSum;

        // Convert to 1-based test numbers
        startTest = bestStart + 1;
        endTest = bestEnd + 1;
    }
}

class Placement {

    // Merge Sort
    static void mergeSort(Candidate[] arr,
                          int left,
                          int right) {

        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid);

        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    // Merge two sorted parts
    static void merge(Candidate[] arr,
                      int left,
                      int mid,
                      int right) {

        Candidate[] temp =
                new Candidate[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {

            // >= maintains stable registration order
            if (arr[i].total >= arr[j].total) {

                temp[k++] = arr[i++];

            } else {

                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {

            arr[left + x] = temp[x];
        }
    }

    // Binary Search for cutoff
    static int findCutoff(Candidate[] arr, int cutoff) {

        int low = 0;
        int high = arr.length;

        while (low < high) {

            int mid = low + (high - low) / 2;

            if (arr[mid].total >= cutoff) {

                low = mid + 1;

            } else {

                high = mid;
            }
        }

        return low;
    }

    // Two Pointer technique
    static void findPair(Candidate[] arr, int target) {

        int left = 0;
        int right = arr.length - 1;

        while (left < right) {

            int sum = arr[left].total +
                    arr[right].total;

            if (sum == target) {

                System.out.println(
                        "PAIR " + target + ": " +
                                arr[left].name + " + " +
                                arr[right].name
                );

                return;
            }

            else if (sum > target) {

                left++;

            }

            else {

                right--;
            }
        }

        System.out.println("PAIR " + target + ": no pair");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Candidate[] arr = new Candidate[n];

        // Read candidate details
        for (int i = 0; i < n; i++) {

            String name = sc.next();

            int[] scores = new int[6];

            for (int j = 0; j < 6; j++) {

                scores[j] = sc.nextInt();
            }

            arr[i] = new Candidate(name, scores);
        }

        // Sort candidates by total score
        mergeSort(arr, 0, n - 1);

        // Print shortlist
        System.out.println("Shortlist:");

        for (int i = 0; i < n; i++) {

            Candidate c = arr[i];

            System.out.println(
                    (i + 1) + ". " +
                            c.name + " " +
                            c.total +
                            " | best climb +" + c.bestClimb +
                            " (test " + c.startTest +
                            " to " + c.endTest + ")"
            );
        }

        // Process queries
        while (sc.hasNext()) {

            String query = sc.next();

            if (query.equalsIgnoreCase("CUTOFF")) {

                int cutoff = sc.nextInt();

                int count = findCutoff(arr, cutoff);

                System.out.println(
                        "CUTOFF " + cutoff + ": " +
                                count + " candidates"
                );
            }

            else if (query.equalsIgnoreCase("PAIR")) {

                int target = sc.nextInt();

                findPair(arr, target);
            }
        }

        sc.close();
    }
}

