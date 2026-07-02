import java.util.Scanner;

public class main {
    public static Scanner input = new Scanner(System.in);

    /**
     * main method, runs the main game loop and prompts to restart game after the game ends
     * @author Shawn Saunders
     * @param args Does nothing... for now!
     */
    public static void main(String[] args) {

        boolean gameIsRunning = true;
        while (gameIsRunning) {
            gameTools.runGame();
            System.out.println("Game over! Would you like to play again? (Y/N)");
            String gameChoice = input.nextLine();
            if (gameChoice.equalsIgnoreCase("Y")) {
                System.out.println("Resetting game!");
            } else if (gameChoice.equalsIgnoreCase("N")) {
                gameIsRunning = false;
            }
        }


    }
}
