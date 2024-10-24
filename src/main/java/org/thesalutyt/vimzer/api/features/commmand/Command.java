package org.thesalutyt.api.features.commmand;

public enum Command {
    EXIT("exit", "Exits the program", false, ArgumentType.NONE),
    CLOSE("c", "Close something based on argument", true, ArgumentType.STRING),
    NEWTAB("t", "Opens a new tab", false, ArgumentType.NONE),
    NEWWINDOW("w", "Opens a new window", true, ArgumentType.BOOLEAN),;

    private final String command;
    private final String description;
    private final boolean hasArgs;
    private final ArgumentType argType;

    Command(String command, String description, boolean hasArgs, ArgumentType argumentsType) {
        this.command = command;
        this.description = description;
        this.hasArgs = hasArgs;
        this.argType = argumentsType;
    }

    public String command() {
        return command;
    }

    public String description() {
        return description;
    }

    public boolean hasArgs() {
        return hasArgs;
    }

    public ArgumentType argType() {
        return argType;
    }

    public void execute(String arg) {
        if (hasArgs()) {
            if (CommandParser.parseArgType(arg) != argType()) {
                throw new IllegalArgumentException("Command '" + command() + "' requires an argument of type " + argType());
            }

            switch (command()) {
                case "c":
                    if (arg.equals("w")) {
                        System.out.println("CLOSING WINDOW");
                    } else if (arg.equals("t")) {
                        System.out.println("CLOSING TAB");
                    }
                case "t":
                case "w":
                default:
                    throw new IllegalArgumentException("Command '" + command() + "' does not exist");
            }
        }
    }

    public void execute() {
        if (hasArgs()) throw new IllegalArgumentException("Command '" + command() + "' requires an argument");

        switch (command()) {
            case "exit":
                System.exit(1);
            case "t":
                System.out.println("OPENING TAB");
            case "w":
                System.out.println("OPENING WINDOW");
            default:
                throw new IllegalArgumentException("Command '" + command() + "' does not exist");
        }
    }
}
