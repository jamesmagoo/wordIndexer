package ie.atu.sw.services;

import ie.atu.sw.utils.DictionaryUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class WordIndexerService {

    Map<String, List<Integer>> wordIndex = new ConcurrentSkipListMap<>();
    private int lineNumber;

    DictionaryUtils dictionaryUtils;

    public WordIndexerService() throws Exception {
        this.dictionaryUtils = new DictionaryUtils();
        dictionaryUtils.loadForbiddenWords("./google-1000.txt");
        dictionaryUtils.loadDict("./smallDict.csv");
    }

    public void indexFile(String filePath, String outputFilePath) throws Exception {
        parseFile(filePath);
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

    private void parseFile(String file) throws Exception {
        System.out.println("Parsing file: " + file);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
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

    public void parse2(String file) throws Exception {
        Files.lines(Path.of(file))
                .forEach(test -> Thread.startVirtualThread(() -> {
                    try {
                        System.out.println("Parsing file on virtual thread: " + file);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
    }


    private void processLine(String line) throws Exception {
        // split line into words
        lineNumber++;
        System.out.println("Processing line: " + lineNumber);
        String regex = "\s"; // split on whitespace
        String[] words = line.split(regex);

        for (String word : words) {
            // Clean up word removing non-alphabetic characters
            String regex2 = "[^a-zA-Z]";
            String wordStripped = word.replaceAll(regex2, "");
            addWordToIndex(wordStripped.toLowerCase());
        }
    }

    private void addWordToIndex(String word) throws Exception {
        List<Integer> pageNumbersList;
        if(dictionaryUtils.getForbiddenWords().contains(word)){
            return;
        }
        if (wordIndex.containsKey(word)) {
            // word already in index get the list already made
            pageNumbersList = wordIndex.get(word);
        } else {
            // word not in index, make list
            pageNumbersList = new ArrayList<>();
        }
        int page = calculatePageNumber(lineNumber);
        pageNumbersList.add(page);
        wordIndex.put(word, pageNumbersList);
    }

    private int calculatePageNumber(int lineNumber) {
        // as per spec a page is ~40 lines
        double pageNumber = (double) lineNumber / 40;
        return (int) Math.ceil(pageNumber);
    }

    private void writeIndexToFile(String out) throws Exception {
        try (FileWriter fw = new FileWriter(new File(out))) {
            Map<String, List<Integer>> temp = new TreeMap<>(wordIndex); //O(n log n)

            for (Map.Entry<String, List<Integer>> entry : temp.entrySet()) { //O(n)
                fw.write(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
        }
    }

}
