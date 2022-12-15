# Word Indexer
### ATU - Higher Diploma - Software Development - Advanced OOP Project Work
###### Lecturer: Dr.John Healy
###### Student: James McGauran
***
*This application parses the .txt files in a given directory, counts the frequencies of n-grams within, and ouputs them to a text file in CSV format. *

## Compiling Note
To compile successfully, use ``javac ie/atu/sw/FileName.java`` from the src folder
Executable .class files are saved in same location.
***
## Class Descriptions

### Runner.java
This class is the entry to the application (containing the main method) and should be run to start it.
***
### Menu.java
- This class provides the UI to the console for the user to interact and select option as provided in the starter code.
##### State
- ``s`` - Scanner object
- ``p`` - Parser object for access to Parser.java functionality
##### Methods
- ``start()`` - initialises menu and deals with user inputs, called from Runner.java
- ``showOptions()`` - prints options to console
***
### Parser.java
- This class contains the functionality of the application, it parses files, processes them, and outputs the results.
##### State
- ``freqTable`` - multi-dimensional object array which stores frequencies of nGrams
- ``nGramSize`` - stores the size of nGrams to be processed, set by user
- ``inputDirPath`` - stores the path to the directory for processign as set by user
- ``outPutName`` - stores the name of the outout file as set by user
##### Methods
*For brevity, getter & setter methods are not listed below*:
- ``Parser()`` - constructor method initialises the size of the freqTable array
- ``executeNGramBuilder()`` - primary function to begin process, called from Menu
- ``printTable()`` - outputs results to file
- ``addNGram()`` - Add n-gram to freqTable
- ``processFile()`` - read files and process them to nGrams
- ``parseDirectory()`` - parses each file a given directory
- ``makeNGrams()`` - makes n-grams and saves them
