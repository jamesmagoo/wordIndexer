package indexer;

import java.io.*;
import java.util.*;

public class WordIndexerService {

    Map<String, List<Integer>> wordIndex = new HashMap<>();
    private int count = 0;

    public void indexFile(String filePath, String outputFilePath) throws Exception {
        parseBook(filePath);
        writeIndexToFile(outputFilePath);
    }

//    private void parseBook(String book) throws Exception{ //Imperative
//        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(book)))){
//            String text;
//            while ((text = br.readLine()) != null) {
//                process(text);
//            }
//        }
//    }

    private void parseBook(String book) throws Exception{
        System.out.println("Parsing book: " + book);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(book)))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void processLine(String line) throws Exception{
        // split line into words
        String regex = "\s"; // split on whitespace
        String[] words = line.split(regex);

        for (String word : words) {
            System.out.println(word);
            // Clean up word removing non-alphabetic characters
            String regex2 = "[^a-zA-Z]";
            String wordStripped = word.replaceAll(regex2,"");
            // Add word to index
            addWordToIndex(wordStripped);
        }
    }

    private void addWordToIndex(String word) throws Exception{
        // TODO: this should be a list of pages..
        List<Integer> pagesNumbersList;
        List<Integer> wordList ;
        if(wordIndex.containsKey(word)) {
            // word already in index get the list already made
            wordList = wordIndex.get(word);
        } else {
            // word not in index, make list and add word
            wordList = new ArrayList<>();
        }
        wordList.add(count);
        wordIndex.put(word, wordList);
        count++;
    }

    private void writeIndexToFile(String out) throws Exception{
        try(FileWriter fw = new FileWriter(new File(out))){
            Map<String, List<Integer>> temp = new TreeMap<>(wordIndex); //O(n log n)

            for (Map.Entry<String, List<Integer>> entry : temp.entrySet()) { //O(n)
                fw.write(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
        }
    }

}
