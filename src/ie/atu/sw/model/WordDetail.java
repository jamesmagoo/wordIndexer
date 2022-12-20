package ie.atu.sw.model;

import java.util.List;

public class WordDetail {
    private String word;
    private String wordType;
    private String definition;

    private DictionaryDetail dictionaryDetail;
    private List<Integer> pageNumbersList;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<Integer> getPageNumbersList() {
        return pageNumbersList;
    }

    public void setPageNumbersList(List<Integer> pageNumbersList) {
        this.pageNumbersList = pageNumbersList;
    }
}
