package ie.atu.sw.model;

import java.util.List;

/**
 * Forms the value object for the word index map Map<String, WordDetail>
 * Contains dictionary details, page index and the word reference.
 * Implements Comparable to allow for sorting of words by their frequency of occurrence.
 */
public class WordDetail implements Comparable<WordDetail>{
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

    /**
     * Words are sorted by there frequency of occurrence in the text provided.
     * This allows the WordDetail objects to be sorted in descending order based on the size of their pageNumbersList.
     *
     *'Big O' Time Complexity -> O (1)
     *
     * @param o the object to be compared.
     * @return int - 0 if same, 1 if greater, -1 if less
     */
    @Override
    public int compareTo(WordDetail o) {
        if(this.pageNumbersList.size() > o.pageNumbersList.size()) return -1;
        if(this.pageNumbersList.size() < o.pageNumbersList.size()) return 1;
        return 0;
    }
}
