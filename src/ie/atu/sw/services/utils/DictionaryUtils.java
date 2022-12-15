package ie.atu.sw.services.utils;

import java.io.*;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class DictionaryUtils {
    private Set<String> forbiddenWords = new ConcurrentSkipListSet<>();

    public Set<String> getForbiddenWords() {
        return forbiddenWords;
    }

    // Method to load dictionary file into a set
    public void loadForbiddenWords(String filePath) throws Exception {
        parseFile(filePath);
    }

    // TODO : figure out how to abstract out this parsign function, it is used in numerous place
    private void parseFile(String file) throws Exception {
        System.out.println("Parsing file: " + file);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                forbiddenWords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
