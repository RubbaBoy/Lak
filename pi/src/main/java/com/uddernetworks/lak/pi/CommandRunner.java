package com.uddernetworks.lak.pi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CommandRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRunner.class);

    public static String runCommand(String... command) {
        return runCommand(Arrays.asList(command));
    }

    public static String runCommand(List<String> command) {
        var result = new StringBuilder();
        runInheritedCommand(command, null, process -> {
            inheritIOToStringBuilder(process.getInputStream(), result);
            inheritIOToStringBuilder(process.getErrorStream(), result);
        });
        return result.toString();
    }

    public static int runLiveCommand(List<String> command) {
        return runLiveCommand(command, null);
    }

    public static int runLiveCommand(List<String> command, String threadName) {
        return runLiveCommand(command, null, threadName);
    }

    public static int runLiveCommand(List<String> command, File directory, String threadName) {
        return runInheritedCommand(command, directory, process -> {
            inheritIO(process.getInputStream(), threadName);
            inheritIO(process.getErrorStream(), threadName);
        });
    }

    public static int runInheritedCommand(List<String> command, File directory, Consumer<Process> processCreate) {
        try {
            LOGGER.debug("Running command {}", String.join(" ", command));

            var process = new ProcessBuilder(command)
                    .directory(directory)
                    .start();

            processCreate.accept(process);

            Runtime.getRuntime().addShutdownHook(new Thread(process::destroyForcibly));

            try {
                return process.waitFor();
            } catch (InterruptedException ignored) { // This is probably from manually stopping the process; nothing bad to report
                process.destroyForcibly();
                return -1;
            }
        } catch (IOException e) {
            LOGGER.error("An error occurred while running command with arguments " + command, e);
            return -1;
        }
    }

    private static void inheritIO(InputStream inputStream, String threadName) {
        CompletableFuture.runAsync(() -> {
            Thread.currentThread().setName(threadName);
            var sc = new Scanner(inputStream);
            while (sc.hasNextLine()) {
                LOGGER.info(sc.nextLine());
            }
        });
    }

    private static void inheritIOToStringBuilder(InputStream inputStream, StringBuilder stringBuilder) {
        CompletableFuture.runAsync(() -> {
            var sc = new Scanner(inputStream);
            while (sc.hasNextLine()) {
                var line = sc.nextLine();
                synchronized (stringBuilder) {
                    stringBuilder.append(line);
                }
            }
        });
    }

}
