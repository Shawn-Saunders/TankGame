// Author: Shawn Saunders
// Date: 6/29/2026
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
                if (result > min && result < max)  {
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

    static double getPower (){
        String valueName = "power";
        int min = 0;
        int max = 1000;
        return getDoubleInRange(valueName, min, max);
    }

    static double getAngle (){
        String valueName = "angle";
        int min = 0;
        int max = 180;
        return getDoubleInRange(valueName, min, max);
    }

    static double getImpact (double power, double angle, double playerOneLocation, double playerTwoLocation){
        final double GRAVITY = 9.81;
        final double RADIAN_CONVERSION =  Math.PI / 180;
        double impact = 0;
        int time = 0;
        double distance = 0;
        double height = 0;
        double landingTime = 0;
        double landingDistance = 0;

        for (time = 0; height >= 0 ; time++) {
            // distance traveled
            distance = (power * Math.cos(angle*RADIAN_CONVERSION)) * time;
            // projectile height
            height = (power * Math.sin(angle*RADIAN_CONVERSION) * time) - 0.5 * GRAVITY*time*time;


            // Check if the height of the projectile is more than 2 to make sure I don't post any negative heights
            if (height < 0) {
                landingTime = ((2 * power) * Math.sin(angle*RADIAN_CONVERSION))/ GRAVITY;
                System.out.printf("The projectile hits the ground after %.2fs ", landingTime);
                landingDistance = (power * Math.cos(angle*RADIAN_CONVERSION)) * landingTime;
                System.out.printf("and it will be %.2fm away from the origin.%n", landingDistance);
            }

        }

        return impact;
    }

    static void runGame(){
        double max = 400;
        double min = 0;
        double power = 0;
        double angle = 0;
        double playerOneLocation = min + (Math.random() * (max - min));
        double playerTwoLocation = min + (Math.random() * (max - min));

        power = gameTools.getPower();
        angle = gameTools.getAngle();
    }
}
