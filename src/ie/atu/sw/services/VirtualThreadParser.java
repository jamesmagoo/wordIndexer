package ie.atu.sw.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class VirtualThreadParser implements Parser{

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
