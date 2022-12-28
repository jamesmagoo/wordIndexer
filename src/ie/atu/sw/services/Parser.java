package ie.atu.sw.services;

import java.util.function.Consumer;

public interface Parser {

    void parseFile(String filePath, Consumer<String> lineProcessor) throws Exception;
}
