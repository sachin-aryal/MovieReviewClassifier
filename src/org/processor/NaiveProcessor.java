package org.processor;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import org.constants.MReviewConstants;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * Created by iam on 11/12/16.
 */
public class NaiveProcessor extends DataModel {

    private double prob_positive;
    private double prob_negative;

    public NaiveProcessor(){
        super(MReviewConstants.POSITIVEFILEMODEL, MReviewConstants.NEGATIVEFILEMODEL,MReviewConstants.WORDSFILEMODEL);
    }


    public void loadModel() {
        super.loadModel();
    }

    /*
    *  Create list of word from input line.
    * */
    public void predict(String inputReview){

        TokenizerFactory<Word> tf = PTBTokenizer.factory();;
        List tokenizedLine = tf.getTokenizer(new StringReader(inputReview)).tokenize();
        String[] review_word_list = new String[tokenizedLine.size()];
        for(int i=0;i<tokenizedLine.size();i++){
            review_word_list[i] = tokenizedLine.get(i).toString();
        }
        positiveProbability(review_word_list);
        negativeProbability(review_word_list);

        
    }


    /*
    *  Calculate probability of classifying input line as positive.
    * */
    public void positiveProbability(String words[]){

        double pYforAllX = 1.0;
        for(String word:words){
            pYforAllX *=  probOfWordGivenPositiveTrue(word);
        }
        prob_positive = (probability_positive * pYforAllX);

    }

    /*
    * Calculate probability of classifying input line as negative.
    * */
    public void negativeProbability(String words[]){
        double pYforAllX = 1;
        for(String word:words){
            pYforAllX *=  probOfWordGivenNegativeTrue(word);
        }
        prob_negative = (probability_negative * pYforAllX);
    }


    /*
    *  Calculate probability of input word as positive
    * */
    public double probOfWordGivenPositiveTrue(String word){
        double count = 1;
        for(Map<String, Integer> values:positiveWordMatrix.values()){
            if(values.containsKey(word)){
                count += values.get(word);
            }
        }
        return (count/(double) positiveDenominator);
    }

    public double probOfWordGivenNegativeTrue(String word){
        double count = 1;
        for(Map<String, Integer> values:negativeWordMatrix.values()){
            if(values.containsKey(word)){
                count += values.get(word);
            }
        }
        return (count/(double) negativeDenominator);
    }


    public double getProb_positive() {return prob_positive; }

    public double getProb_negative() {
        return prob_negative;
    }
}
