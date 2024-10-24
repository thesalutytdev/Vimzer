package org.thesalutyt.vimzer.api.shortcuts;

import org.thesalutyt.vimzer.api.features.commmand.Command;
import org.thesalutyt.vimzer.api.features.commmand.CommandParser;
import org.thesalutyt.vimzer.api.features.commmand.Feedback;

public class Shortcut {
    private final String shortcut;
    private final Command command;

    public Shortcut(String shortcut, Command command) {
        if (ShortcutsHandler.containsShortcut(shortcut)) throw new RuntimeException("Shortcut already exists: " + shortcut);
        if (command == null) throw new RuntimeException("Command cannot be null!");

        this.shortcut = shortcut;
        this.command = command;
    }

    public Shortcut(String shortcut, String command) {
        this.shortcut = shortcut;
        this.command = CommandParser.getCommand(command);
    }

    public String getShortcut() {
        return shortcut;
    }

    public Command getCommand() {
        return command;
    }

    public Feedback execute(String args) {
        if (!command.hasArgs()) return command.execute();

        return command.execute(args);
    }

    public Feedback execute() {
        if (command.hasArgs()) throw new RuntimeException("Command needs an argument!");

        return command.execute();
    }

    public String beautify() {
        return shortcut + " - " + command;
    }

    @Override
    public String toString() {
        return shortcut + ";" + command.command();
    }
}
