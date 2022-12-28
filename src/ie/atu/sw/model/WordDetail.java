package ie.atu.sw.model;

import java.util.List;

/**
 * Forms the value object for the word index map Map<String, WordDetail>
 * Contains dictionary details, page index and the word reference.
 */
// TODO implement comparable here so WordDetails can be sorted for top 10 feature.
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
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(WordDetail o) {
        if(this.pageNumbersList.size() > o.pageNumbersList.size()) return -1;
        if(this.pageNumbersList.size() < o.pageNumbersList.size()) return 1;
        return 0;
    }
}
