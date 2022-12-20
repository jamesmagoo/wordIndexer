package ie.atu.sw.utils;

import ie.atu.sw.model.DictionaryDetail;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Pattern;

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
                        //dictionary.add(line);
                        System.out.println("Parsing file on virtual thread: " + line);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    private void processDictionaryLine(String dictionaryLine) {
        // take first part of line add to key map
        // take second part add to word type value of map
        // take third part add to definition value of map
        // TODO check if there a part to avoid an ArrayOutOfBounds Exception i.e. some may not have a definition
        String[] parts = dictionaryLine.split(Pattern.quote(","));
        String word = parts[0];
        String type = parts[1];
        String definition = parts[2];
        for (String part : parts
        ) {
            System.out.println(part.trim());
        }
    }

    public void setDictionaryDefinition(String dictionaryLine){
        // take the word/
        // check if it is in the key set of the dictionary map
        // if yes append to WordDetail object
        // otherwise append "Dictionary Definition not found
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
