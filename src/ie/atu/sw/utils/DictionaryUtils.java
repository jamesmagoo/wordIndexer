package ie.atu.sw.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class DictionaryUtils {
    private Set<String> forbiddenWords = new ConcurrentSkipListSet<>();
    private Set<String> dictionary = new ConcurrentSkipListSet<>();

    public Set<String> getForbiddenWords() {
        return forbiddenWords;
    }

    // Method to load dictionary file into a set
    public void loadForbiddenWords(String filePath) throws Exception {
        parseFile(filePath);
    }

    public void loadDictionary(String file) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDict(String file) throws Exception {
        Files.lines(Path.of(file))
                .forEach(line -> Thread.startVirtualThread(() -> {
                    try {
                        System.out.println("Parsing file on virtual thread: " + line);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
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
