package ru.otus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Tester {

    private final Task task;
    private final Path path;

    public Tester(Task task, Path path) {
        this.task = task;
        this.path = path;
    }

    public void runTests() {
        int nTest = 0;
        while (true) {
            Path inFile = path.resolve(String.format("test.%s.in", nTest));
            Path outFile = path.resolve(String.format("test.%s.out", nTest));
            if (!Files.exists(inFile.toAbsolutePath()) || !Files.exists(outFile.toAbsolutePath())) {
                break;
            }
            System.out.printf("Test #%2d : %s\n", nTest, runTest(inFile, outFile));
            nTest++;
        }
    }

    private boolean runTest(Path inFile, Path outFile) {
        try {
            List<String> data = Files.readAllLines(inFile);
            String expected = Files.readString(outFile).trim();
            String actual = task.run(data);
            return Objects.equals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
