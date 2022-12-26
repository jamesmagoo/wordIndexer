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

    Map<String, WordDetail> wordDetailIndex = new ConcurrentSkipListMap<>();
    private int lineNumber;
    private String inputFilePath;
    private String outputFilePath;

    DictionaryUtils dictionaryUtils;

    public WordIndexerService() throws Exception {
        this.dictionaryUtils = new DictionaryUtils();
    }

    public void indexFile() throws Exception {
        parseFile();
        writeIndexToFile();
    }

    private void parseFile() throws Exception {
        if(inputFilePath == null) return;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)))) {
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
        String regex = "\s"; // split on whitespace
        String[] words = line.split(regex);

        for (String word : words) {
            // Clean up word removing non-alphabetic characters
            String regex2 = "[^a-zA-Z]";
            String wordStripped = word.replaceAll(regex2, "");
            if (!wordStripped.isEmpty()) {
                addWordToIndex(wordStripped.toLowerCase());
            }
        }
    }

    /**
     * Checks if word is already indexed & not forbidden. If not ,adds it to list and gets a
     * dictionary definition. If it does, adds to page index list.
     * @param word
     * @throws Exception
     */
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
            dictionaryUtils.setDictionaryDefinition(word, wordDetail);
        }
        wordDetail.setPageNumbersList(pageNumbersList);
        wordDetailIndex.put(word, wordDetail);
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


    // TODO : use stringbuilder here

    /**
     * Writes the index to the output file
     * @throws Exception
     */
    private void writeIndexToFile() throws Exception {
        try (FileWriter fw = new FileWriter(new File(this.outputFilePath))) {
            Map<String, WordDetail> temp = new TreeMap<>(wordDetailIndex); //O(n log n)
            for (Map.Entry<String, WordDetail> entry : temp.entrySet()) { //O(n)
                fw.write(entry.getKey().toUpperCase() + "\n"
                        + "Page Index: "+ entry.getValue().getPageNumbersList() + "\n"
                        + "Occurrence Count: "+ entry.getValue().getPageNumbersList().size() + "\n"
                        + entry.getValue().getDictionaryDetail().getWordType() + "\n");
                // Print definitions list
                List<String> defs = entry.getValue().getDictionaryDetail().getWordDefinitions();
                for (String def: defs) {
                    fw.write(def + "\n");
                }
                fw.write("--------------------------------------------\n");
            }
        } catch (Exception e){
            System.out.println("Caught " + e);
        }
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

}
