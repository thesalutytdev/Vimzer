package org.thesalutyt.vimzer.api.error;

public enum ErrorType {
    WRONG_ARGUMENT_TYPE("Wrong argument type. ", 0),
    COMMAND_NOT_FOUND("Command not found. ", 1),
    COMMAND_FAILED("Command failed. ", 2),
    COMMAND_REQUIRES_ARG("Command requires an argument. ", 3),
    VAR_IS_CONST("Variable is constant. ", 4),
    VAR_NOT_FOUND("Variable not found. ", 5),
    VAR_ALREADY_EXISTS("Variable already exists. ", 6),
    SHORTCUT_NOT_FOUND("Shortcut not found. ", 7),
    SHORTCUT_ALREADY_EXISTS("Shortcut already exists. ", 8),
    FILE_NOT_FOUND("File not found. ", 9),;

    private final String message;
    private final int errorCode;

    ErrorType(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String message() {
        return message;
    }

    public int errorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return String.format("%s: %s (%d)", name(), message(), errorCode());
    }
}
