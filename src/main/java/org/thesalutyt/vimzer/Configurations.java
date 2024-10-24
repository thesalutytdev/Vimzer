package org.thesalutyt;

public class Configurations {
    private static boolean debug = true;
    private static boolean log = true;
    private static String defaultPage = "https://thesalutytdev.github.io/";

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
}
