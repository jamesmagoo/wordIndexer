package ie.atu.sw.model;

import java.util.List;

public class DictionaryDetail {
    private List<String> wordDefinitions;
    private String wordType;

    public DictionaryDetail(List<String> wordDefinitions, String wordType) {
        this.wordDefinitions = wordDefinitions;
        this.wordType = wordType;
    }

    public List<String> getWordDefinitions() {
        return wordDefinitions;
    }

    public void setWordDefinitions(List<String> wordDefinitions) {
        this.wordDefinitions = wordDefinitions;
    }

    public void addWordDefinition(String definition){
        this.wordDefinitions.add(definition);
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }
}
