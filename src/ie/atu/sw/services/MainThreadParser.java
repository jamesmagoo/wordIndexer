package ie.atu.sw.services;

import java.io.*;
import java.util.function.Consumer;

public class MainThreadParser implements Parser {

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
