package ie.atu.sw.model;

import java.util.List;

/**
 * Forms the value object for the word index map Map<String, WordDetail>
 * Contains dictionary details, page index and the word reference.
 */
public class WordDetail {
    private String word;
    private DictionaryDetail dictionaryDetail;
    private List<Integer> pageNumbersList;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Integer> getPageNumbersList() {
        return pageNumbersList;
    }

    public void setPageNumbersList(List<Integer> pageNumbersList) {
        this.pageNumbersList = pageNumbersList;
    }

    public DictionaryDetail getDictionaryDetail() {
        return dictionaryDetail;
    }

    public void setDictionaryDetail(DictionaryDetail dictionaryDetail) {
        this.dictionaryDetail = dictionaryDetail;
    }
}
