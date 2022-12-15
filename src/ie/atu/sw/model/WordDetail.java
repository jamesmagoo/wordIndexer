package ie.atu.sw.model;

import java.util.List;

public class WordDetail {
    private String word;
    private String wordType;
    private String definition;
    private List<Integer> pageIndex;

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

    public List<Integer> getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(List<Integer> pageIndex) {
        this.pageIndex = pageIndex;
    }
}
