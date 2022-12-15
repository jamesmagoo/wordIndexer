package ie.atu.sw.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Class for utilities related to word resources such as lists of words
 * to be omitted, and dictionary definitions of words.
 */
public class DictionaryUtils {
    private Set<String> forbiddenWords = new ConcurrentSkipListSet<>();
    private Set<String> dictionary = new ConcurrentSkipListSet<>();


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
                        // TODO make this into an object
                        dictionary.add(line);
                        System.out.println("Parsing file on virtual thread: " + line);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    public void buildWordDetail(){
        // find a dictionary definition for the word
        // if not present "No Defintion Available"
        // add word type as per dictionary
        // add page numbers list
    }

    public void findDictionaryDefinition(String word){

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
