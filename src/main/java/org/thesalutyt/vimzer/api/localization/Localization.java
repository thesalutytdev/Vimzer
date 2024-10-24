package org.thesalutyt.vimzer.api.localization;

import java.util.HashMap;

public class Localization {
    private static final HashMap<String, String> localizationEN = new HashMap<>();
    private static final HashMap<String, String> localizationRU = new HashMap<>();

    static {
        localizationEN.put("debug", "Debug");
        localizationEN.put("log", "Log");
        localizationEN.put("default_page", "Default page");
        localizationEN.put("command_line_on_start", "Command line on start");
        localizationEN.put("password_set", "Password set");

        localizationRU.put("debug", "Отладка");
        localizationRU.put("log", "Лог");
        localizationRU.put("default_page", "Страница по умолчанию");
        localizationRU.put("command_line_on_start", "Командная строка при запуске");
        localizationRU.put("password_set", "Пароль установлен");
    }

    public static HashMap<String, String> getLocalizationEN() {
        return localizationEN;
    }

    public static HashMap<String, String> getLocalizationRU() {
        return localizationRU;
    }

    public static String getEN(String key) {
        return localizationEN.get(key);
    }

    public static String getRU(String key) {
        return localizationRU.get(key);
    }

    public static String get(String key) {
        return getEN(key) == null ? getRU(key) : getEN(key);
    }

    public static void add(String key, String value) {
        if (localizationEN.containsKey(key)) {
            localizationRU.put(key, localizationEN.get(key));
            return;
        }
        localizationEN.put(key, value);
    }

    public static boolean contains(String key) {
        return localizationEN.containsKey(key) || localizationRU.containsKey(key);
    }

    public static boolean containsEN(String key) {
        return localizationEN.containsKey(key);
    }

    public static boolean containsRU(String key) {
        return localizationRU.containsKey(key);
    }
}
