package ru.otus;

import org.apache.commons.cli.*;

import java.io.File;
import java.nio.file.Path;

/**
 * Консольное приложение по сжатию / распаковке файлов
 */
public class ConsoleApp {

    /**
     * Запуск приложения
     *
     * @param args аргументы приложения
     */
    public void run(String[] args) {
        CommandLine line = parseArguments(args);

        boolean ok = true;
        if (line.hasOption("inputFile") && line.hasOption("outputFile") && line.hasOption("action") && line.hasOption("type")) {
            String inputFile = line.getOptionValue("inputFile");
            String outputFile = line.getOptionValue("outputFile");
            Path input = Path.of(inputFile);
            Path output = Path.of(outputFile);

            String action = line.getOptionValue("action");
            String type = line.getOptionValue("type");

            if (action.equals("compress") && type.equals("plain")) {
                new RLEPlain().compress(input, output);
            } else if (action.equals("decompress") && type.equals("plain")) {
                new RLEPlain().decompress(input, output);
            } else if (action.equals("compress") && type.equals("improved")) {
                new RLEImproved().compress(input, output);
            } else if (action.equals("decompress") && type.equals("improved")) {
                new RLEImproved().decompress(input, output);
            } else {
                ok = false;
            }
        } else {
            ok = false;
        }

        if (!ok) {
            printAppHelp();
        }
    }

    /**
     * Парсинг аргументов приложения
     *
     * @param args аргументы приложения
     * @return <code>CommandLine</code> со списком аргументов приложения
     */
    private CommandLine parseArguments(String[] args) {
        Options options = getOptions();
        CommandLine line = null;

        CommandLineParser parser = new DefaultParser();

        try {
            line = parser.parse(options, args);
        } catch (ParseException ex) {
            System.err.println("Failed to parse command line arguments");
            System.err.println(ex.getMessage());
            printAppHelp();

            System.exit(1);
        }

        return line;
    }

    /**
     * Возможные параметры приложения
     */
    private Options getOptions() {
        Options options = new Options();
        options.addOption("fi", "inputFile", true, "input file in resource folder");
        options.addOption("fo", "outputFile", true, "output file in resource folder");
        options.addOption("a", "action", true, "compress / decompress");
        options.addOption("t", "type", true, "plain / improved algorithm");
        return options;
    }

    /**
     * Помощь по использованию
     */
    private void printAppHelp() {
        Options options = getOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("RLE", options, true);
    }

    /**
     * Запуск приложения (список аргументов передаём вручную для проверки)
     */
    public static void main(String[] args) throws Exception {
        Path rootFolder = Path.of(new File(".").getCanonicalFile().getPath(), "hw21-rle/src/main/resources");
        for (String type : new String[] { "plain", "improved" }) {
            for (String action : new String[] { "compress", "decompress" }) {
                new ConsoleApp().run(new String[] {
                    "-inputFile", rootFolder.resolve(action.equals("compress") ? "img.jpg" : String.format("img_compress_%s.jpg", type)).toString(),
                    "-outputFile", rootFolder.resolve(String.format("img_%s_%s.jpg", action, type)).toString(),
                    "-action", action,
                    "-type", type
                });
                new ConsoleApp().run(new String[] {
                    "-inputFile", rootFolder.resolve(action.equals("compress") ? "text.txt" : String.format("text_compress_%s.txt", type)).toString(),
                    "-outputFile", rootFolder.resolve(String.format("text_%s_%s.txt", action, type)).toString(),
                    "-action", action,
                    "-type", type
                });
            }
        }
    }
}
