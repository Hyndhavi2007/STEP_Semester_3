package WEEK11.assigment_problems;

import java.util.*;

class Fire {

    static int[][] exitDistance(char[][] plan) {

        int rows = plan.length;
        int cols = plan[0].length;

        int[][] distance = new int[rows][cols];

        Queue<int[]> queue = new LinkedList<>();

        // Initialize distance matrix with -1
        for (int i = 0; i < rows; i++) {

            Arrays.fill(distance[i], -1);

        }

        // Add all exits to queue
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                if (plan[i][j] == 'E') {

                    distance[i][j] = 0;

                    queue.add(new int[]{i, j});
                }
            }
        }

        // Four possible directions
        int[] rowDir = {-1, 1, 0, 0};
        int[] colDir = {0, 0, -1, 1};

        // BFS traversal
        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int r = current[0];
            int c = current[1];

            // Check all four directions
            for (int k = 0; k < 4; k++) {

                int newRow = r + rowDir[k];
                int newCol = c + colDir[k];

                // Check grid boundaries
                if (newRow >= 0 && newRow < rows &&
                        newCol >= 0 && newCol < cols) {

                    // Visit only unvisited open rooms
                    if (plan[newRow][newCol] == '.' &&
                            distance[newRow][newCol] == -1) {

                        distance[newRow][newCol] =
                                distance[r][c] + 1;

                        queue.add(new int[]{newRow, newCol});
                    }
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) {

        char[][] plan = {

                {'.', '#', '.', 'E'},

                {'.', '.', '.', '#'},

                {'E', '#', '.', '.'}
        };

        int[][] result = exitDistance(plan);

        System.out.println("Exit Distances:");

        for (int i = 0; i < result.length; i++) {

            for (int j = 0; j < result[0].length; j++) {

                System.out.print(result[i][j] + " ");
            }

            System.out.println();
        }
    }
}

