package ie.atu.sw.model;

import java.util.List;

/**
 * Object to describe a word in detail i.e. its dictionary details, and page indices.
 *
 * <ol>
 *     <li>This class describes a word detail object and is the **value** in the main `wordDetailIndex` map i.e. `Map<String, WordDetail>`.</li>
 *     <li>This class implements `Comparable` to allow words to be sorted for the Top 20 Words Feature.</li>
 *     <li>This class contains the list of pages, the word and a `DictionaryDetail` field related to that word.</li>
 * </ol>
 *
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
