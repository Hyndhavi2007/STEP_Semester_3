package Java_String_Concepts.class_problems;
import java.util.Random;
import java.util.Scanner;

public class CollegeCodingArcade {
    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
                (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
                (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            return "Player Wins";
        }

        return "Computer Wins";
    }
    public static String generateComputerMove(Random random) {
        String[] moves = {"Rock", "Paper", "Scissors"};
        return moves[random.nextInt(moves.length)];
    }
    public static String formatMove(String move) {
        move = move.trim().toLowerCase();

        switch (move) {
            case "rock":
                return "Rock";
            case "paper":
                return "Paper";
            case "scissors":
                return "Scissors";
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        final int ROUNDS = 5;

        String[] playerMoves = new String[ROUNDS];
        String[] computerMoves = new String[ROUNDS];
        String[] results = new String[ROUNDS];

        int wins = 0;
        int losses = 0;
        int draws = 0;
        for (int i = 0; i < ROUNDS; i++) {

            String playerMove;

            while (true) {
                System.out.print("Round " + (i + 1)
                        + " - Enter Rock, Paper, or Scissors: ");

                playerMove = formatMove(scanner.nextLine());

                if (!playerMove.isEmpty()) {
                    break;
                }

                System.out.println("Invalid move. Please try again.");
            }

            String computerMove = generateComputerMove(random);
            String result = playRound(playerMove, computerMove);

            playerMoves[i] = playerMove;
            computerMoves[i] = computerMove;
            results[i] = result;
            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }

            System.out.println("Computer: " + computerMove);
            System.out.println("Result: " + result);
            System.out.println();
        }
        double winPercentage = (double) wins / ROUNDS * 100;
        System.out.println("========== FINAL SUMMARY ==========");
        System.out.printf("%-8s %-15s %-17s %-15s%n",
                "Round", "Player Move", "Computer Move", "Result");
        System.out.println("-----------------------------------------------");

        for (int i = 0; i < ROUNDS; i++) {
            System.out.printf("%-8d %-15s %-17s %-15s%n",
                    i + 1,
                    playerMoves[i],
                    computerMoves[i],
                    results[i]);
        }

        System.out.println("-----------------------------------------------");
        System.out.println("Wins   : " + wins);
        System.out.println("Losses : " + losses);
        System.out.println("Draws  : " + draws);
        System.out.printf("Win %%  : %.1f%%%n", winPercentage);

        scanner.close();
    }
}