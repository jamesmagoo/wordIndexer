package ie.atu.sw.model;

public class DictionaryDetail {
    private String wordDefinition;
    private String wordType;

    public DictionaryDetail(String wordDefinition, String wordType) {
        this.wordDefinition = wordDefinition;
        this.wordType = wordType;
    }

    public String getWordDefinition() {
        return wordDefinition;
    }

    public void setWordDefinition(String wordDefinition) {
        this.wordDefinition = wordDefinition;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }
}
