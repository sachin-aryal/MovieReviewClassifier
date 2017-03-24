package org.demo;

import org.processor.AbstractNaive;
import org.processor.NaiveProcessor;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by iam on 3/23/17.
 */
public class NaiveBayesDemo extends AbstractNaive{

    @Override
    public void predict() {

        boolean userContinue = true;
        NaiveProcessor naiveProcessor = new NaiveProcessor();
        Scanner scanner = new Scanner(System.in);

        naiveProcessor.loadModel();
        while (userContinue) {
            System.out.println("Enter movie review:");
            String review = scanner.nextLine();
            naiveProcessor.predict(review);
            double positiveProbability = naiveProcessor.getProb_positive();
            double negativeProbability = naiveProcessor.getProb_negative();

            if (positiveProbability > negativeProbability) {
                System.out.println("Positive Review........");
            } else if (positiveProbability < negativeProbability) {
                System.out.println("Negative Review...........");
            } else {
                System.out.println("Neutral Review................");
            }

            System.out.println("Want to check another movie review YES OR NO :");
            String userWish = scanner.nextLine();
            userContinue = userWish.equalsIgnoreCase("YES");
        }
    }
}
