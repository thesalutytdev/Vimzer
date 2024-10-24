package org.thesalutyt.vimzer.back.history.command;

import org.thesalutyt.vimzer.Configurations;
import org.thesalutyt.vimzer.api.features.commmand.CommandParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CommandHistory {
    public ArrayList<String> history = new ArrayList<>();
    private int currentIndex = 0;
    private static final File file = new File(Configurations.getBrowserDir() + File.separator + ".command.vzhst");

    public void add(String command) {
        history.add(command);
        currentIndex = history.size() - 1;
        dump();
    }

    public String getPrevious() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = 0;
        }
        return history.get(currentIndex);
    }

    public String getNext() {
        currentIndex++;
        if (currentIndex >= history.size()) {
            currentIndex = history.size() - 1;
        }
        return history.get(currentIndex);
    }

    public void clear() {
        history.clear();
        currentIndex = 0;
        dump();
    }
    public int size() {
        return history.size();
    }

    public void dump() {
        try {
            if (!file.exists()) file.createNewFile();

            FileWriter writer = new FileWriter(file);
            for (String line : history) {
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (!file.exists()) return;

            history.clear();
            history.addAll(
                java.nio.file.Files.readAllLines(file.toPath())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String beautify() {
        StringBuilder sb = new StringBuilder();

        for (String line : history) {
            sb.append("> ")
                .append(line)
                .append(" - ")
                .append(CommandParser.getCommand(line.split(" ")[0]).description())
                .append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return String.join("\n", history);
    }
}
