package org.test;

import org.constants.MReviewConstants;
import org.processor.AbstractNaive;
import org.processor.NaiveProcessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by iam on 3/22/17.
 */
public class NaiveBayesTest extends AbstractNaive{

    @Override
    public void test() {

        NaiveProcessor naiveProcessor = new NaiveProcessor();
        naiveProcessor.loadModel();

        final double[] positiveProbability = {0.0};
        final double[] negativeProbability = { 0.0 };

        final int[] TP = {0};
        final int[] FP = {0};
        final int[] TN = {0};
        final int[] FN = {0};

        int totalTestData = (MReviewConstants.MAXLINES/100)*70;
        System.out.println("Positive Test File....................");
        try (Stream<String> stream = Files.lines(Paths.get(MReviewConstants.POSITIVEFILETEST)).limit(totalTestData)) { // Test Data for Positive Class.
            stream.forEach(line->{
                naiveProcessor.predict(line.replaceAll("[^A-Za-z0-9 ]", ""));
                positiveProbability[0] = naiveProcessor.getProb_positive();
                negativeProbability[0] = naiveProcessor.getProb_negative();
                if(positiveProbability[0] > negativeProbability[0]){
                    TP[0]++;
                }else if(positiveProbability[0] < negativeProbability[0]){
                    FN[0]++;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Negative Test File....................");
        try (Stream<String> stream = Files.lines(Paths.get(MReviewConstants.NEGATIVEFILETEST)).limit(totalTestData)) {  // Test Data for Positive Class.
            stream.forEach(line->{
                naiveProcessor.predict(line.replaceAll("[^A-Za-z0-9 ]", ""));
                positiveProbability[0] = naiveProcessor.getProb_positive();
                negativeProbability[0] = naiveProcessor.getProb_negative();
                if(positiveProbability[0]>negativeProbability[0]){
                    FP[0]++;
                }else if(positiveProbability[0]<negativeProbability[0]){
                    TN[0]++;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        double accuracy = (TP[0] + TN[0]) / (double)(TP[0] + TN[0] + FP[0] + FN[0]);
        System.out.println("Accuracy :"+accuracy);

    }
}
