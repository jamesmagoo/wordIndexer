package ie.atu.sw.utils;

import ie.atu.sw.model.DictionaryDetail;
import ie.atu.sw.model.WordDetail;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Class for utilities related to word resources such as lists of words
 * to be omitted, and dictionary definitions of words.
 */
public class DictionaryUtils {
    private Set<String> forbiddenWords = new ConcurrentSkipListSet<>();
    private Map<String, DictionaryDetail> dictionary = new ConcurrentSkipListMap<>();


    /**
     * Gets the forbidden words list as loaded by the user
     *
     * @return the Set of forbidden words
     */
    public Set<String> getForbiddenWords() {
        return forbiddenWords;
    }

    /**
     * Loads a list of forbidden words from a provided file.
     *
     * @param filePath
     * @throws Exception if the file cannot be found at the given path.
     */
    public void loadForbiddenWords(String filePath) throws Exception {
        parseFile(filePath);
    }

    /**
     * Loads dictionary file into Set
     *
     * @param filePath
     * @throws Exception if the file cannot be found at the given path.
     */
    public void loadDictionary(String filePath) throws Exception {
        Files.lines(Path.of(filePath))
                .forEach(line -> Thread.startVirtualThread(() -> {
                    try {
                        processDictionaryLine(line);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    public void testloadDictionary(String filePath) throws Exception {
        Files.lines(Path.of(filePath))
                .forEach(line -> {
                    try {
                        processDictionaryLine(line);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void processDictionaryLine(String dictionaryLine) {

        // Split the line on the semicolon character to get the individual definitions
        String[] definitions = dictionaryLine.split(";");

        // Split the first definition on the comma character to get the word and word type
        String[] parts = definitions[0].split(",");
        String word = parts[0];
        String wordDef = parts[1];
        String[] splitAgain =  wordDef.split(":");
        String wordType = splitAgain[0];
        String firstDef = splitAgain[1];

        // Print the word and word type
        System.out.println(word.toUpperCase());
        System.out.println(wordType);
        System.out.println(firstDef);
        List<String> defs = new ArrayList<>();
        defs.add(firstDef.trim());

        // Loop over the remaining definitions and add to list
        for (int i = 1; i < definitions.length; i++) {
            parts = definitions[i].split(";");
            wordDef = parts[0];
            System.out.println(wordDef);
            defs.add(wordDef.trim());
        }
        System.out.println("________________");
        DictionaryDetail dictionaryDetail = new DictionaryDetail(defs, wordType);
        dictionary.put(word.toLowerCase(), dictionaryDetail);

    }

    public void setDictionaryDefinition(String word, WordDetail wordDetail){
        // take the word/
        if(dictionary.containsKey(word)){
            wordDetail.setDictionaryDetail(dictionary.get(word));
        } else {
            wordDetail.setDictionaryDetail(new DictionaryDetail(Arrays.asList("Definition Not Found"), "Unknown"));
        }
    }

    // TODO : figure out how to abstract out this parsing function, it is used in numerous place
    private void parseFile(String file) throws Exception {
        System.out.println("Parsing file: " + file);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                forbiddenWords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
