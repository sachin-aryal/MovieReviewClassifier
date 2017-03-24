package org.train;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import org.constants.MReviewConstants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by iam on 11/12/16.
 */
public class DataDetails {

    private List<String> positiveWordLines;
    private List<String> negativeWordLines;
    private Map<Integer,Map<String,Integer>> positiveWordMatrix;
    private Map<Integer,Map<String,Integer>> negativeWordMatrix;
    private Map<Integer,List<Word>> positiveWordToken;
    private Map<Integer,List<Word>> negativeWordToken;
    private Set<Word> uniqueWords;

    private String positiveFile;
    private String negativeFile;

    DataDetails(String positiveFile,String negativeFile) {
        this.positiveFile = positiveFile;
        this.negativeFile = negativeFile;
    }

    /*
    *  Prepare data that need for prediction.
    * */
    protected void buildDataModel() throws IOException {

        initializeLists();
        setWordLines();
        setTokenizedWord();

        positiveWordToken.values().forEach(uniqueWords::addAll);
        negativeWordToken.values().forEach(uniqueWords::addAll);

        buildFrequencyMatrix();
        writeFrequency();
        System.out.println("Training Completed.");

    }

    /*
    * Initialize required list for application.
    * */
    private void initializeLists(){
        positiveWordLines = new ArrayList<>();
        negativeWordLines  = new ArrayList<>();
        positiveWordMatrix = new LinkedHashMap<>();
        negativeWordMatrix = new LinkedHashMap<>();
        positiveWordToken = new LinkedHashMap<>();
        negativeWordToken = new LinkedHashMap<>();
        uniqueWords = new HashSet<>();
    }

    /*
    *  Read file line by line and add to respective positive and negative list.
    * */
    private void setWordLines() {

        try (Stream<String> stream = Files.lines(Paths.get(positiveFile)).limit(MReviewConstants.MAXLINES)) {
            stream.forEach(line->{
                positiveWordLines.add(line.replaceAll("[^A-Za-z0-9 ]", ""));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> stream = Files.lines(Paths.get(negativeFile)).limit(MReviewConstants.MAXLINES)) {
            stream.forEach(line->{
                negativeWordLines.add(line.replaceAll("[^A-Za-z0-9 ]", ""));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Documents Loaded............");

    }

    /*
    *  Tokenize positive and negative lines.
    *  Add tokenize words to respective positive and negative word list.
    * */
    private void setTokenizedWord(){
        final TokenizerFactory<Word> tf = PTBTokenizer.factory();;

        final int[] idx = {1};
        idx[0] = 1;
        positiveWordLines.forEach(line->{
            List tokenizedLine = tf.getTokenizer(new StringReader(line)).tokenize();
            positiveWordToken.put(idx[0]++, tokenizedLine);
        });

        idx[0] = 1;
        negativeWordLines.forEach(line->{
            List tokenizedLine = tf.getTokenizer(new StringReader(line)).tokenize();
            negativeWordToken.put(idx[0]++, tokenizedLine);
        });

        System.out.println("Lines tokenization completed..................");
    }


    /*
    *  Create a positive and negative word frequency matrix for each line.
    * */
    private void buildFrequencyMatrix(){

        final int[] positiveDocNo = {0};
        positiveWordLines.forEach(line->{
            Map wordCount = new HashMap();
            uniqueWords.forEach(word -> {
                wordCount.put(word.toString(),getWordOccurrence(line,word.toString()));
            });
            positiveWordMatrix.put(positiveDocNo[0]++,wordCount);
        });

        final int[] negativeDocNo = {0};
        negativeWordLines.forEach(line->{
            Map wordCount = new HashMap();
            uniqueWords.forEach(word -> {
                wordCount.put(word.toString(),getWordOccurrence(line,word.toString()));
            });
            negativeWordMatrix.put(negativeDocNo[0]++,wordCount);
        });

        System.out.println("Positive/Negative word frequency matrix completed.............");

    }
    /*
    * Return number of occurrence of word in line.
    * */
    private int getWordOccurrence(String line, String word){
        int i = 0;
        try {
            Pattern p = Pattern.compile(word);
            Matcher m = p.matcher(line);
            while (m.find()) {
                i++;
            }
        }catch (Exception ignore){
            return 0;
        }
        return i;
    }

    /*
    * Write positive and negative word matrix with frequency.
    * */
    private void writeFrequency(){
        Path path = Paths.get(MReviewConstants.POSITIVEFILEMODEL);
        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            for(Map<String, Integer> values:positiveWordMatrix.values()){
                for(Integer wordCount: values.values()){
                    writer.write(wordCount+",");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        path = Paths.get(MReviewConstants.NEGATIVEFILEMODEL);
        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            for(Map<String, Integer> values:negativeWordMatrix.values()){
                for(Integer wordCount: values.values()){
                    writer.write(wordCount+",");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        path = Paths.get(MReviewConstants.WORDSFILEMODEL);
        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            for(Map<String, Integer> values:positiveWordMatrix.values()){
                for(String words: values.keySet()){
                    writer.write(words+",");
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Positive/Negative word frequency matrix saved to file............................");

    }

}
