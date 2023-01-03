package ie.atu.sw.services;

import ie.atu.sw.model.WordDetail;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Services primary function is to index words, as follows:
 * <ol>
 *     <li>Parses the provided file extending the <code>MainThreadParser.java</code> class using it's method <code>parseFile(String filePath, Consumer processor)</code> method.</li>
 *     <li>Processes the file by stripping whitespace and annotations from each word in a line in `processLine(String line)` method.</li>
 *     <li>The word is then passed to `addWordToIndex(String word)` which contains all the logic to either add to an index or make a new entry to the `wordDetailIndex` Map which contains the index state.</li>
 *     <li>This method uses the `DictionaryService` class methods to pull information on forbidden words and dictionary definitions. </li>
 *     <li>Extra features are also implemented here - `processTop20Words()` and `searchWordIndex()` methods.</li>
 * </ol>
 */
public class WordIndexerService extends MainThreadParser {

    private Map<String, WordDetail> wordDetailIndex = new ConcurrentSkipListMap<>();
    private List<String> topTwentyWords = new ArrayList<>();
    private int lineNumber;
    private String inputFilePath;
    private String outputFilePath;
    private DictionaryService dictionaryService;

    public WordIndexerService() throws Exception {
        dictionaryService = new DictionaryService();
    }

    /**
     * Entry method to index the txt file provided.
     *
     * <p>'Big O' Time Complexity -> O log(n)</p>
     *
     * @return -  boolean success or fail
     * @throws - Exception
     */
    public boolean indexFile() throws Exception {
        if ((inputFilePath != null) && (outputFilePath != null)) {
            super.parseFile(inputFilePath, this::processLine);
            writeIndexToFile();
            return true;
        } else {
            System.out.println("ERROR: Input/Output Directory Paths");
            return false;
        }
    }

    /**
     * <p>Strips line of whitespaces & annotations before adding index</p>
     *
     * <p>'Big O' Time Complexity -> O(n)</p>
     *
     * @param line each line from the parsed file
     * @throws Exception
     */
    private void processLine(String line) {
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
     * <p>Checks if word is already indexed & not forbidden. If not ,adds it to list and gets a
     * dictionary definition. If word is already indexed, adds page to the page index list.</p>
     *
     * <p>'Big O' Time Complexity -> O log(n)</p>
     *
     * @param word each word from the parsed line
     * @throws Exception
     */
    private void addWordToIndex(String word) {
        List<Integer> pageNumbersList;
        if (dictionaryService.getForbiddenWords().contains(word)) {
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
            // word not in index, make new WordDetail object & list of pages
            wordDetail = new WordDetail();
            pageNumbersList = new ArrayList<>();
            int page = calculatePageNumber(lineNumber);
            pageNumbersList.add(page);
            dictionaryService.setDictionaryDefinition(word, wordDetail);
        }
        wordDetail.setPageNumbersList(pageNumbersList);
        wordDetail.setWord(word);
        wordDetailIndex.put(word, wordDetail);
    }

    /**
     * <p>Calculates page number for index. A page is assumed to be ~40 lines</p>
     *
     * <p>'Big O' Time Complexity -> O(1)</p>
     *
     * @param lineNumber the line number currently being parsed in the file.
     * @return pageNumber
     */
    private int calculatePageNumber(int lineNumber) {
        // as per spec a page is ~40 lines
        double pageNumber = (double) lineNumber / 40;
        return (int) Math.ceil(pageNumber);
    }

    /**
     * <p>Writes the index to the output file</p>
     *
     * <p>'Big O' Time Complexity -> O log(n)</p>
     *
     * @throws Exception
     */
    private void writeIndexToFile() throws Exception {
        try (FileWriter fw = new FileWriter(outputFilePath); BufferedWriter bw = new BufferedWriter(fw)) {
            Map<String, WordDetail> temp = new TreeMap<>(wordDetailIndex); //O(n log n)
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, WordDetail> entry : temp.entrySet()) {
                sb.append(entry.getKey().toUpperCase()).append("\n");
                sb.append("Page Index: ").append(entry.getValue().getPageNumbersList()).append("\n");
                sb.append("Occurrence Count: ").append(entry.getValue().getPageNumbersList().size()).append("\n");
                sb.append("Word Type: ").append("\n");
                sb.append(entry.getValue().getDictionaryDetail().getWordType());
                sb.append("\n\n").append("Definition(s): ").append("\n");
                List<String> defs = entry.getValue().getDictionaryDetail().getWordDefinitions();
                for (String def : defs) {
                    sb.append(def).append("\n");
                }
                sb.append("--------------------------------------------\n");
            }
            bw.write(sb.toString());
            bw.flush();
        } catch (Exception e) {
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

    /**
     * <strong>EXTRA FEATURE</strong>
     * <p>Calculates the top 20 words in the index, sorting
     *      * by frequency of occurrence.</p>
     *
     * <p>'Big O' Time Complexity -> O nlog(n)</p>
     *
     * @return success or fail - if required files are loaded or not.
     * @throws Exception
     */
    public boolean processTop20Words() throws Exception {
        if(dictionaryService.getForbiddenWords().isEmpty()) return false;
        if (indexFile()) {
            List<WordDetail> values = new ArrayList<>(List.copyOf(wordDetailIndex.values()));
            Collections.sort(values); //O nlog(n)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                sb.append(i+1).append(": ")
                        .append(values.get(i).getWord()).append(", occurs ")
                        .append(values.get(i).getPageNumbersList().size()).append(" times.");
                topTwentyWords.add(i, sb.toString());
                sb = new StringBuilder();
            }
            writeTop20File();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Writes the top 20 words to an output file.
     *
     * <p>'Big O' Time Complexity -> O(n)</p>
     *
     */
    private void writeTop20File() {
        try (FileWriter fw = new FileWriter("./top20.txt"); BufferedWriter bw = new BufferedWriter(fw)) {
            List<String> temp = new ArrayList<>(topTwentyWords);
            StringBuilder sb = new StringBuilder();
            for (String word : temp) {
                sb.append(word).append("\n");
            }
            bw.write(sb.toString());
            bw.flush();
        } catch (Exception e) {
            System.out.println("Caught " + e);
        }
    }

    /**
     * <strong>EXTRA FEATURE</strong>
     * <p>This method searches the index for a specific word and outputs
     * if it is in the file to the console.</p>
     *
     *
     * 'Big O' Time Complexity -> O log(n)
     *
     * @param searchWord the word to be searched for in the index.
     * @return boolean - success or fail of word search
     * @throws Exception
     */
    public boolean searchWordIndex(String searchWord) throws Exception {
        if(dictionaryService.getForbiddenWords().isEmpty()) return false;
        if (indexFile()) {
            if(dictionaryService.getForbiddenWords().contains(searchWord)){
                System.out.println("This is a common word and is not indexed.");
                return true;
            }
            if(wordDetailIndex.containsKey(searchWord.toLowerCase())){
                String foundWord = wordDetailIndex.get(searchWord).getWord();
                List<Integer> pageList = wordDetailIndex.get(searchWord).getPageNumbersList();
                System.out.println(foundWord + " is in the file.\n");
                System.out.println("Occurs on page(s): " + pageList + ".\n");
                System.out.println("Occurrence Count: " + pageList.size() + ".\n");
                return true;
            } else {
                System.out.println(searchWord + " is not in the file.");
                return false;
            }
        } else {
            System.out.println("Set input/output and forbidden words files.");
            return false;
        }
    }
}
