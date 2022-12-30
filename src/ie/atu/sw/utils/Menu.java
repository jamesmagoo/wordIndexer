package ie.atu.sw.utils;

import ie.atu.sw.services.DictionaryService;
import ie.atu.sw.services.WordIndexerService;

import java.util.Scanner;

public class Menu {

    private Scanner s;
    private WordIndexerService wordIndexerService;
    private DictionaryService dictionaryService;
    private boolean keepRunning = true;

    public Menu() throws Exception {
        s = new Scanner(System.in);
        dictionaryService = new DictionaryService();
        wordIndexerService = new WordIndexerService();
    }

    /**
     * Method to start the console menu and handle user inputs
     *
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        showOptions();
        while (keepRunning) {
            // try-catch to deal with non-integer inputs generating NumberFormatExceptions
            try {
                int choice = Integer.parseInt(s.next());
                if (choice == 1) {
                    System.out.println(ConsoleColour.GREEN);
                    System.out.println("Input Folder Path:");
                    String inputPath = s.next();
                    if (inputPath != null) {
                        wordIndexerService.setInputFilePath(inputPath);
                        System.out.print("Select Option [1-8]>");
                        System.out.println();
                        showOptions();
                    } else {
                        System.out.println("Error");
                    }
                } else if (choice == 2) {
                    System.out.println(ConsoleColour.PURPLE_BOLD);
                    System.out.println("Enter the dictionary file path:");
                    String dictionaryInput = s.next();
                    dictionaryService.setDictionaryPath(dictionaryInput);
                    dictionaryService.loadDictionary();
                    System.out.print("Select Option [1-8]>");
                    System.out.println();
                    showOptions();
                } else if (choice == 3) {
                    System.out.println(ConsoleColour.PURPLE_BOLD);
                    System.out.println("Set forbidden Words Path:");
                    String forbiddenWordsPath = s.next();
                    if (forbiddenWordsPath != null) {
                        dictionaryService.setForbiddenWordsPath(forbiddenWordsPath);
                        dictionaryService.loadForbiddenWords();
                        System.out.print("Select Option [1-8]>");
                        System.out.println();
                        showOptions();
                    } else {
                        System.out.println("Error");
                    }
                } else if (choice == 4) {
                    System.out.println(ConsoleColour.PURPLE_BOLD);
                    System.out.println("Set output file Name:");
                    String outputPath = s.next();
                    if (outputPath != null) {
                        wordIndexerService.setOutputFilePath(outputPath);
                        System.out.print("Select Option [1-8]>");
                        System.out.println();
                        showOptions();
                    } else {
                        System.out.println("Error");
                    }

                } else if (choice == 5) {
                    try {
                        displayLoading();
                        if (wordIndexerService.indexFile() == true) {
                            System.out.println();
                            System.out.println("Success, please see output file saved in folder.");
                        } else {
                            System.out.println("There was an error, please ensure all inputs were correct");
                        }
                        ;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.print("Select Option [1-8]>");
                    System.out.println();
                } else if (choice == 6) {
                    try {
                        System.out.println(ConsoleColour.BLUE_UNDERLINED);
                        System.out.println("Top 20 Words");
                        System.out.println(ConsoleColour.YELLOW);
                        // TODO fix this so it only works when common words is set
                        if (wordIndexerService.processTopTenWords() == true) {
                            System.out.println(ConsoleColour.GREEN);
                            System.out.println("Please find txt file outputted called top20.txt");
                        } else {
                            System.out.println(ConsoleColour.RED_BRIGHT);
                            System.out.println("Set input/output and forbidden words files.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (choice == 7) {
                    System.out.println(ConsoleColour.PURPLE_BOLD);
                    System.out.println("Enter word to search for:");
                    String searchWord = s.next();
                    displayLoading();
                    System.out.println("\n");
                    if (wordIndexerService.wordSearch(searchWord) == true) {
                        System.out.println(ConsoleColour.GREEN_BOLD_BRIGHT);
                        System.out.print("Select Option [1-8]>");
                        System.out.println();
                    } else {
                        System.out.println(ConsoleColour.RED_BRIGHT);
                    }

                } else if (choice == 8) {
                    System.out.println(ConsoleColour.RED_BRIGHT);
                    System.out.println("Closing Application..");
                    System.exit(0);
                } else if (choice == 11) {
                    System.out.println(ConsoleColour.RED_BRIGHT);
                    dictionaryService.setDictionaryPath("./dictionary.csv");
                    dictionaryService.loadDictionary();
                    dictionaryService.setForbiddenWordsPath("./google-1000.txt");
                    dictionaryService.loadForbiddenWords();
                    System.out.println("TESTING ");
                    wordIndexerService.setInputFilePath("./1984.txt");
                    wordIndexerService.setOutputFilePath("testing2.txt");
                    if (wordIndexerService.indexFile() == true) {
                        System.out.println();
                        System.out.println(ConsoleColour.GREEN);
                        System.out.println("Success, please see output file saved in folder.");
                    } else {
                        System.out.println("There was an error, please ensure all inputs were correct");
                    }
                } else {
                    System.out.println(ConsoleColour.RED_BRIGHT);
                    System.out.println("Invalid Choice");
                    System.out.print("Select Option [1-8]>");
                    showOptions();
                }
            } catch (Exception e) {
                System.out.println("Please enter a number between 1 & 6 only. No characters.");
            }
        }
    }

    /**
     * Displays menu layout/UI in console.
     *
     * @throws InterruptedException
     */
    private void showOptions() throws InterruptedException {
        System.out.println(ConsoleColour.YELLOW);
        System.out.println("************************************************************");
        System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
        System.out.println("*                                                          *");
        System.out.println("*              Virtual Threaded Text Indexer               *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
        System.out.println("(1) Specify Text File To Index");
        System.out.println("(2) Configure Dictionary");
        System.out.println("(3) Configure Common Words");
        System.out.println("(4) Specify Output File");
        System.out.println("(5) Execute");
        System.out.println("(6) Top 20 Words");
        System.out.println("(7) Word Searcher");
        System.out.println("(8) Quit");

        //Output a menu of options and solicit text from the user
        System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
        System.out.print("Select Option [1-8]>");
        System.out.println();
    }


    /**
     * Loading bar logic
     *
     * @param index
     * @param total
     */
    public static void printProgress(int index, int total) {
        if (index > total) return;    //Out of range
        int size = 50;                //Must be less than console width
        char done = '█';            //Change to whatever you like.
        char todo = '░';            //Change to whatever you like.

        //Compute basic metrics for the meter
        int complete = (100 * index) / total;
        int completeLen = size * complete / 100;

        /*
         * A StringBuilder should be used for string concatenation inside a
         * loop. However, as the number of loop iterations is small, using
         * the "+" operator may be more efficient as the instructions can
         * be optimized by the compiler. Either way, the performance overhead
         * will be marginal.
         */
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append((i < completeLen) ? done : todo);
        }

        /*
         * The line feed escape character "\r" returns the cursor to the
         * start of the current line. Calling print(...) overwrites the
         * existing line and creates the illusion of an animation.
         */
        System.out.print("\r" + sb + "] " + complete + "%");

        //Once the meter reaches its max, move to a new line.
        if (done == total) System.out.println("\n");
    }

    /**
     * Displays loading bar
     *
     * @throws InterruptedException
     */
    public void displayLoading() throws InterruptedException {
        System.out.print(ConsoleColour.YELLOW);    //Change the colour of the console text
        int size = 100;                            //The size of the meter. 100 equates to 100%
        for (int i = 0; i < size; i++) {        //The loop equates to a sequence of processing steps
            printProgress(i + 1, size);        //After each (some) steps, update the progress meter
            Thread.sleep(10);                    //Slows things down so the animation is visible
        }
    }

}
