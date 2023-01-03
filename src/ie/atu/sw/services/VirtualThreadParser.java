package ie.atu.sw.services;

import ie.atu.sw.interfaces.Parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;


/**
 * Implements a virtual thread solution of the Parser interface
 */
public class VirtualThreadParser implements Parser {

    /**
     * This is parsing function as specified by the Parser interface.
     *
     * 'Big O' Time Complexity -> O(n) for forEach method.
     * Complexity of passed in lineProcessor may dictate ultimate time complexity of this function.
     *
     * @param filePath the path of the file to be parsed.
     * @param lineProcessor a provided processing method for each line of the provided file.
     * @throws Exception
     */
    @Override
    public void parseFile(String filePath, Consumer<String> lineProcessor) throws Exception {
        if(filePath == null) return;
        Files.lines(Path.of(filePath))
                .forEach(line -> Thread.startVirtualThread(() -> {
                    try {
                        lineProcessor.accept(line);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

}
