package ie.atu.sw.services;

import ie.atu.sw.model.WordDetail;
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
    Map<String, WordDetail> wordDetailIndex = new ConcurrentSkipListMap<>();
    private int lineNumber;

    DictionaryUtils dictionaryUtils;

    public WordIndexerService() throws Exception {
        this.dictionaryUtils = new DictionaryUtils();
        dictionaryUtils.loadForbiddenWords("./google-1000.txt");
        dictionaryUtils.loadDictionary("./smallDict.csv");
    }

    public void indexFile(String filePath, String outputFilePath) throws Exception {
        parseFile(filePath);
        writeIndexToFile(outputFilePath);
    }

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


    /**
     * Strips line of whitespaces & annotations before adding index
     *
     * @param line
     * @throws Exception
     */
    private void processLine(String line) throws Exception {
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
        WordDetail wordDetail;
        if (wordDetailIndex.containsKey(word)) {
            // word already in index get the wordDetail already made
            wordDetail = wordDetailIndex.get(word);
            pageNumbersList = wordDetail.getPageNumbersList();
            int page = calculatePageNumber(lineNumber);
            pageNumbersList.add(page);
        } else {
            // word not in index, make list
            wordDetail = new WordDetail();
            pageNumbersList = new ArrayList<>();
            int page = calculatePageNumber(lineNumber);
            pageNumbersList.add(page);
        }
        wordDetail.setPageNumbersList(pageNumbersList);
        wordDetailIndex.put(word, wordDetail);
        dictionaryUtils.setDictionaryDefinition(word);
        // TODO call out ot dictionary service to match word with definition and make new WordDetail object ?
    }

    /**
     * Calculates page number for index. A page is assumed to be ~40 lines
     * @param lineNumber
     * @return pageNumber
     */
    private int calculatePageNumber(int lineNumber) {
        // as per spec a page is ~40 lines
        double pageNumber = (double) lineNumber / 40;
        return (int) Math.ceil(pageNumber);
    }

    private void writeIndexToFile(String out) throws Exception {
        try (FileWriter fw = new FileWriter(new File(out))) {
            Map<String, WordDetail> temp = new TreeMap<>(wordDetailIndex); //O(n log n)

            for (Map.Entry<String, WordDetail> entry : temp.entrySet()) { //O(n)
                fw.write(entry.getKey() + "\t" + entry.getValue().getPageNumbersList() + "\n");
            }
        }
    }

}
