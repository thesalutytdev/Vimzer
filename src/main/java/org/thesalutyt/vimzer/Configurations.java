package org.thesalutyt.vimzer;

import org.thesalutyt.vimzer.api.features.lock.Password;
import org.thesalutyt.vimzer.window.MainWindow;
import org.thesalutyt.vimzer.window.popus.DefaultPopup;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Configurations {
    private static boolean debug = true;
    private static boolean log = true;
    private static String defaultPage = "https://google.com/";
    private static final File browserDir = new File(System.getProperty("user.home") + File.separator + ".vimzer");
    private static final DefaultPopup popup = new DefaultPopup("");
    private static MainWindow currentActiveBrowserWindow;
    private static final ArrayList<MainWindow> browserWindows = new ArrayList<>();
    private static boolean commandLineOnStart = false;
    private static boolean passwordSet = false;
    private static boolean isFirstRun = true;
    private static final String SCRIPT_EXTENSION = ".vmz";

    static {
        load();
        save();
    }

    public static String getScriptExtension() {
        return SCRIPT_EXTENSION;
    }

    public static boolean isPasswordSet() {
        return passwordSet;
    }

    public static void setPasswordSet(boolean passwordSet) {
        Configurations.passwordSet = passwordSet;
    }

    public static boolean isCommandLineOnStart() {
        return commandLineOnStart;
    }

    public static void setCommandLineOnStart(boolean commandLineOnStart) {
        Configurations.commandLineOnStart = commandLineOnStart;
    }

    public static MainWindow getCurrentActiveBrowserWindow() {
        return currentActiveBrowserWindow;
    }

    public static ArrayList<MainWindow> getBrowserWindows() {
        return browserWindows;
    }

    public static void addBrowserWindow(MainWindow browserWindow) {
        browserWindows.add(browserWindow);
    }

    public static void removeBrowserWindow(MainWindow browserWindow) {
        browserWindows.remove(browserWindow);
    }

    public static void setFirstRun(boolean firstRun) {
        Configurations.isFirstRun = firstRun;
    }

    public static void setCurrentActiveBrowserWindow(MainWindow currentActiveBrowserWindow) {
        Configurations.currentActiveBrowserWindow = currentActiveBrowserWindow;
    }

    public static DefaultPopup getPopup() {
        return popup;
    }

    static {
        if (!browserDir.exists()) {
            browserDir.mkdirs();
        }
    }

    public static boolean isFirstRun() {
        return isFirstRun;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Configurations.debug = debug;
    }

    public static boolean isLog() {
        return log;
    }

    public static void setLog(boolean log) {
        Configurations.log = log;
    }

    public static String getDefaultPage() {
        return defaultPage;
    }

    public static void setDefaultPage(String defaultPage) {
        Configurations.defaultPage = defaultPage;
    }

    public static void removePassword() {
        Configurations.setPasswordSet(false);
        Password.setPassword("");
    }

    public static void setPassword(String arg) {
        Password.setPassword(arg);
        Configurations.setPasswordSet(true);
    }

    public static File getBrowserDir() {
        return browserDir;
    }

    public static String structData() {
        return ("debug=" + Configurations.isDebug() + "\n" +
        "log=" + Configurations.isLog() + "\n" +
        "default_page=" + Configurations.getDefaultPage() + "\n" +
        "command_line_on_start=" + Configurations.isCommandLineOnStart() + "\n" +
        "password_set=" + Configurations.isPasswordSet() + "\n" +
        "first_run=" + Configurations.isFirstRun() + "\n");
    }

    public static void save() {
        try {
            Configurations.getBrowserDir().mkdirs();
            File file = new File(Configurations.getBrowserDir() + File.separator + ".config");

            if (!file.exists()) file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(structData());

            writer.close();
        } catch (Exception e) {
            Configurations.getPopup().display();
        }
    }

    public static void load() {
        try {
            File file = new File(Configurations.getBrowserDir() + File.separator + ".config");

            if (!file.exists()) return;

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split("=");
                if (split.length == 2) {
                    switch (split[0]) {
                        case "debug":
                            Configurations.setDebug(Boolean.parseBoolean(split[1]));
                            break;
                        case "log":
                            Configurations.setLog(Boolean.parseBoolean(split[1]));
                            break;
                        case "default_page":
                            Configurations.setDefaultPage(split[1]);
                            break;
                        case "command_line_on_start":
                            Configurations.setCommandLineOnStart(Boolean.parseBoolean(split[1]));
                            break;
                        case "password_set":
                            Configurations.setPasswordSet(Boolean.parseBoolean(split[1]));
                            break;
                        case "first_run":
                            Configurations.setFirstRun(Boolean.parseBoolean(split[1]));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            Configurations.getPopup().display();
        }
    }

    public static void reset() {
        Configurations.setDebug(false);
        Configurations.setLog(false);
        Configurations.setDefaultPage("https://thesalutytdev.github.io/");
        Configurations.setCommandLineOnStart(false);

    }

    public static void showHelp() {
        String[] helpLines = {
                "Vimzer - Browser with commands!",
                "",
                "How to open command line:",
                "  - Press Shift 3 times to open command line.",
                "  - Type in command and press Enter.",
                "",
                "Options:",
                " - To see all commands execute \"cmdl\" command.",
                " - To see this window again type \"helpw\" command.",
                "",
                "Thanks for using Vimzer!",
        };

        new DefaultPopup.MultiLine(helpLines).display();
    }
}
