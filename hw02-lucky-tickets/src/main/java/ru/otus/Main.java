package ru.otus;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        Task lucky = new Lucky();
        Tester tester = new Tester(lucky, Path.of(Main.class.getClassLoader().getResource("1.Tickets").toURI()));
        tester.runTests();
    }
}