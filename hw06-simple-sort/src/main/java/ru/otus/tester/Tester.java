package ru.otus.tester;

import ru.otus.util.ExceptionUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class Tester<T> {

    private final List<Task<T>> tasks;
    private final Path path;
    private final ExecutorService executorService;

    public Tester(List<Task<T>> tasks, Path path) {
        this.tasks = tasks;
        this.path = path;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void runTests() {
        for (Task<T> task : tasks) {
            System.out.println("--------------------------");
            System.out.printf("Task %s\n", task.name());
            int nTest = 0;
            boolean onTimeout = false; // если тест падает по таймауту, то последующие не выполняем
            while (!onTimeout) {
                Path inFile = path.resolve(String.format("test.%s.in", nTest));
                Path outFile = path.resolve(String.format("test.%s.out", nTest));
                if (!Files.exists(inFile.toAbsolutePath()) || !Files.exists(outFile.toAbsolutePath())) {
                    break;
                }
                long start = System.currentTimeMillis();
                // запускаем алгоритм в отдельном потоке, ждём таймаут
                Future<Boolean> resultFuture = executorService.submit(() -> {
                    try {
                        return runTest(task, inFile, outFile);
                    } catch (Throwable ex) {
                        System.err.println(ExceptionUtils.getMessage(ex));
                        return null;
                    }
                });
                Boolean result = null;
                try {
                    result = resultFuture.get(2, TimeUnit.MINUTES);
                } catch (Throwable ex) {
                    System.err.println(ExceptionUtils.getMessage(ex));
                }
                onTimeout = !resultFuture.isDone();
                long end = System.currentTimeMillis();
                String resultStr = onTimeout ? "timeout" : (result == null ? "null" : result.toString());
                resultFuture.cancel(true);
                System.out.printf("Test #%2d result: %7s time: %10d ms addInfo: %s\n", nTest, resultStr, end - start, task.info());
                nTest++;
            }
        }
    }

    public void stop() {
        executorService.shutdownNow();
    }

    abstract boolean compare(String expected, T actual);

    private boolean runTest(Task<T> task, Path inFile, Path outFile) {
        try {
            List<String> data = Files.readAllLines(inFile);
            String expected = Files.readString(outFile).trim();
            T actual = task.run(data);
            return compare(expected, actual);
        } catch (Throwable ex) {
            System.err.println(ExceptionUtils.getMessage(ex));
            return false;
        }
    }

}
