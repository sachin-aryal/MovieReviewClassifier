package org.processor;

import org.demo.NaiveBayesDemo;
import org.test.NaiveBayesTest;
import org.train.NaiveBayesTrainer;

/**
 * Created by iam on 3/24/17.
 */
public class NaiveBayesFactory {

    public static NaiveBayes getInstance(String type) throws IllegalAccessException, InstantiationException {
        NaiveBayes naiveBayes = null;

        switch (type){
            case "demo":
                naiveBayes = NaiveBayesDemo.class.newInstance();
                break;
            case "test":
                naiveBayes = NaiveBayesTest.class.newInstance();
                break;
            case "train":
                naiveBayes = NaiveBayesTrainer.class.newInstance();
                break;
            default:
                break;
        }
        return naiveBayes;
    }
}
