package ie.atu.sw.services;

import ie.atu.sw.interfaces.Parser;

import java.io.*;
import java.util.function.Consumer;

public class MainThreadParser implements Parser {

    /**
     * This is parsing function as specified by the Parser interface. Takes a files and parses
     * each line passing it to a specified lineProcessor function.
     *
     * 'Big O' Time Complexity -> O(n) for while loop
     * Complexity of passed in lineProcessor may dictate ultimate time complexity of this function.
     *
     * @param filePath the path of the file to be parsed.
     * @param lineProcessor a provided processing method for each line of the provided file.
     * @throws Exception
     */
    @Override
    public void parseFile(String filePath, Consumer<String> lineProcessor) throws Exception {
        if(filePath == null) return;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                lineProcessor.accept(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
