package ie.atu.sw.services;

import ie.atu.sw.model.WordDetail;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class WordIndexerService extends MainThreadParser {

    Map<String, WordDetail> wordDetailIndex = new ConcurrentSkipListMap<>();
    private int lineNumber;
    private String inputFilePath;
    private String outputFilePath;

    DictionaryService dictionaryService;

    public WordIndexerService() throws Exception {
        this.dictionaryService = new DictionaryService();
    }

    /**
     * Entry method to index the txt file provided.
     * @return
     * @throws Exception
     */
    public boolean indexFile() throws Exception {
        if ((inputFilePath != null) && (outputFilePath != null)) {
            super.parseFile(inputFilePath, this::processLine);
            System.out.println(wordDetailIndex.size());
            writeIndexToFile();
            return true;
        } else {
            System.out.println("ERROR: Input/Output Directory Paths");
            return false;
        }

    }

//    @Override
//    public void parseFile() throws Exception {
//        if(inputFilePath == null) return;
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)))) {
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                processLine(line);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void parseFile() throws Exception {
//        if(inputFilePath == null) return;
//        Files.lines(Path.of(inputFilePath))
//                .forEach(line -> Thread.startVirtualThread(() -> {
//                    try {
//                        processLine(line);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }));
//    }

    /**
     * Strips line of whitespaces & annotations before adding index
     *
     * @param line
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
     * Checks if word is already indexed & not forbidden. If not ,adds it to list and gets a
     * dictionary definition. If it does, adds to page index list.
     * @param word
     * @throws Exception
     */
    private void addWordToIndex(String word) {
        List<Integer> pageNumbersList;
        if(dictionaryService.getForbiddenWords().contains(word)){
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
            dictionaryService.setDictionaryDefinition(word, wordDetail);
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

    /**
     * Writes the index to the output file
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
                for (String def: defs) {
                    sb.append(def).append("\n");
                }
                sb.append("--------------------------------------------\n");
            }
            bw.write(sb.toString());
            bw.flush();
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
