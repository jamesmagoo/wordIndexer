package ie.atu.sw.interfaces;

import java.util.function.Consumer;

/**
 * This interface describes the parsing functionality of the application.
 */
public interface Parser {

    void parseFile(String filePath, Consumer<String> lineProcessor) throws Exception;
}
