package ie.atu.sw.services;

import ie.atu.sw.model.DictionaryDetail;
import ie.atu.sw.model.WordDetail;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Class for utilities related to word resources such as lists of words
 * to be omitted, and dictionary definitions of words.
 */
public class DictionaryService extends VirtualThreadParser{
    private static Set<String> forbiddenWords = new ConcurrentSkipListSet<>();
    private static Map<String, DictionaryDetail> dictionary = new ConcurrentSkipListMap<>();
    private String dictionaryPath;
    private String forbiddenWordsPath;

    /**
     * Parse & loads forbidden words from a provided file.
     * These words will not be included in any word indexing.
     *
     * @throws Exception if the file cannot be found at the given path.
     */
    public void loadForbiddenWords() throws Exception {
     super.parseFile(forbiddenWordsPath, line -> forbiddenWords.add(line));
    }

    /**
     * Parses & loads dictionary file into Set for reference in indexing.
     *
     * @throws Exception if the file cannot be found at the given path.
     */
    public void loadDictionary() throws Exception {
      super.parseFile(dictionaryPath, this::processDictionaryLine);
    }

    /**
     * Parses a dictionary line to extract and add to list.
     * 1. the word
     * 2. the word type
     * 3. definition(s)
     * @param dictionaryLine
     */
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
        List<String> defs = new ArrayList<>();
        defs.add(firstDef.trim());

        // Loop over the remaining definitions and add to list
        for (int i = 1; i < definitions.length; i++) {
            parts = definitions[i].split(";");
            wordDef = parts[0];
            defs.add(wordDef.trim());
        }
        DictionaryDetail dictionaryDetail = new DictionaryDetail(defs, wordType);
        dictionary.put(word.toLowerCase(), dictionaryDetail);
    }


    /**
     * Method to set dictionary definitions in the word index.
     * @param word
     * @param wordDetail
     */
    public void setDictionaryDefinition(String word, WordDetail wordDetail){
        if(dictionary.containsKey(word)){
            wordDetail.setDictionaryDetail(dictionary.get(word));
        } else {
            wordDetail.setDictionaryDetail(new DictionaryDetail(Arrays.asList("Definition Not Found"), "No Word Type Found"));
        }
    }

    public void setDictionaryPath(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    public String getDictionaryPath() {
        return dictionaryPath;
    }

    public void setForbiddenWordsPath(String forbiddenWordsPath) {
        this.forbiddenWordsPath = forbiddenWordsPath;
    }

    public String getForbiddenWordsPath() {
        return forbiddenWordsPath;
    }

    public Set<String> getForbiddenWords() {
        return forbiddenWords;
    }
}
