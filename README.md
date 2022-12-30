# Word Indexer - Multi-Threaded Indexing API
### ATU - Higher Diploma - Software Development - Advanced OOP Project Work
###### Lecturer: Dr.John Healy
###### Student: James McGauran
***

Multithreaded indexing API in Java 19+ that allows a word index to be created from a .txt file.

## Main Features:
- Set input/output/dictionary & forbidden words lists to get an index and word analysis of the provided .txt file.
- Dictionary definitions are provided if a dictionary file is loaded. 
- It is recommended to load a forbidden/common words file to eliminate words which would spoil an index.

## Extra Features:
- Option 6 - *Top 20 Words* - get the top 20 words in a text based on occurrences.
- Option 7 - *Word Searcher* - search for a word in the text and get its definition, frequency, and page index printed to the console.

## Compiling Note
To compile successfully, use ``javac ie/atu/sw/FileName.java`` from the src folder
Executable .class files are saved in same location.
***
## Class Descriptions
### Main.java
This class is the entry to the application (containing the main method) and should be run to start it.
***
### Menu.java
- This class provides the UI to the console for the user to interact and select option as provided in the starter code.
***
### WordIndexerService.java
This is the service class who's primary function is to index words, as follows:
1. Parses the provided file extending the `MainThreadParser.java` class using it's method `parseFile(String filePath, Consumer processor)` method.
2. It first processes the file by stripping whitespace & annotations from each word in a line in `processLine(String line)` method.
3. This word is then passed to `addWordToIndex(String word)` which contains all the logic to either add to an index or make a new entry to the `wordDetailIndex` Map which contains the index state.
4. This method uses the `DictionaryService` class methods to pull information on forbidden words & dictionary definitions. 
5. Extra features are also implemented here - `processTop20Words()` & `searchWordIndex()` methods. 
***
### DictionaryService.java
The primary function of DictionaryService is to parse and save provided dictionaries and forbidden words lists for use by the indexing service.
- This service avails of Virtual Threads to parse text files, extending the `VirtualThreadParser.java` class.
- Forbidden words are parsed and saved to state in the `forbiddenWords` set. 
- Dictionary words & their definitions are parsed and saved in the `Map<String, DictionaryDetail> dictionary` map.
***
### WordDetail.java
- This class describes a word detail object and is the **value** in the main `wordDetailIndex` map i.e. `Map<String, WordDetail>`.
- This class implements `Comparable` to allow words to be sorted for the Top 20 Words Feature.
- This class contains the list of pages, the word and a `DictionaryDetail` field related to that word. 
***
### DictionaryDetail.java
Object to store dictionary definitions & word type for a given word, used in the `dictionary` Map in `DictionaryService`
***
### Parser.java
This interface describes the parsing functionality of the application.
***
### VirtualThreadParser.java
This class implements the Parser interface using virtual threads to parse a provided text file path with a provided processing function.
***
### MainThreadParser.java
This class implements the Parser interface using platform threads to parse a provided text file path with a provided processing function.
