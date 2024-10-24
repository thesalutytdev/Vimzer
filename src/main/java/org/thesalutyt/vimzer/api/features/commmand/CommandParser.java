package org.thesalutyt.api.features.commmand;

public class CommandParser {
    public static void parse(String input) {
        String[] parse = input.strip().split(" ");

        for (int i = 0; i < parse.length; i++) {
            String current = parse[i];


        }
    }

    public static ArgumentType parseArgType(String arg) {
        if (arg.matches("\\d+")) return ArgumentType.NUMBER;
        if (arg.matches("true|false")) return ArgumentType.BOOLEAN;
        if (arg.matches("\".*\"|'.*'")) return ArgumentType.STRING;
        throw new IllegalArgumentException("Invalid argument type: " + arg);
    }
}
