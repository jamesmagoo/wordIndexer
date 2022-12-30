package ie.atu.sw.interfaces;

import java.util.function.Consumer;

public interface Parser {

    void parseFile(String filePath, Consumer<String> lineProcessor) throws Exception;
}
