package org.thesalutyt.vimzer.api.features.commmand.error;

public enum ErrorType {
    WRONG_ARGUMENT_TYPE("Wrong argument type. ", 0),
    COMMAND_NOT_FOUND("Command not found. ", 1),
    COMMAND_FAILED("Command failed. ", 2),
    COMMAND_REQUIRES_ARG("Command requires an argument. ", 3),;

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
