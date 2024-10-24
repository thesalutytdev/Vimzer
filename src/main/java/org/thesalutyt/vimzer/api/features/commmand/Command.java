package org.thesalutyt.vimzer.api.features.commmand;

import org.thesalutyt.vimzer.Configurations;
import org.thesalutyt.vimzer.api.features.Time;
import org.thesalutyt.vimzer.api.features.commmand.argument.ArgumentType;
import org.thesalutyt.vimzer.api.error.ErrorType;
import org.thesalutyt.vimzer.api.script.ScriptInstance;
import org.thesalutyt.vimzer.back.shortcuts.Shortcut;
import org.thesalutyt.vimzer.back.shortcuts.ShortcutsHandler;
import org.thesalutyt.vimzer.window.popus.DefaultPopup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum Command {
    EXIT("exit", "Exits the program", false, ArgumentType.NONE, new ExecutorNoArg() {
        @Override
        public Feedback execute() {
            System.exit(0);

            return Feedback.SUCCESS;
        }
    }),

    CLOSE("c", "Close something based on argument", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            if (arg.equals("w")) {
                System.out.println("CLOSING WINDOW");
            } else if (arg.equals("t")) {
                System.out.println("CLOSING TAB");
            }

            return Feedback.SUCCESS;
        }
    }),

    NEWTAB("t", "Opens a new tab", false, ArgumentType.NONE, new ExecutorNoArg() {
        @Override
        public Feedback execute() {
            System.out.println("CREATING TAB");

            return Feedback.SUCCESS;
        }
    }),

    NEWWINDOW("w", "Opens a new window", true, ArgumentType.BOOLEAN, new Executor() {
        @Override
        public Feedback execute(String arg) {
            System.out.println("CREATING WINDOW");

            return Feedback.SUCCESS;
        }
    }),

    PRINT("print", "Prints something based on argument", true, ArgumentType.ANY, new Executor() {
        @Override
        public Feedback execute(String arg) {
            new DefaultPopup(arg).display();
            System.out.println(arg);

            return Feedback.SUCCESS;
        }
    }),

    SETDEBUG("setdebug", "Sets debug mode", true, ArgumentType.BOOLEAN, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations.setDebug(Boolean.parseBoolean(arg));
            return Feedback.SUCCESS;
        }
    }),

    SETLOG("setlog", "Sets logging mode", true, ArgumentType.BOOLEAN, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations.setLog(Boolean.parseBoolean(arg));
            return Feedback.SUCCESS;
        }
    }),

    HELLOWORLD("helloworld", "Prints hello world", false, ArgumentType.NONE, new ExecutorNoArg() {
        @Override
        public Feedback execute() {
            new DefaultPopup("Hello World!").display();

            return Feedback.SUCCESS;
        }
    }),

    LOAD("load", "Loads a URL", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations.getCurrentActiveBrowserWindow().getBrowser().loadURL(arg);

            return Feedback.SUCCESS;
        }
    }),

    STPG("stpg", "Sets startup page", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations.setDefaultPage(arg);

            return Feedback.SUCCESS;
        }
    }),

    RSCFG("rscfg", "Reloads the config", false, ArgumentType.NONE, new ExecutorNoArg() {
        @Override
        public Feedback execute() {
            Configurations.reset();

            return Feedback.SUCCESS;
        }
    }),

    SCFG("scfg", "Saves the config", false, ArgumentType.NONE, new ExecutorNoArg() {
        @Override
        public Feedback execute() {
            Configurations.save();

            return Feedback.SUCCESS;
        }
    }),

    VIEW("view", "Displays some info", true, ArgumentType.NUMBER, new Executor() {
        @Override
        public Feedback execute(String arg) {
            int view = Integer.parseInt(arg.strip());
            switch (view) {
                case 0:
                    // Config
                    String[] cfg = Configurations.structData().split("\n");
                    DefaultPopup.MultiLine popup = new DefaultPopup.MultiLine(cfg);

                    popup.display();

                    break;
                case 1:
                    // History
                    String[] history = Configurations
                            .getCurrentActiveBrowserWindow()
                            .getHistory()
                            .toString().split("\n");

                    popup = new DefaultPopup.MultiLine(history);
                    popup.display();

                    break;

                case 2:
                    // Command history
                    String[] commandHistory = Configurations
                            .getCurrentActiveBrowserWindow()
                            .getCommandLine()
                            .getHistory()
                            .beautify() .split("\n");

                    popup = new DefaultPopup.MultiLine(commandHistory);
                    popup.display();

                    break;

                case 3:
                    // Shortcuts
                    String[] shortcuts;
                    ArrayList<String> shortcutsList = new ArrayList<>();

                    for (Shortcut shortcut : ShortcutsHandler.getShortcuts()) {
                        shortcutsList.add("> " + shortcut.beautify());
                    }

                    shortcuts = shortcutsList.toArray(new String[0]);

                    popup = new DefaultPopup.MultiLine(shortcuts);
                    popup.display();

                    break;

                default:
                    Feedback feedback = Feedback.FAIL;
                    feedback.setMessage(feedback.getMessage()
                            .replace("%ERROR_MESSAGE%", ErrorType.WRONG_ARGUMENT_TYPE.message())
                            .replace("%ERROR_CODE%", String.valueOf(ErrorType.WRONG_ARGUMENT_TYPE.errorCode())));

                    return feedback;
            }

            return Feedback.SUCCESS;
        }
    }),

    CLS("cls", "Clears something", true, ArgumentType.NUMBER, new Executor() {
        @Override
        public Feedback execute(String arg) {
            int cls = Integer.parseInt(arg);
            switch (cls) {
                case 0:
                    // Config
                    Configurations.reset();
                    break;
                case 1:
                    // History
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .getHistory()
                            .clear();
                    break;
                case 2:
                    // Command history
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .getCommandLine()
                            .getHistory()
                            .clear();
                    break;
            }

            return Feedback.SUCCESS;
        }
    }),

    TITLE("title", "Sets the title of the window", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations
                    .getCurrentActiveBrowserWindow()
                    .setTitle(arg);

            return Feedback.SUCCESS;
        }
    }),

    RELOAD("reload", "Reloads the page", false, ArgumentType.NONE, new ExecutorNoArg() {
        @Override
        public Feedback execute() {
            Configurations.getCurrentActiveBrowserWindow()
                    .getBrowser()
                    .loadURL(Configurations.getCurrentActiveBrowserWindow().getURL());

            return Feedback.SUCCESS;
        }
    }),

    SETPASS("setpass", "Sets the password", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations
                    .setPassword(arg);

            return Feedback.SUCCESS;
        }
    }),

    RMPASS("rmpass", "Removes the password", false, ArgumentType.NONE, new ExecutorNoArg() {
        @Override
        public Feedback execute() {
            Configurations.removePassword();

            return Feedback.SUCCESS;
        }
    }),

    GOOGLE("google", "Google something", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations
                    .getCurrentActiveBrowserWindow()
                    .loadURL("https://www.google.com/search?q=" + arg);

            return Feedback.SUCCESS;
        }
    }),

    YANDEX("yandex", "Yandex something", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations
                    .getCurrentActiveBrowserWindow()
                    .loadURL("https://www.yandex.com/search/?text=" + arg);

            return Feedback.SUCCESS;
        }
    }),

    BING("bing", "Bing something", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations
                    .getCurrentActiveBrowserWindow()
                    .loadURL("https://www.bing.com/search?q=" + arg);

            return Feedback.SUCCESS;
        }
    }),

    DDG("ddg", "DuckDuckGo something", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations
                    .getCurrentActiveBrowserWindow()
                    .loadURL("https://duckduckgo.com/?q=" + arg);

            return Feedback.SUCCESS;
        }
    }),

    WIKI("wiki", "Wikipedia something", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations
                    .getCurrentActiveBrowserWindow()
                    .loadURL("https://wikipedia.org/wiki/" + arg);

            return Feedback.SUCCESS;
        }
    }),

    CMDL("cmdl", "List of all commands", false, ArgumentType.NONE, new ExecutorNoArg() {
        @Override
        public Feedback execute() {
            DefaultPopup.MultiLine __popup;

            ArrayList<String> lines = new ArrayList<>();

            for (Command command : Command.values()) {
                lines.add(command.toString());
            }

            __popup = new DefaultPopup.MultiLine(lines.toArray(new String[0]));
            __popup.display();

            return Feedback.SUCCESS;
        }
    }),

    RUNSC("runsc", "Runs a script", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            return new ScriptInstance(new File(arg)).execute();
        }
    }),

    ASHC("ashc", "Adds a shortcut. Usage: ashc \"shortcut command\"", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            String __shc = arg.split(" ")[0];
            String __cmd = arg.replaceFirst(__shc + " ", "");

            Shortcut __shortcut = new Shortcut(__shc, __cmd);

            boolean __exists = false;

            for (Command c : Command.values()) {
                if (c.command().equals(__shc)) {
                    __exists = true;
                    break;
                }
            }

            if (ShortcutsHandler.getShortcut(__shc) != null || __exists) {
                Feedback feedback = Feedback.FAIL;
                feedback.setMessage(feedback.getMessage()
                        .replace("%ERROR_MESSAGE%", ErrorType.SHORTCUT_ALREADY_EXISTS.message())
                        .replace("%ERROR_CODE%",
                                String.valueOf(ErrorType.SHORTCUT_ALREADY_EXISTS.errorCode())));
                return feedback;
            }

            ShortcutsHandler.addShortcut(__shortcut);

            return Feedback.SUCCESS;
        }
    }),

    RSHC("rshc", "Removes a shortcut", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            if (ShortcutsHandler.getShortcut(arg) == null) {
                Feedback feedback = Feedback.FAIL;
                feedback.setMessage(feedback.getMessage()
                        .replace("%ERROR_MESSAGE%", ErrorType.SHORTCUT_NOT_FOUND.message())
                        .replace("%ERROR_CODE%",
                                String.valueOf(ErrorType.SHORTCUT_NOT_FOUND.errorCode())));
                return feedback;
            } else {
                ShortcutsHandler.removeShortcut(ShortcutsHandler.getShortcut(arg));
            }

            return Feedback.SUCCESS;
        }
    }),

    WAIT("wait", "Waits an amount of time and then executes. Usage: wait \"1000.ms command\"", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            String __waitTime = arg.split(" ")[0];
            String __cmdWait = arg.replaceFirst(__waitTime + " ", "");

            new Thread(() -> {
                try {
                    Thread.sleep(new Time(__waitTime).getMilliSeconds().longValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                CommandParser.parse(__cmdWait);
            }).start();

            return Feedback.SUCCESS;
        }
    }),

    HELPW("helpw", "Shows a help window", false, ArgumentType.NONE, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations.showHelp();

            return Feedback.SUCCESS;
        }
    }),

    FRUN("frun", "Sets firstrun", true, ArgumentType.BOOLEAN, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Configurations.setFirstRun(Boolean.parseBoolean(arg));

            return Feedback.SUCCESS;
        }
    }),

    HELP("help", "Shows help for command in the argument", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            Command __helpCmd = CommandParser.getCommand(arg);

            if (__helpCmd == null) {
                Feedback __feedback = Feedback.FAIL;
                __feedback.setMessage(__feedback.getMessage()
                        .replace("%ERROR_MESSAGE%", ErrorType.COMMAND_NOT_FOUND.message())
                        .replace("%ERROR_CODE%",
                                String.valueOf(ErrorType.COMMAND_NOT_FOUND.errorCode())));
                return __feedback;
            } else {
                String[] __cmdHelpLines = {
                        "Command: " + __helpCmd.command(),
                        "Description: " + __helpCmd.description(),
                        " ",
                        "Need arguments: " + __helpCmd.hasArgs()
                };

                if (__helpCmd.hasArgs()) {
                    __cmdHelpLines = Arrays.copyOf(__cmdHelpLines, __cmdHelpLines.length + 1);
                    __cmdHelpLines[__cmdHelpLines.length - 1] = "Arguments type: " + __helpCmd.argType();
                }

                new DefaultPopup.MultiLine(__cmdHelpLines).display();
            }

            return Feedback.SUCCESS;
        }
    }),

    LOG("log", "Logs something", true, ArgumentType.ANY, new Executor() {
        @Override
        public Feedback execute(String arg) {
            System.out.println(arg);
            return Feedback.SUCCESS;
        }
    }),

    RUN("run", "Runs a command", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            return CommandParser.parse(arg);
        }
    }),

    ASYNC("async", "Asynchronously runs a command", true, ArgumentType.STRING, new Executor() {
        @Override
        public Feedback execute(String arg) {
            AtomicReference<Feedback> feedback = new AtomicReference<>(Feedback.SUCCESS);

            new Thread(() -> feedback.set(CommandParser.parse(arg))).start();
            return feedback.get();
        }
    })
    ;

    private final String command;
    private final String description;
    private final boolean hasArgs;
    private final ArgumentType argType;
    private final CommandExecutor execute;

    Command(String command, String description, boolean hasArgs, ArgumentType argumentsType, CommandExecutor execute) {
        if (hasArgs && argumentsType.equals(ArgumentType.NONE)) {
            throw new IllegalArgumentException("Command " + command + " requires arguments");
        }

        for (Command c : Command.values()) {
            if (c.command().equals(command)) {
                throw new IllegalArgumentException("Command " + command + " already exists");
            }
        }

        this.command = command;
        this.description = description;
        this.hasArgs = hasArgs;
        this.argType = argumentsType;
        this.execute = execute;
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

    public CommandExecutor executor() {
        return execute;
    }

    @Deprecated
    public Feedback execute(String arg) {
        if (hasArgs()) {
            if (CommandParser.parseArgType(arg) != argType() && argType() != ArgumentType.ANY
                || CommandParser.parseArgType(arg) == ArgumentType.ERROR) {
                Feedback feedback = Feedback.FAIL;
                feedback.setMessage(feedback.getMessage()
                        .replace("%ERROR_MESSAGE%", ErrorType.WRONG_ARGUMENT_TYPE.message())
                        .replace("%ERROR_CODE%",
                                String.valueOf(ErrorType.WRONG_ARGUMENT_TYPE.errorCode())));

                return feedback;
            }

            if (CommandParser.parseArgType(arg) == ArgumentType.STRING) {
                arg = arg.substring(1, arg.length() - 1);
            }

            switch (command()) {
//                case "var":
//                    String key = arg.split("=")[0];
//                    String value_ = arg.split("=")[1];
//
//                    Variable var = new Variable(key, value_);
//                    return VariableHandler.addVariable(var);
//
//                case "setvar":
//                    String setKey = arg.split("=")[0];
//                    String setValue = arg.split("=")[1];
//                    return VariableHandler.setVariableValue(setKey, setValue);
//
//                case "rmvar":
//                    return VariableHandler.removeVariable(arg);

                case "log":
                    System.out.println(arg);
                    break;

                case "help":
                    Command __helpCmd = CommandParser.getCommand(arg);

                    if (__helpCmd == null) {
                        Feedback __feedback = Feedback.FAIL;
                        __feedback.setMessage(__feedback.getMessage()
                                .replace("%ERROR_MESSAGE%", ErrorType.COMMAND_NOT_FOUND.message())
                                .replace("%ERROR_CODE%",
                                        String.valueOf(ErrorType.COMMAND_NOT_FOUND.errorCode())));
                        return __feedback;
                    } else {
                        String[] __cmdHelpLines = {
                                "Command: " + __helpCmd.command(),
                                "Description: " + __helpCmd.description(),
                                " ",
                                "Need arguments: " + __helpCmd.hasArgs()
                        };

                        if (__helpCmd.hasArgs()) {
                            __cmdHelpLines = Arrays.copyOf(__cmdHelpLines, __cmdHelpLines.length + 1);
                            __cmdHelpLines[__cmdHelpLines.length - 1] = "Arguments type: " + __helpCmd.argType();
                        }

                        new DefaultPopup.MultiLine(__cmdHelpLines).display();
                    }

                    break;

                case "wait":
                    String __waitTime = arg.split(" ")[0];
                    String __cmdWait = arg.replaceFirst(__waitTime + " ", "");

                    new Thread(() -> {
                        try {
                            Thread.sleep(new Time(__waitTime).getMilliSeconds().longValue());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        CommandParser.parse(__cmdWait);
                    }).start();

                    break;

                case "rshc":
                    if (ShortcutsHandler.getShortcut(arg) == null) {
                        Feedback feedback = Feedback.FAIL;
                        feedback.setMessage(feedback.getMessage()
                                .replace("%ERROR_MESSAGE%", ErrorType.SHORTCUT_NOT_FOUND.message())
                                .replace("%ERROR_CODE%",
                                        String.valueOf(ErrorType.SHORTCUT_NOT_FOUND.errorCode())));
                        return feedback;
                    } else {
                        ShortcutsHandler.removeShortcut(ShortcutsHandler.getShortcut(arg));
                    }

                    break;

                case "ashc":
                    String __shc = arg.split(" ")[0];
                    String __cmd = arg.replaceFirst(__shc + " ", "");

                    Shortcut __shortcut = new Shortcut(__shc, __cmd);

                    boolean __exists = false;

                    for (Command c : Command.values()) {
                        if (c.command().equals(__shc)) {
                            __exists = true;
                            break;
                        }
                    }

                    if (ShortcutsHandler.getShortcut(__shc) != null || __exists) {
                        Feedback feedback = Feedback.FAIL;
                        feedback.setMessage(feedback.getMessage()
                                .replace("%ERROR_MESSAGE%", ErrorType.SHORTCUT_ALREADY_EXISTS.message())
                                .replace("%ERROR_CODE%",
                                        String.valueOf(ErrorType.SHORTCUT_ALREADY_EXISTS.errorCode())));
                        return feedback;
                    }

                    ShortcutsHandler.addShortcut(__shortcut);

                    break;

                case "runsc":
                    return new ScriptInstance(new File(arg)).execute();

                case "google":
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .loadURL("https://www.google.com/search?q=" + arg);
                    break;

                case "yandex":
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .loadURL("https://www.yandex.com/search/?text=" + arg);
                    break;

                case "bing":
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .loadURL("https://www.bing.com/search?q=" + arg);
                    break;

                case "ddg":
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .loadURL("https://duckduckgo.com/?q=" + arg);
                    break;

                case "wiki":
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .loadURL("https://wikipedia.org/wiki/" + arg);
                    break;

                case "setpass":
                    Configurations
                            .setPassword(arg);
                    break;

                case "pinw":
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .setAlwaysOnTop(Boolean.parseBoolean(arg));
                    break;

                case "title":
                    Configurations
                            .getCurrentActiveBrowserWindow()
                            .setTitle(arg);
                    break;

                case "view":
                    int view = Integer.parseInt(arg);
                    switch (view) {
                        case 0:
                            // Config
                            String[] cfg = Configurations.structData().split("\n");
                            DefaultPopup.MultiLine popup = new DefaultPopup.MultiLine(cfg);

                            popup.display();

                            break;
                        case 1:
                            // History
                            String[] history = Configurations
                                    .getCurrentActiveBrowserWindow()
                                    .getHistory()
                                    .toString().split("\n");

                            popup = new DefaultPopup.MultiLine(history);
                            popup.display();

                            break;

                        case 2:
                            // Command history
                            String[] commandHistory = Configurations
                                    .getCurrentActiveBrowserWindow()
                                    .getCommandLine()
                                    .getHistory()
                                    .beautify() .split("\n");

                            popup = new DefaultPopup.MultiLine(commandHistory);
                            popup.display();

                            break;

                        case 3:
                            // Shortcuts
                            String[] shortcuts;
                            ArrayList<String> shortcutsList = new ArrayList<>();

                            for (Shortcut shortcut : ShortcutsHandler.getShortcuts()) {
                                shortcutsList.add("> " + shortcut.beautify());
                            }

                            shortcuts = shortcutsList.toArray(new String[0]);

                            popup = new DefaultPopup.MultiLine(shortcuts);
                            popup.display();

                            break;
                    }

                    break;

                case "cls":
                    int cls = Integer.parseInt(arg);
                    switch (cls) {
                        case 0:
                            // Config
                            Configurations.reset();
                            break;
                        case 1:
                            // History
                            Configurations
                                    .getCurrentActiveBrowserWindow()
                                    .getHistory()
                                    .clear();
                            break;
                        case 2:
                            // Command history
                            Configurations
                                    .getCurrentActiveBrowserWindow()
                                    .getCommandLine()
                                    .getHistory()
                                    .clear();
                            break;
                    }

                    break;

                case "frun":
                    Configurations.setFirstRun(Boolean.parseBoolean(arg));
                    break;

                case "stpg":
                    Configurations.setDefaultPage(arg);
                    break;

                case "setdebug":
                    boolean debug = Boolean.parseBoolean(arg);
                    Configurations.setDebug(debug);
                    break;

                case "setlog":
                    boolean log = Boolean.parseBoolean(arg);
                    Configurations.setLog(log);
                    break;

                case "exit":
                    System.exit(0);

                case "c":
                    if (arg.equals("w")) {
                        System.out.println("CLOSING WINDOW");
                    } else if (arg.equals("t")) {
                        System.out.println("CLOSING TAB");
                    }

                    break;

                case "print":
                    Feedback feedback_ = Feedback.MESSAGE;

                    feedback_.setMessage(feedback_.getMessage()
                            .replace("%MESSAGE%", arg));

                    feedback_.setNecessary(true);

                    System.out.println(feedback_.getMessage());

                    return feedback_;

                case "load":
                    Configurations.getCurrentActiveBrowserWindow()
                            .loadURL(arg);

                    Configurations.getCurrentActiveBrowserWindow().requestFocus();
                    break;


                case "t":
                case "w":
                default:
                    Feedback feedback = Feedback.FAIL;
                    feedback.setMessage(feedback.getMessage()
                            .replace("%ERROR_MESSAGE%", ErrorType.COMMAND_NOT_FOUND.message())
                            .replace("%ERROR_CODE%",
                                    String.valueOf(ErrorType.COMMAND_NOT_FOUND.errorCode())));

                    return feedback;
            }
        }

        return Feedback.SUCCESS;
    }

    @Deprecated
    public Feedback execute() {
        if (hasArgs()) {
            Feedback feedback = Feedback.FAIL;
            feedback.setMessage(feedback.getMessage()
                    .replace("%ERROR_MESSAGE%", ErrorType.COMMAND_REQUIRES_ARG.message())
                    .replace("%ERROR_CODE%",
                            String.valueOf(ErrorType.COMMAND_REQUIRES_ARG.errorCode())));

            return feedback;
        }

        switch (command()) {
            case "helpw":
                Configurations.showHelp();
                break;

            case "exit":
                System.exit(0);

            case "cmdl":
                DefaultPopup.MultiLine __popup;

                ArrayList<String> lines = new ArrayList<>();

                for (Command command : Command.values()) {
                    lines.add(command.toString());
                }

                __popup = new DefaultPopup.MultiLine(lines.toArray(new String[0]));
                __popup.display();

                break;

            case "rmpass":
                Configurations.removePassword();
                break;

            case "reload":
                Configurations.getCurrentActiveBrowserWindow().reload();
                break;

            case "scfg":
                Configurations.save();
                break;

            case "rscfg":
                Configurations.reset();
                break;

            case "t":
                System.out.println("OPENING TAB");
                break;

            case "w":
                System.out.println("OPENING WINDOW");
                break;

            case "helloworld":
                new DefaultPopup("Hello World!").display();

                System.out.println("Hello World!");
                break;

            default:
                Feedback feedback_ = Feedback.FAIL;
                feedback_.setMessage(feedback_.getMessage()
                        .replace("%ERROR_MESSAGE%", ErrorType.COMMAND_NOT_FOUND.message())
                        .replace("%ERROR_CODE%",
                                String.valueOf(ErrorType.COMMAND_NOT_FOUND.errorCode())));

                return feedback_;
        }


        return Feedback.SUCCESS;
    }

    @Override
    public String toString() {
        return String.format("%s: %s (%s)", command(), description(), argType());
    }

    public interface CommandExecutor {
        boolean hasArgs();
    }

    @FunctionalInterface
    public interface ExecutorNoArg extends CommandExecutor {
        Feedback execute();

        @Override
        default boolean hasArgs() {
            return false;
        }
    }

    @FunctionalInterface
    public interface Executor extends CommandExecutor {
        Feedback execute(String arg);

        @Override
        default boolean hasArgs() {
            return true;
        }
    }

}
