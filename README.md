# Word Indexer - Multi-Threaded Indexing API
### ATU - Higher Diploma - Software Development - Advanced OOP Project Work
###### Lecturer: Dr.John Healy
###### Student: James McGauran
***  

Multithreaded indexing API in Java 19+ that allows a word index to be created from a .txt file.

## Main Features:
- Set input/output/dictionary & forbidden words file paths to get an index and word analysis of the provided .txt file.
- Option 5 - execute to get an index .txt file outputted with a list of words, their page indices, number of occurences, and a dictionary defintion.
- Dictionary definitions are provided if a dictionary file is loaded. Please ensure the dictionary format is as per note below for correct parsing.
- It is recommended to load a forbidden/common words file to eliminate words which would spoil an index.

## Extra Features:
- Option 6 - *Top 20 Words* - get the top 20 words in a text based on number of occurrences.  This will be outputted to a .txt file. A common words file must be uplaoded to use this feature.
- Option 7 - *Word Searcher* - search for a word in the text and get, frequency, and page index printed to the console.

## JAR File Note
You may need to run the provided indexer.jar file with the `--enable-preview` flag, as this application uses the Virtual Thread preview features in Java 19.

`java --enable-preview  -cp ./indexer.jar ie.atu.sw.Runner`

## Note on Dictionary Format
The application is designed to work for dictioanry definaition in .csv format as provided on Moodle.

***
