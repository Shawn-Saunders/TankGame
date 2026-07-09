// Author: Shawn Saunders
// Date: 7/6/2026
// Description: A set of tools to run the tank game
//              using functions.

import java.util.Random;
import java.util.Scanner;

public class gameTools {

    public static Scanner input = new Scanner(System.in);

    /**
     * getDoubleInRange method, enters a loop to validate the users input is a double before returning the resulting
     * double value
     * @author Shawn Saunders
     * @param valueType Gets the name of the value we are trying to get a double for
     * @param min Gets the minimum range value
     * @param max Gets the maximum range value
     */
    static double getDoubleInRange (String valueType, int min, int max){
        double result = 0;
        boolean isValidating = true;
        while (isValidating) {
            System.out.print("Enter a projectile " + valueType + " (" + min + "-" + max + "): ");
            if (input.hasNextDouble()) {
                result = input.nextDouble();
                if (result >= min && result <= max)  {
                    isValidating = false;
                } else {
                    System.out.println("ERROR: " + valueType + " must be between " + min + " and " + max + "!");
                    input.nextLine();
                }
            } else {
                System.out.println("ERROR: " + valueType + " must be a number!");
                input.nextLine();
            }
        }
        return result;
    }

    /**
     * getPower, gets the power from the user
     * @return checks if the input was in range and a proper double
     */
    static double getPower (){
        String valueName = "power";
        int min = 1;
        int max = 1000;
        return getDoubleInRange(valueName, min, max);
    }

    /**
     * getAngle, gets the angle from the user and checks if it is a double in the correct range
     * @return returns the validated double
     */
    static double getAngle (){
        String valueName = "angle";
        int min = 0;
        int max = 180;
        return getDoubleInRange(valueName, min, max);
    }

    static String getName (){
        String name = "";
        boolean isValidating = true;
        while (isValidating) {
            System.out.print("Enter player name: ");
            name = input.nextLine().trim();
            if (!name.isEmpty()) {
                isValidating = false;
            } else {
                System.out.println("ERROR: player name cannot be empty!");
            }
        }
        return name;
    }

    /**
     * getImpact, preforms the math required to find the impact area
     * @param shooterLocation the location of the one firing a projectile
     * @param enemyLocation the location of the target the shooter is trying to hit
     * @return returns the location that was hit
     */
    static double getImpact (double shooterLocation, double enemyLocation){
        final double GRAVITY = 9.81;
        final double RADIAN_CONVERSION =  Math.PI / 180;
        double impact = 0;
        double height = 0;
        double landingTime;
        double landingDistance;
        double distanceFromTarget;

        double power = getPower();
        double angle = getAngle();

        for (int time = 0; height >= 0 ; time++) {
            // projectile height
            height = (power * Math.sin(angle*RADIAN_CONVERSION) * time) - 0.5 * GRAVITY*time*time;

            // Check if the height of the projectile is more than 2 to make sure I don't post any negative heights
            if (height < 0) {
                landingTime = ((2 * power) * Math.sin(angle*RADIAN_CONVERSION))/ GRAVITY;
                landingDistance = (power * Math.cos(angle*RADIAN_CONVERSION)) * landingTime + shooterLocation;
                impact = landingDistance;
                distanceFromTarget = Math.abs(landingDistance - enemyLocation);
                System.out.printf("Your shot landed %.2fm away from your target!%n", distanceFromTarget);
            }

        }

        return impact;
    }

    /**
     * hasImpacted checks if the location the projectile hit is the enemies position
     * @param impact the location of the projectile when it impacted the ground
     * @param playerLocation the location of the enemy
     * @return whether the location was the same as the opponent
     */
    static boolean hasImpacted (double impact, double playerLocation) {
        final double HIT_RADIUS = 1.0;
        return Math.abs(impact - playerLocation) <= HIT_RADIUS;
    }

    static Random random = new Random();
    /**
     * runGame, main method to run each round of the game
     * @param playerOneName the name of the first player
     * @param playerTwoName the name of the second player
     */
    static void runGame(String playerOneName, String playerTwoName) {
        final double MAX = 400;
        final double MIN = 0;
        boolean gameOver = false;
        double playerOneLocation = random.nextDouble(MIN,MAX);
        double playerTwoLocation = random.nextDouble(MIN,MAX);
        double impact;
        int turn = 0;

        while (!gameOver) {
            if (turn % 2 == 0) {
                // Debug to tell you the exact power and angle to hit the opponent
                // System.out.println(debugSolvePower(playerOneLocation, playerTwoLocation));

                System.out.println("Player " + playerOneName + " it's your turn.");
                impact = getImpact(playerOneLocation, playerTwoLocation);

                if (hasImpacted(impact, playerTwoLocation)) {
                    gameOver = true;
                }
            } else {
                // Debug to tell you the exact power and angle to hit the opponent
                // System.out.println(debugSolvePower(playerTwoLocation, playerOneLocation));

                System.out.println("Player " + playerTwoName + " it's your turn.");
                impact = getImpact(playerTwoLocation, playerOneLocation);

                if (hasImpacted(impact, playerOneLocation)) {
                    gameOver = true;
                }
            }
            turn++;
        }

        // Trinary operator (as discussed to avoid an if statement for turn % 2 == 1)
        System.out.println(turn % 2 == 1 ? playerOneName : playerTwoName + " wins!");
    }

    /**
     * DEBUG ONLY: calculates the power/angle needed to land exactly on target,
     * accounting for whether the target is left or right of the shooter
     * @param shooterLocation location of the shooter
     * @param targetLocation location to hit
     * @return the power value that should land on target at the printed angle
     */
    static double debugSolvePower(double shooterLocation, double targetLocation) {
        final double GRAVITY = 9.81;
        final double RADIAN_CONVERSION = Math.PI / 180;
        double distance = Math.abs(targetLocation - shooterLocation);

        // target to the right needs angle < 90, target to the left needs angle > 90
        double angle = (targetLocation >= shooterLocation) ? 45.0 : 135.0;

        double power = Math.sqrt((distance * GRAVITY) / Math.abs(Math.sin(2 * angle * RADIAN_CONVERSION)));
        System.out.printf("DEBUG: use power=%.2f angle=%.0f to hit target at distance %.2f%n", power, angle, distance);
        return power;
    }
}
