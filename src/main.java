// Author: Shawn Saunders
// Date: 7/6/2026
// Description: Small console game to shoot projectiles based on a power
//              and angle. Objective is to shoot the opposing player.
import java.util.Scanner;

public class main {
    public static Scanner input = new Scanner(System.in);

    /**
     * main method, runs the main game loop and prompts to restart
     * game after the game ends
     * @author Shawn Saunders
     * @param args Does nothing... for now!
     */
    public static void main(String[] args) {

        // declare local variables
        boolean gameIsRunning = true;
        String playerOneName = gameTools.getName();
        String playerTwoName = gameTools.getName();

        // Repeat game loop
        while (gameIsRunning) {
            // Runs the game
            gameTools.runGame(playerOneName, playerTwoName);
            System.out.println("Game over! Would you like to play again? (Y/N)");

            // Input
            String gameChoice = input.nextLine();
            if (gameChoice.equalsIgnoreCase("YES") ||
                    gameChoice.equalsIgnoreCase("Y")) {
                System.out.println("Resetting game!");
            } else if (gameChoice.equalsIgnoreCase("NO") ||
                    gameChoice.equalsIgnoreCase("N")) {
                gameIsRunning = false;
            }
        }
        System.out.println("Game exiting. Goodbye!");


    }
}
