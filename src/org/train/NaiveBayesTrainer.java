package org.train;

import org.constants.MReviewConstants;
import org.processor.AbstractNaive;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by iam on 11/12/16.
 */
public class NaiveBayesTrainer extends AbstractNaive{

    @Override
    public void train() {
        try {
            DataDetails dataDetails = new DataDetails(MReviewConstants.POSITIVEFILETRAINING,MReviewConstants.NEGATIVEFILETRAINING);
            dataDetails.buildDataModel();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("I/O Exception.");
        }
    }
}
