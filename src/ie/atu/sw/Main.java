package ie.atu.sw;

import ie.atu.sw.services.WordIndexerService;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author James McGauran
 * @version 0.01
 *
 */
public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Word Indexer using virtual threads");

        // Create a WordIndexerService
        WordIndexerService wordIndexerService = new WordIndexerService();
        // Index the files in the directory
        wordIndexerService.indexFile("./testInput.txt", "output.txt");

//        Menu m = new Menu();
//        m.start();


    }
}

