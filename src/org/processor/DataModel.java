package org.processor;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by iam on 3/23/17.
 */
public class DataModel {

    protected int positive_count;
    protected int negative_count;
    protected double probability_positive = 0.0;
    protected double probability_negative = 0.0;
    protected int totalReview;
    protected int positiveDenominator;
    protected int negativeDenominator;
    private Set<String> uniqueWords;

    protected Map<Integer,Map<String,Integer>> positiveWordMatrix;
    protected Map<Integer,Map<String,Integer>> negativeWordMatrix;

    private String positiveFile;
    private String negativeFile;
    private String wordFile;

    DataModel(String positiveFile,String negativeFile,String wordFile) {
        this.positiveFile = positiveFile;
        this.negativeFile = negativeFile;
        this.wordFile = wordFile;
    }

    public void loadModel(){

        initializeLists();
        fillModelMatrix();

        positive_count  = positiveWordMatrix.size();
        negative_count = negativeWordMatrix.size();
        totalReview = positive_count+negative_count;
        probability_positive = (double)positive_count/(double)totalReview;
        probability_negative = (double)negative_count/(double)totalReview;
        positiveDenominator = positive_count+positiveWordMatrix.size();
        negativeDenominator = negative_count+positiveWordMatrix.size();
    }

    private void initializeLists(){
        positiveWordMatrix = new LinkedHashMap<>();
        negativeWordMatrix = new LinkedHashMap<>();
        uniqueWords = new HashSet<>();
    }

    public void fillModelMatrix(){

        try (Stream<String> stream = Files.lines(Paths.get(wordFile))) {
            stream.forEach(line->{
                String words[] = line.split(",");
                for(int i=0;i<words.length-1;i++){
                    uniqueWords.add(words[i]);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        final int[] positiveDoc = {0};
        try (Stream<String> stream = Files.lines(Paths.get(positiveFile))) {
            stream.forEach(line->{
                Map wordCount = new HashMap();
                String[] wordOccurrence = line.split(",");
                final int[] wordIndex = {0};
                uniqueWords.forEach(word->{
                    try{
                        wordCount.put(word,Integer.parseInt(wordOccurrence[wordIndex[0]++]));
                    }catch (NumberFormatException ex){
                        wordCount.put(word,0);
                    }
                });
                positiveWordMatrix.put(positiveDoc[0]++,wordCount);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> stream = Files.lines(Paths.get(negativeFile))) {
            stream.forEach(line->{
                Map wordCount = new HashMap();
                String[] wordOccurrence = line.split(",");
                final int[] wordIndex = {0};
                uniqueWords.forEach(word->{
                    try{
                        wordCount.put(word,Integer.parseInt(wordOccurrence[wordIndex[0]++]));
                    }catch (NumberFormatException ex){
                        wordCount.put(word,0);
                    }
                });
                negativeWordMatrix.put(positiveDoc[0]++,wordCount);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Positive/Negative word frequency matrix load completed.............");

    }

}
