package org.thesalutyt.vimzer.api.features.commmand;

import org.thesalutyt.vimzer.Configurations;
import org.thesalutyt.vimzer.api.features.commmand.argument.ArgumentType;
import org.thesalutyt.vimzer.api.error.ErrorType;
import org.thesalutyt.vimzer.back.shortcuts.ShortcutsHandler;
import org.thesalutyt.vimzer.window.popus.DefaultPopup;

import java.util.Arrays;
import java.util.Objects;

public class CommandParser {
    public static Feedback parse(String input) {
        String[] parse = input.strip().split(" ");

        String cmd = parse[0];

        Command command;
        Command.CommandExecutor executor;

        if (!ShortcutsHandler.containsShortcut(cmd)) {
            command = Arrays.stream(Command.values())
                    .filter(c -> c.command().equals(cmd))
                    .findFirst()
                    .orElseThrow(() -> {
                        Feedback feedback = Feedback.FAIL;

                        feedback.setMessage(feedback.getMessage()
                                .replace("%ERROR_MESSAGE%", ErrorType.COMMAND_NOT_FOUND.message())
                                .replace("%ERROR_CODE%", String.valueOf(ErrorType.COMMAND_NOT_FOUND.errorCode())));

                        DefaultPopup popup = new DefaultPopup(feedback.getMessage());
                        popup.display();

                        return new IllegalArgumentException("Command not found: " + cmd);
                    });
        } else {
            command = Objects.requireNonNull(ShortcutsHandler.getShortcut(cmd)).getCommand();
        }

        try {
            if (command.hasArgs()) {
                StringBuilder argB = new StringBuilder();

                for (int i = 1; i < parse.length; i++) {
                    String arg_ = parse[i];

                    argB.append(arg_).append(" ");
                }

                String arg = argB.toString().strip();

                if (CommandParser.parseArgType(arg) == ArgumentType.STRING) {
                    arg = arg.substring(1, arg.length() - 1).strip();
                }

                return ((Command.Executor) command.executor()).execute(arg);
            } else {
                return ((Command.ExecutorNoArg) command.executor()).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();

            Feedback feedback = Feedback.FAIL;
            feedback.setMessage(e.getMessage());

            return feedback;
        }
    }

    public static ArgumentType parseArgType(String arg) {
        if (arg.matches("\\d+")) return ArgumentType.NUMBER;
        if (arg.matches("true|false")) return ArgumentType.BOOLEAN;
        if (arg.matches("\".*\"|'.*'")) return ArgumentType.STRING;

        Feedback feedback = Feedback.FAIL;

        feedback.setMessage(feedback.getMessage()
                .replace("%ERROR_MESSAGE%", ErrorType.WRONG_ARGUMENT_TYPE.message())
                .replace("%ERROR_CODE%", String.valueOf(ErrorType.WRONG_ARGUMENT_TYPE.errorCode())));

        Configurations.getPopup().setMessage(feedback.getMessage());
        Configurations.getPopup().display();

        return ArgumentType.ERROR;
    }

    public static Command getCommand(String command) {
        return Arrays.stream(Command.values())
                .filter(c -> c.command().equals(command))
                .findFirst()
                .orElse(null);
    }
}
