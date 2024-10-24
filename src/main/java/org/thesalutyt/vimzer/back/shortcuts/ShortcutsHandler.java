package org.thesalutyt.vimzer.api.shortcuts;

import org.thesalutyt.vimzer.Configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ShortcutsHandler {
    private static ArrayList<Shortcut> shortcuts = new ArrayList<>();
    private static final File file = new File(Configurations.getBrowserDir() + File.separator + ".shortcuts");

    static {
        load();
    }

    public static ArrayList<Shortcut> getShortcuts() {
        return shortcuts;
    }

    public static void addShortcut(Shortcut shortcut) {
        shortcuts.add(shortcut);
        dump();
    }

    public static void removeShortcut(Shortcut shortcut) {
        shortcuts.remove(shortcut);
        dump();
    }

    public static Shortcut getShortcut(String shortcut) {
        for (Shortcut s : shortcuts) {
            if (s.getShortcut().equals(shortcut)) {
                return s;
            }
        }

        return null;
    }

    public static boolean containsShortcut(String shortcut) {
        return getShortcut(shortcut) != null;
    }

    public static void dump() {
        try {
            FileWriter writer = new FileWriter(file);

            for (Shortcut shortcut : shortcuts) {
                writer.write(shortcut.toString() + "\n");
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            if (!file.exists()) return;

            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String shortcut = line.split(";")[0];
                String command = line.split(";")[1];

                if (Configurations.isDebug()) {
                    System.out.println("Loaded shortcut: " + shortcut + " - " + command);
                }
                
                shortcuts.add(new Shortcut(shortcut, command));
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
