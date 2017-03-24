package org.processor;

import java.util.Scanner;

/**
 * Created by iam on 3/24/17.
 */
public class BuildApp {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Scanner intScanner = new Scanner(System.in);
        Scanner stringScanner = new Scanner(System.in);
        int userChoice = 0;
        boolean userContinue = true;
        while (userContinue) {
            System.out.println("Enter Train --> 1\n Test -->2\n Demo -->3");
            userChoice = intScanner.nextInt();
            NaiveBayes naiveBayes = null;
            switch (userChoice) {
                case 1:
                    naiveBayes = NaiveBayesFactory.getInstance("train");
                    naiveBayes.train();
                    break;
                case 2:
                    naiveBayes = NaiveBayesFactory.getInstance("test");
                    naiveBayes.test();
                    break;
                case 3:
                    naiveBayes = NaiveBayesFactory.getInstance("demo");
                    naiveBayes.predict();
                    break;
                default:
                    System.out.println("Enter valid number");
                    break;
            }
            System.out.println("You want to continue YES OR NO :");
            String userWish = stringScanner.nextLine();
            userContinue = userWish.equalsIgnoreCase("YES");
        }
    }
}
