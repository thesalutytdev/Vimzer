package org.thesalutyt.vimzer.back.history.browser;

import org.thesalutyt.vimzer.Configurations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class History {
    private ArrayList<HistoryPage> history = new ArrayList<>();
    private int currentIndex = 0;

    public History() {
        load();
    }

    public void clear() {
        history.clear();
        currentIndex = 0;
        dump();
    }

    public void add(HistoryPage page) {
        history.add(page);
        dump();
        currentIndex = history.size() - 1;
    }

    public HistoryPage next() {
        currentIndex++;
        if (currentIndex >= history.size()) {
            currentIndex = history.size() - 1;
        }
        return history.get(currentIndex);
    }

    public HistoryPage previous() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = 0;
        }
        return history.get(currentIndex);
    }

    public void dump() {
        File file = new File(Configurations.getBrowserDir() + File.separator + ".history.vzhst");
        try {
            FileWriter writer = new FileWriter(file);
            for (HistoryPage line : history) {
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File file = new File(Configurations.getBrowserDir() + File.separator + ".history.vzhst");
        if (!file.exists()) {
            return;
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                history.add(HistoryPage.fromString(line));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (HistoryPage line : history) {
            str.append(line.beautify()).append("\n");
        }
        return str.toString();
    }
}
